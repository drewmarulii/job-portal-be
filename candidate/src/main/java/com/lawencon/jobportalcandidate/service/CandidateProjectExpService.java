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
import com.lawencon.jobportalcandidate.dao.CandidateProjectExpDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidateprojectexp.CandidateProjectExpInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateprojectexp.CandidateProjectExpResDto;
import com.lawencon.jobportalcandidate.dto.candidateprojectexp.CandidateProjectExpUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateProjectExp;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.util.DateUtil;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateProjectExpService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private CandidateProjectExpDao projectExpDao;

	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateProjectExpResDto> getProjectExpByCandidate(String id) {
		final List<CandidateProjectExpResDto> projectExpResList = new ArrayList<>();
		final List<CandidateProjectExp> projectExp = projectExpDao.getByCandidate(id);
		for (int i = 0; i < projectExp.size(); i++) {
			final CandidateProjectExpResDto projectExpRes = new CandidateProjectExpResDto();
			projectExpRes.setDescription(projectExp.get(i).getDescription());
			projectExpRes.setEndDate(DateUtil.localDateToString(projectExp.get(i).getEndDate()));
			projectExpRes.setStartDate(DateUtil.localDateToString(projectExp.get(i).getStartDate()));
			projectExpRes.setProjectName(projectExp.get(i).getProjectName());
			projectExpRes.setProjectUrl(projectExp.get(i).getProjectUrl());
			projectExpRes.setId(projectExp.get(i).getId());

			projectExpResList.add(projectExpRes);
		}

		return projectExpResList;
	}

	public InsertResDto insertCandidateProjectExp(CandidateProjectExpInsertReqDto data) {
		final InsertResDto insertRes = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateProjectExp projectExp = new CandidateProjectExp();
			projectExp.setProjectCode(GenerateCode.generateCode());
			data.setProjectCode(projectExp.getProjectCode());
			projectExp.setProjectName(data.getProjectName());
			projectExp.setDescription(data.getDescription());
			projectExp.setProjectUrl(data.getProjectUrl());
			projectExp.setStartDate(DateUtil.parseStringToLocalDate(data.getStartDate()));
			projectExp.setEndDate(DateUtil.parseStringToLocalDate(data.getEndDate()));

			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class,
					principalService.getAuthPrincipal());
			data.setEmail(candidateUser.getUserEmail());
			projectExp.setCandidateUser(candidateUser);
			projectExp.setCreatedBy(principalService.getAuthPrincipal());

			projectExpDao.save(projectExp);

			final String candidateProjectsAPI = "http://localhost:8080/candidate-projects";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<CandidateProjectExpInsertReqDto> candidateProjectInsert = RequestEntity
					.post(candidateProjectsAPI).headers(headers).body(data);

			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(candidateProjectInsert,
					InsertResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				insertRes.setId(projectExp.getId());
				insertRes.setMessage("Candidate Project Exp Insert Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return insertRes;
	}

	public UpdateResDto updateCandidateProjectExp(CandidateProjectExpUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final CandidateProjectExp projectExp = projectExpDao.getById(CandidateProjectExp.class, data.getId());
			projectExp.setProjectName(data.getProjectName());
			projectExp.setDescription(data.getDescription());
			projectExp.setProjectUrl(data.getProjectUrl());
			projectExp.setStartDate(DateUtil.parseStringToLocalDate(data.getStartDate()));
			projectExp.setEndDate(DateUtil.parseStringToLocalDate(data.getEndDate()));
			
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			projectExp.setCandidateUser(candidateUser);
			projectExp.setUpdatedBy(principalService.getAuthPrincipal());
			
			final CandidateProjectExp projectExpId = projectExpDao.saveAndFlush(projectExp);
			updateResDto.setVersion(projectExpId.getVersion());
			updateResDto.setMessage("Candidate Project Exp Update Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return updateResDto;
	}

	public DeleteResDto deleteCandidateProjectExp(String id) {
		final DeleteResDto deleteRes = new DeleteResDto();

		try {
			em().getTransaction().begin();
			final CandidateProjectExp project = projectExpDao.getById(CandidateProjectExp.class, id);
			projectExpDao.deleteById(CandidateProjectExp.class, project.getId());
			
			final String candidateProjectDeleteAPI = "http://localhost:8080/candidate-projects/deleteProject/";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final HttpEntity<CandidateProjectExp> httpEntity = new HttpEntity<CandidateProjectExp>(headers);

			final ResponseEntity<CandidateProjectExp> responseAdmin = restTemplate.exchange(
					candidateProjectDeleteAPI+project.getProjectCode(), HttpMethod.DELETE, httpEntity, CandidateProjectExp.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				deleteRes.setMessage("Delete Candidate Project Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Deletion Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return deleteRes;
	}

}
