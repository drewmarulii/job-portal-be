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
import com.lawencon.jobportalcandidate.dao.CandidateFamilyDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidatefamily.CandidateFamilyInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidatefamily.CandidateFamilyResDto;
import com.lawencon.jobportalcandidate.dto.candidatefamily.CandidateFamilyUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateFamily;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.util.DateUtil;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateFamilyService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CandidateFamilyDao candidateFamilyDao;

	@Autowired
	private CandidateUserDao candidateUserDao;
	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateFamilyResDto> getFamilyByCandidate(String id) {
		final List<CandidateFamilyResDto> familiesDto = new ArrayList<>();
		final List<CandidateFamily> families = candidateFamilyDao.getFamilyByCandidate(id);

		for (int i = 0; i < families.size(); i++) {
			final CandidateFamilyResDto family = new CandidateFamilyResDto();
			family.setId(families.get(i).getId());
			family.setFullname(families.get(i).getFullname());
			family.setRelationship(families.get(i).getRelationship());
			family.setDegreeName(families.get(i).getDegreeName());
			family.setOccupation(families.get(i).getOccupation());
			family.setBirthDate(DateUtil.localDateToString(families.get(i).getBirthDate()));
			family.setBirthPlace(families.get(i).getBirthPlace());

			familiesDto.add(family);
		}

		return familiesDto;
	}

	public InsertResDto insertFamily(CandidateFamilyInsertReqDto data) {
		final CandidateFamily family = new CandidateFamily();

		InsertResDto result = new InsertResDto();

		try {
			em().getTransaction().begin();
			family.setFamilyCode(GenerateCode.generateCode());
			data.setFamilyCode(family.getFamilyCode());
			family.setFullname(data.getFullname());
			family.setRelationship(data.getRelationship());
			family.setDegreeName(data.getDegreeName());
			family.setOccupation(data.getOccupation());
			family.setBirthDate(LocalDate.parse(data.getBirthDate().toString()));
			family.setBirthPlace(data.getBirthPlace());
			family.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
					principalService.getAuthPrincipal());
			family.setCandidateUser(candidate);
			data.setEmail(candidate.getUserEmail());
			final CandidateFamily familyId = candidateFamilyDao.save(family);

//			result = new InsertResDto();
//			result.setId(family.getId());
//			result.setMessage("Family record added!");

			final String candidateFamilyApi = "http://localhost:8080/candidate-family";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<CandidateFamilyInsertReqDto> familyInsert = RequestEntity.post(candidateFamilyApi)
					.headers(headers).body(data);
			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(familyInsert, InsertResDto.class);
			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(familyId.getId());
				result.setMessage("Family Insert Success !");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Family Insert failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return result;
	}

	public UpdateResDto updateFamily(CandidateFamilyUpdateReqDto data) {
		final CandidateFamily family = candidateFamilyDao.getById(CandidateFamily.class, data.getId());

		UpdateResDto result = null;

		try {
			em().getTransaction().begin();
			family.setId(data.getId());
			family.setFullname(data.getFullname());
			family.setRelationship(data.getRelationship());
			family.setDegreeName(data.getDegreeName());
			family.setOccupation(data.getOccupation());
			family.setBirthDate(LocalDate.parse(data.getBirthDate().toString()));
			family.setBirthPlace(data.getBirthPlace());
			family.setUpdatedBy(principalService.getAuthPrincipal());
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			family.setCandidateUser(candidate);
			candidateFamilyDao.saveAndFlush(family);

			result = new UpdateResDto();
			result.setVersion(family.getVersion());
			result.setMessage("Family record updated!");

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}

	public DeleteResDto deleteFamily(String id) {
		final DeleteResDto response = new DeleteResDto();

		try {
			em().getTransaction().begin();
			final CandidateFamily family = candidateFamilyDao.getById(CandidateFamily.class, id);
			candidateFamilyDao.deleteById(CandidateFamily.class, family.getId());

			final String candidateFamilyDeleteAPI = "http://localhost:8080/candidate-family/deleteFamily/";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final HttpEntity<CandidateFamily> httpEntity = new HttpEntity<CandidateFamily>(headers);

			final ResponseEntity<CandidateFamily> responseAdmin = restTemplate.exchange(
					candidateFamilyDeleteAPI + family.getFamilyCode(), HttpMethod.DELETE, httpEntity,
					CandidateFamily.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				response.setMessage("Family Member has been removed");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Deletion Failed");
			}
		} catch (Exception e) {

		}

		return response;
	}
}
