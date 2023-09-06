package com.lawencon.jobportalcandidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportalcandidate.dao.CandidateSkillDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidateskill.CandidateSkillInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateskill.CandidateSkillResDto;
import com.lawencon.jobportalcandidate.dto.candidateskill.CandidateSkillUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateSkill;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateSkillService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CandidateSkillDao candidateSkillDao;
	
	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private PrincipalService<String> principalService;
	
	public List<CandidateSkillResDto> getSkillsByCandidate(String id) {
		final List<CandidateSkillResDto> skillsDto = new ArrayList<>();
		final List<CandidateSkill> skills = candidateSkillDao.getByCandidate(id);
		
		for (int i=0; i<skills.size(); i++) {
			final CandidateSkillResDto skill = new CandidateSkillResDto();
			skill.setId(skills.get(i).getId());
			skill.setSkillName(skills.get(i).getSkillName());
			
			skillsDto.add(skill);
		}
		
		return skillsDto;
	}
	
	public InsertResDto insertSkill(CandidateSkillInsertReqDto data) {		
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateSkill skill = new CandidateSkill();
			skill.setSkillName(data.getSkillName());
			skill.setSkillCode(GenerateCode.generateCode());
			data.setSkillCode(skill.getSkillCode());
			
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			data.setEmail(candidate.getUserEmail());
			skill.setCandidateUser(candidate);
			skill.setCreatedBy(principalService.getAuthPrincipal());
			
			candidateSkillDao.save(skill);
			
			final String candidateSkillAPI = "http://localhost:8080/candidate-skills";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<CandidateSkillInsertReqDto> candidateSkillInsert = RequestEntity
					.post(candidateSkillAPI).headers(headers).body(data);
			
			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(candidateSkillInsert,
					InsertResDto.class);
			
			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(skill.getId());
				result.setMessage("Skill has been added");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UpdateResDto updateSkill(CandidateSkillUpdateReqDto data) {
		final CandidateSkill skill = candidateSkillDao.getById(CandidateSkill.class, data.getId());
		
		UpdateResDto result = null; 
		
		try {
			em().getTransaction().begin();
			skill.setId(data.getId());
			skill.setSkillName(data.getSkillName());
			
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			skill.setCandidateUser(candidate);
			skill.setUpdatedBy(principalService.getAuthPrincipal());
			candidateSkillDao.save(skill);
			
			result = new UpdateResDto();
			result.setVersion(skill.getVersion());
			result.setMessage("Skill record updated!");
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}
	
	public DeleteResDto deleteSkill(String id) {
		final DeleteResDto response = new DeleteResDto();

		try {
			em().getTransaction().begin();
			final CandidateSkill skill = candidateSkillDao.getById(CandidateSkill.class, id);
			candidateSkillDao.deleteById(CandidateSkill.class, skill.getId());
			
			final String candidateSkillDeleteAPI = "http://localhost:8080/candidate-skills/deleteSkill/";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final HttpEntity<CandidateSkill> httpEntity = new HttpEntity<CandidateSkill>(headers);
			
			final ResponseEntity<CandidateSkill> responseAdmin = restTemplate.exchange(
					candidateSkillDeleteAPI+skill.getSkillCode(), HttpMethod.DELETE, httpEntity, CandidateSkill.class);
			
			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				response.setMessage("Delete Candidate Skill Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Deletion Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return response;
	}
}
