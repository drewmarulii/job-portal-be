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
import com.lawencon.jobportalcandidate.dao.CandidateTrainingExpDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidatetrainingexp.CandidateTrainingExpInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidatetrainingexp.CandidateTrainingExpResDto;
import com.lawencon.jobportalcandidate.dto.candidatetrainingexp.CandidateTrainingExpUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateTrainingExp;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.util.DateUtil;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateTrainingExpService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private CandidateTrainingExpDao trainingDao;
	
	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateTrainingExpResDto> getAllTrainingExpByCandidate(String id) {
		final List<CandidateTrainingExpResDto> trainingExpResList = new ArrayList<>();
		final List<CandidateTrainingExp> trainingExp = trainingDao.getByCandidate(id);
		for (int i = 0; i < trainingExp.size(); i++) {
			final CandidateTrainingExpResDto trainingExpRes = new CandidateTrainingExpResDto();
			trainingExpRes.setTrainingName(trainingExp.get(i).getTrainingName());
			trainingExpRes.setOrganizationName(trainingExp.get(i).getOrganizationName());
			trainingExpRes.setId(trainingExp.get(i).getId());
			trainingExpRes.setDescription(trainingExp.get(i).getDescription());
			trainingExpRes.setStartDate(DateUtil.localDateToString(trainingExp.get(i).getStartDate()));
			trainingExpRes.setEndDate(DateUtil.localDateToString(trainingExp.get(i).getEndDate()));
			
			trainingExpResList.add(trainingExpRes);
		}
		return trainingExpResList;
	}

	public InsertResDto insertCandidateTrainingExp(CandidateTrainingExpInsertReqDto data) {
		 InsertResDto insertRes = null;
		try {
			em().getTransaction().begin();
			final CandidateTrainingExp trainingExp = new CandidateTrainingExp();
			trainingExp.setTrainingCode(GenerateCode.generateCode());
			data.setTrainingCode(trainingExp.getTrainingCode());
			trainingExp.setCreatedBy(principalService.getAuthPrincipal());
			trainingExp.setTrainingName(data.getTrainingName());
			trainingExp.setDescription(data.getDescription());
			trainingExp.setOrganizationName(data.getOrganizationName());
			trainingExp.setStartDate(DateUtil.parseStringToLocalDate(data.getStartDate()));
			trainingExp.setEndDate(DateUtil.parseStringToLocalDate(data.getEndDate()));

			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			trainingExp.setCandidateUser(candidateUser);
			data.setEmail(candidateUser.getUserEmail());
			final CandidateTrainingExp trainingId = trainingDao.save(trainingExp);
			
			final String candidateTrainingApi = "http://localhost:8080/candidate-trainings";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			insertRes = new InsertResDto();
			final RequestEntity<CandidateTrainingExpInsertReqDto>trainingInsert = RequestEntity.post(candidateTrainingApi).headers(headers).body(data);
			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(trainingInsert, InsertResDto.class);
			if(responseAdmin.getStatusCode().equals(HttpStatus.CREATED)){
				insertRes.setId(trainingId.getId());
				insertRes.setMessage("Training Exp record added!");
				em().getTransaction().commit();
			}
			else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Trainin Exp Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertRes;
	}
	
	public UpdateResDto updateCandidateTrainingExp(CandidateTrainingExpUpdateReqDto data) {
		final UpdateResDto updateRes = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final CandidateTrainingExp trainingExp = trainingDao.getById(CandidateTrainingExp.class, data.getId());
			trainingExp.setUpdatedBy(principalService.getAuthPrincipal());
			trainingExp.setTrainingName(data.getTrainingName());
			trainingExp.setOrganizationName(data.getOrganizationName());
			trainingExp.setStartDate(DateUtil.parseStringToLocalDate(data.getStartDate()));
			trainingExp.setEndDate(DateUtil.parseStringToLocalDate(data.getEndDate()));

			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			trainingExp.setCandidateUser(candidateUser);

			final CandidateTrainingExp trainingId = trainingDao.save(trainingExp);
			
			updateRes.setMessage("Candidate Training Exp Insert Success");
			updateRes.setVersion(trainingId.getVersion());
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return updateRes;
	}
	
	
	public DeleteResDto deleteCandidateTrainingExp(String id) {
		final DeleteResDto deleteRes = new DeleteResDto();

		try {
			em().getTransaction().begin();
			final CandidateTrainingExp training = trainingDao.getById(CandidateTrainingExp.class, id);
			trainingDao.deleteById(CandidateTrainingExp.class, training.getId());
			
			final String candidateTrainingAPI = "http://localhost:8080/candidate-trainings/deleteTraining/";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final HttpEntity<CandidateTrainingExp> httpEntity = new HttpEntity<CandidateTrainingExp>(headers);

			final ResponseEntity<CandidateTrainingExp> responseAdmin = restTemplate.exchange(
					candidateTrainingAPI+training.getTrainingCode(), HttpMethod.DELETE, httpEntity, CandidateTrainingExp.class);
			
			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				deleteRes.setMessage("Delete Candidate Training Success");
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
