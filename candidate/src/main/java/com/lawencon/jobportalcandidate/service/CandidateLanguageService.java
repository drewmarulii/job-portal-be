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
import com.lawencon.jobportalcandidate.dao.CandidateLanguageDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidatelanguage.CandidateLanguageInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidatelanguage.CandidateLanguageResDto;
import com.lawencon.jobportalcandidate.dto.candidatelanguage.CandidateLanguageUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateLanguage;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateLanguageService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CandidateLanguageDao candidateLanguageDao;

	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private PrincipalService<String> principalService;
	
	public List<CandidateLanguageResDto> getCandidateLanguageByCandidate(String id) {
		final List<CandidateLanguage> candidateLanguage = candidateLanguageDao.getLanguageByCandidate(id);
		final List<CandidateLanguageResDto> candidateLanguageResList = new ArrayList<>();
		for (int i = 0; i < candidateLanguage.size(); i++) {
			final CandidateLanguageResDto language = new CandidateLanguageResDto();
			language.setLanguageName(candidateLanguage.get(i).getLanguageName());
			language.setId(candidateLanguage.get(i).getId());
			language.setListeningRate(candidateLanguage.get(i).getListeningRate());
			language.setSpeakingRate(candidateLanguage.get(i).getSpeakingRate());
			language.setWritingRate(candidateLanguage.get(i).getWritingRate());
			
			candidateLanguageResList.add(language);
		}
		return candidateLanguageResList;
	}
	
	public InsertResDto insertCandidateLanguage(CandidateLanguageInsertReqDto data) {
		InsertResDto insertRes = null;
		try {
			em().getTransaction().begin();
			final CandidateLanguage candidateLanguage = new CandidateLanguage();
			candidateLanguage.setLangugageCode(GenerateCode.generateCode());
			data.setLanguageCode(candidateLanguage.getLangugageCode());
			candidateLanguage.setLanguageName(data.getLanguageName());
			candidateLanguage.setListeningRate(data.getListeningRate());
			candidateLanguage.setSpeakingRate(data.getSpeakingRate());
			candidateLanguage.setWritingRate(data.getWritingRate());
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			candidateLanguage.setCandidateUser(candidateUser);
			data.setEmail(candidateUser.getUserEmail());
			candidateLanguage.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateLanguage languageId = candidateLanguageDao.save(candidateLanguage);
			
			final String candidateDocumentApi = "http://localhost:8080/candidate-language";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			insertRes = new InsertResDto();
			final RequestEntity<CandidateLanguageInsertReqDto>languageInsert = RequestEntity.post(candidateDocumentApi).headers(headers).body(data);
			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(languageInsert, InsertResDto.class);
			if(responseAdmin.getStatusCode().equals(HttpStatus.CREATED)){
				insertRes.setId(languageId.getId());
				insertRes.setMessage("Insert Candidate Language Success");
				em().getTransaction().commit();
			}
		}catch(Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return insertRes;
	}
	public UpdateResDto updateCandidateLanguage(CandidateLanguageUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final CandidateLanguage candidateLanguage = candidateLanguageDao.getById(CandidateLanguage.class, data.getId());
			candidateLanguage.setLanguageName(data.getLanguageName());
			candidateLanguage.setListeningRate(data.getLanguageName());
			candidateLanguage.setSpeakingRate(data.getSpeakingRate());
			candidateLanguage.setWritingRate(data.getWritingRate());
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			candidateLanguage.setCandidateUser(candidateUser);
			candidateLanguage.setUpdatedBy(principalService.getAuthPrincipal());
			final CandidateLanguage languageId = candidateLanguageDao.saveAndFlush(candidateLanguage);
			
			updateResDto.setMessage("Update Candidate Language Success");
			updateResDto.setVersion(languageId.getVersion());
			em().getTransaction().commit();
		}catch(Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return updateResDto;
	}
	
	public DeleteResDto DeleteCandidateLanguage(String id) {
		final DeleteResDto deleteRes = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			final CandidateLanguage language = candidateLanguageDao.getById(CandidateLanguage.class, id);
			candidateLanguageDao.deleteById(CandidateLanguage.class, language.getId());
			
			final String candidateLanguageDeleteAPI = "http://localhost:8080/candidate-language/deleteLanguage/";
			
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final HttpEntity<CandidateLanguage> httpEntity = new HttpEntity<CandidateLanguage>(headers);

			final ResponseEntity<CandidateLanguage> responseAdmin = restTemplate.exchange(
					candidateLanguageDeleteAPI+language.getLangugageCode(), HttpMethod.DELETE, httpEntity, CandidateLanguage.class);
			
			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				deleteRes.setMessage("Delete Candidate Language Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Deletion Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		candidateLanguageDao.deleteById(CandidateLanguage.class, id);
		
		deleteRes.setMessage("Delete Candidate Language Success");
		return deleteRes;
	}
	
}
