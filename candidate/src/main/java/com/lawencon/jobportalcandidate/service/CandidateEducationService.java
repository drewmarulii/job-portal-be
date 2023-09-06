package com.lawencon.jobportalcandidate.service;

import java.time.LocalDate;
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
import com.lawencon.jobportalcandidate.dao.CandidateEducationDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidateeducation.CandidateEducationInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateeducation.CandidateEducationResDto;
import com.lawencon.jobportalcandidate.dto.candidateeducation.CandidateEducationUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateAddress;
import com.lawencon.jobportalcandidate.model.CandidateEducation;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.util.DateUtil;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateEducationService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CandidateEducationDao candidateEducationDao;

	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateEducationResDto> getEducationByCandidate(String id) {
		final List<CandidateEducationResDto> educationsDto = new ArrayList<>();
		final List<CandidateEducation> educations = candidateEducationDao.getEducationByCandidate(id);

		for (int i = 0; i < educations.size(); i++) {
			final CandidateEducationResDto education = new CandidateEducationResDto();
			education.setId(educations.get(i).getId());
			education.setDegreeName(educations.get(i).getDegreeName());
			education.setInstituitionName(educations.get(i).getInstitutionName());
			education.setMajors(educations.get(i).getMajors());
			education.setCgpa(educations.get(i).getCgpa());
			education.setStartYear(DateUtil.localDateToString(educations.get(i).getStartYear()));
			education.setEndYear(DateUtil.localDateToString(educations.get(i).getEndYear()));
			education.setCandidateId(educations.get(i).getCandidateUser().getId());

			educationsDto.add(education);
		}

		return educationsDto;
	}

	public InsertResDto insertEducation(CandidateEducationInsertReqDto data) {
		final InsertResDto insertResDto = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateEducation education = new CandidateEducation();
			education.setEducationCode(GenerateCode.generateCode());
			data.setEducationCode(education.getEducationCode());
			education.setDegreeName(data.getDegreeName());
			education.setInstitutionName(data.getInstituitionName());
			education.setMajors(data.getMajors());
			education.setCgpa(data.getCgpa());
			education.setStartYear(LocalDate.parse(data.getStartYear().toString()));
			education.setEndYear(LocalDate.parse(data.getEndYear().toString()));

			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			data.setEmail(candidate.getUserEmail());
			education.setCandidateUser(candidate);
			education.setCreatedBy(principalService.getAuthPrincipal());
			
			candidateEducationDao.save(education);

			final String candidateEducationAPI = "http://localhost:8080/candidate-educations";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<CandidateEducationInsertReqDto> candidateEducationInsert = RequestEntity
					.post(candidateEducationAPI).headers(headers).body(data);
			
			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(candidateEducationInsert,
					InsertResDto.class);
			
			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				insertResDto.setId(education.getId());
				insertResDto.setMessage("Education has been added");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertResDto;
	}

	public UpdateResDto updateEducation(CandidateEducationUpdateReqDto data) {
		final CandidateEducation education = candidateEducationDao.getById(CandidateEducation.class, data.getId());

		UpdateResDto result = null;

		try {
			em().getTransaction().begin();
			education.setId(data.getId());
			education.setDegreeName(data.getDegreeName());
			education.setInstitutionName(data.getInstituitionName());
			education.setMajors(data.getMajors());
			education.setCgpa(data.getCgpa());
			education.setStartYear(LocalDate.parse(data.getStartYear()));
			education.setEndYear(LocalDate.parse(data.getEndYear()));

			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			education.setCandidateUser(candidate);
			education.setUpdatedBy(principalService.getAuthPrincipal());
			candidateEducationDao.saveAndFlush(education);

			result = new UpdateResDto();
			result.setVersion(education.getVersion());
			result.setMessage("Education has been updated");

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}

	public DeleteResDto deleteEducation(String id) {
		final DeleteResDto response = new DeleteResDto();

		try {
			em().getTransaction().begin();
			final CandidateEducation education = candidateEducationDao.getById(CandidateEducation.class, id);
			candidateEducationDao.deleteById(CandidateEducation.class, education.getId());
			
			final String candidateEducationAPI = "http://localhost:8080/candidate-educations/deleteEducation/";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final HttpEntity<CandidateEducation> httpEntity = new HttpEntity<CandidateEducation>(headers);

			final ResponseEntity<CandidateAddress> responseAdmin = restTemplate.exchange(
					candidateEducationAPI+education.getEducationCode(), HttpMethod.DELETE, httpEntity, CandidateAddress.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				response.setMessage("Delete Candidate Education Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		candidateEducationDao.deleteById(CandidateEducation.class, id);

		response.setMessage("Education has been removed");

		return response;
	}
}
