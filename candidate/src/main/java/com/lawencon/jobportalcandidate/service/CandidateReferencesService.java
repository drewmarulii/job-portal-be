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
import com.lawencon.jobportalcandidate.dao.CandidateReferencesDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidatereferences.CandidateReferencesInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidatereferences.CandidateReferencesResDto;
import com.lawencon.jobportalcandidate.dto.candidatereferences.CandidateReferencesUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateReferences;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateReferencesService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CandidateReferencesDao candidateRefDao;
	
	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private PrincipalService<String> principalService;
	

	
	public List<CandidateReferencesResDto> getReferencesByCandidate(String id) {
		final List<CandidateReferencesResDto> referencesDto = new ArrayList<>();
		final List<CandidateReferences> references = candidateRefDao.getByCandidate(id);
		
		for (int i=0; i<references.size(); i++) {
			final CandidateReferencesResDto reference = new CandidateReferencesResDto();
			reference.setId(references.get(i).getId());
			reference.setFullname(references.get(i).getFullName());
			reference.setRelationship(references.get(i).getRelationship());
			reference.setOccupation(references.get(i).getOccupation());
			reference.setPhoneNumber(references.get(i).getPhoneNumber());
			reference.setEmail(references.get(i).getEmail());
			reference.setCompany(references.get(i).getCompany());
			reference.setDescription(references.get(i).getDescription());
			
			referencesDto.add(reference);
		}
		
		return referencesDto;
	}
	
	public InsertResDto insertReference(CandidateReferencesInsertReqDto data) {
		final CandidateReferences reference = new CandidateReferences();

		InsertResDto result = null;
		
		try {
			em().getTransaction().begin();
			reference.setReferenceCode(GenerateCode.generateCode());
			data.setReferenceCode(reference.getReferenceCode());
			reference.setFullName(data.getFullname());
			reference.setRelationship(data.getRelationship());
			reference.setOccupation(data.getOccupation());
			reference.setPhoneNumber(data.getPhoneNumber());
			reference.setEmail(data.getEmail());
			reference.setCompany(data.getCompany());
			reference.setDescription(data.getDescription());
			reference.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			reference.setCandidateUser(candidate);
			data.setCandidateEmail(candidate.getUserEmail());
			final CandidateReferences refId =  candidateRefDao.save(reference);
			
			final String candidateReferenceApi = "http://localhost:8080/candidate-references";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			result = new InsertResDto();
			final RequestEntity<CandidateReferencesInsertReqDto>referenceInsert = RequestEntity.post(candidateReferenceApi).headers(headers).body(data);
			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(referenceInsert, InsertResDto.class);
			if(responseAdmin.getStatusCode().equals(HttpStatus.CREATED)){
				result.setId(refId.getId());
				result.setMessage("Reference record added!");
				em().getTransaction().commit();
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}
	
	public UpdateResDto updateReferences(CandidateReferencesUpdateReqDto data) {
		final CandidateReferences reference = candidateRefDao.getById(CandidateReferences.class, data.getId());

		UpdateResDto result = null;
		
		try {
			em().getTransaction().begin();
			reference.setFullName(data.getFullname());
			reference.setRelationship(data.getRelationship());
			reference.setOccupation(data.getOccupation());
			reference.setPhoneNumber(data.getPhoneNumber());
			reference.setEmail(data.getEmail());
			reference.setCompany(data.getCompany());
			reference.setDescription(data.getDescription());
			reference.setUpdatedBy(principalService.getAuthPrincipal());
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			reference.setCandidateUser(candidate);
			
			result = new UpdateResDto();
			result.setVersion(reference.getVersion());
			result.setMessage("Reference record updated!");
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}
	
	public DeleteResDto deleteReference(String id) {
		final DeleteResDto response = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			final CandidateReferences reference = candidateRefDao.getById(CandidateReferences.class, id);
			candidateRefDao.deleteById(CandidateReferences.class, reference.getId());
			
			final String candidateReferenceDeleteAPI = "http://localhost:8080/candidate-references/deleteReference/";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final HttpEntity<CandidateReferences> httpEntity = new HttpEntity<CandidateReferences>(headers);

			final ResponseEntity<CandidateReferences> responseAdmin = restTemplate.exchange(
					candidateReferenceDeleteAPI+reference.getReferenceCode(), HttpMethod.DELETE, httpEntity, CandidateReferences.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				response.setMessage("Delete Candidate Reference Success");
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
