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
import com.lawencon.jobportalcandidate.dao.CandidateAddressDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidateaddress.CandidateAddressInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateaddress.CandidateAddressResDto;
import com.lawencon.jobportalcandidate.dto.candidateaddress.CandidateAddressUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateAddress;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateAddressService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private CandidateAddressDao candidateAddressDao;

	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateAddressResDto> getAllByCandidate(String id) {
		final List<CandidateAddressResDto> candidateAddressResList = new ArrayList<>();
		final List<CandidateAddress> candidateAddress = candidateAddressDao.getByCandidateId(id);
		for (int i = 0; i < candidateAddress.size(); i++) {
			final CandidateAddressResDto addressRes = new CandidateAddressResDto();
			addressRes.setAddress(candidateAddress.get(i).getAddress());
			addressRes.setCity(candidateAddress.get(i).getCity());
			addressRes.setCountry(candidateAddress.get(i).getCountry());
			addressRes.setPostalCode(candidateAddress.get(i).getPostalCode());
			addressRes.setProvince(candidateAddress.get(i).getProvince());
			addressRes.setResidenceType(candidateAddress.get(i).getResidenceType());
			addressRes.setId(candidateAddress.get(i).getId());
			candidateAddressResList.add(addressRes);
		}
		return candidateAddressResList;
	}

	public InsertResDto insertCandidateAddress(CandidateAddressInsertReqDto data) {
		final InsertResDto insertResDto = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateAddress candidateAddress = new CandidateAddress();
			
			candidateAddress.setAddressCode(GenerateCode.generateCode());
			data.setAddressCode(candidateAddress.getAddressCode());
			candidateAddress.setAddress(data.getAddress());
			candidateAddress.setCity(data.getCity());
			candidateAddress.setCountry(data.getCountry());
			candidateAddress.setProvince(data.getProvince());
			candidateAddress.setPostalCode(data.getPostalCode());
			candidateAddress.setResidenceType(data.getResidenceType());

			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class,
					principalService.getAuthPrincipal());
			data.setEmail(candidateUser.getUserEmail());
			candidateAddress.setCandidateUser(candidateUser);
			candidateAddress.setCreatedBy(principalService.getAuthPrincipal());

			candidateAddressDao.save(candidateAddress);

			final String candidateAddressAPI = "http://localhost:8080/candidate-address";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<CandidateAddressInsertReqDto> candidateAddressInsert = RequestEntity
					.post(candidateAddressAPI).headers(headers).body(data);

			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(candidateAddressInsert,
					InsertResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				insertResDto.setId(candidateAddress.getId());
				insertResDto.setMessage("Insert Candidate Address Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Insert Failed");

		}

		return insertResDto;
	}

	public UpdateResDto updateCandidateAdress(CandidateAddressUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final CandidateAddress candidateAddress = candidateAddressDao.getById(CandidateAddress.class, data.getId());
			candidateAddress.setAddress(data.getAddress());
			candidateAddress.setCity(data.getCity());
			candidateAddress.setCountry(data.getCountry());
			candidateAddress.setProvince(data.getProvince());
			candidateAddress.setPostalCode(data.getPostalCode());
			candidateAddress.setResidenceType(data.getResidenceType());
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, data.getCandidateId());
			candidateAddress.setCandidateUser(candidateUser);
			candidateAddress.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateAddress candidateAddressId = candidateAddressDao.save(candidateAddress);

			updateResDto.setMessage("Update Candidate Address Success");
			updateResDto.setVersion(candidateAddressId.getVersion());
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return updateResDto;
	}

	public DeleteResDto deleteCandidateAddress(String id) {
		final DeleteResDto deleteRes = new DeleteResDto();
		try {
			em().getTransaction().begin();
			final CandidateAddress address = candidateAddressDao.getById(CandidateAddress.class, id);
			candidateAddressDao.deleteById(CandidateAddress.class, address.getId());
			
			final String candidateAddressAPI = "http://localhost:8080/candidate-address/deleteAddress/";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final HttpEntity<CandidateAddress> httpEntity = new HttpEntity<CandidateAddress>(headers);

			final ResponseEntity<CandidateAddress> responseAdmin = restTemplate.exchange(
					candidateAddressAPI+address.getAddressCode(), HttpMethod.DELETE, httpEntity, CandidateAddress.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				deleteRes.setMessage("Delete Candidate Address Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}
						
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return deleteRes;
	}
}
