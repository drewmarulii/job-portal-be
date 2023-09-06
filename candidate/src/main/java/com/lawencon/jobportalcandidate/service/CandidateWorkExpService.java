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
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dao.CandidateWorkExpDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidateworkexp.CandidateWorkExpInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateworkexp.CandidateWorkExpResDto;
import com.lawencon.jobportalcandidate.dto.candidateworkexp.CandidateWorkExpUpdateReqDto;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.model.CandidateWorkExp;
import com.lawencon.jobportalcandidate.util.BigDecimalUtil;
import com.lawencon.jobportalcandidate.util.DateUtil;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.jobportalcandidate.util.MoneyUtil;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateWorkExpService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CandidateWorkExpDao candidateWorkExpDao;

	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateWorkExpResDto> getWorkByCandidate(String id) {
		final List<CandidateWorkExpResDto> worksDto = new ArrayList<>();
		final List<CandidateWorkExp> works = candidateWorkExpDao.getByCandidate(id);

		for (int i = 0; i < works.size(); i++) {
			final CandidateWorkExpResDto work = new CandidateWorkExpResDto();
			work.setId(works.get(i).getId());
			work.setPositionName(works.get(i).getPositionName());
			work.setCompanyName(works.get(i).getCompanyName());
			work.setAddress(works.get(i).getAddress());
			work.setResponsibility(works.get(i).getResponsibility());
			work.setReasonLeave(works.get(i).getReasonLeave());
			work.setLastSalary(MoneyUtil.parseToRupiah(works.get(i).getLastSalary()));
			work.setStartDate(DateUtil.localDateToString(works.get(i).getStartDate()));
			work.setEndDate(DateUtil.localDateToString(works.get(i).getEndDate()));

			worksDto.add(work);
		}

		return worksDto;
	}

	public InsertResDto insertWorksExperience(CandidateWorkExpInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateWorkExp work = new CandidateWorkExp();
			work.setWorkingCode(GenerateCode.generateCode());
			data.setWorkingCode(work.getWorkingCode());
			work.setPositionName(data.getPositionName());
			work.setCompanyName(data.getCompanyName());
			work.setAddress(data.getAddress());
			work.setResponsibility(data.getResponsibility());
			work.setReasonLeave(data.getReasonLeave());
			work.setLastSalary(BigDecimalUtil.parseToBigDecimal(data.getLastSalary().toString()));
			work.setStartDate(DateUtil.parseStringToLocalDate(data.getStartDate()));
			work.setEndDate(DateUtil.parseStringToLocalDate(data.getEndDate()));

			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			data.setEmail(candidate.getUserEmail());
			work.setCandidateUser(candidate);
			work.setCreatedBy(principalService.getAuthPrincipal());

			candidateWorkExpDao.save(work);
			
			final String candidateWorkAPI = "http://localhost:8080/candidate-works";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			headers.setBearerAuth(JwtConfig.get());
			
			final RequestEntity<CandidateWorkExpInsertReqDto> candidateWorkInsert = RequestEntity
					.post(candidateWorkAPI).headers(headers).body(data);
			
			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(candidateWorkInsert,
					InsertResDto.class);
			
			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(work.getId());
				result.setMessage("Working Experience record added!");
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
	
	public UpdateResDto updateWorksExperience(CandidateWorkExpUpdateReqDto data) {
		final CandidateWorkExp work = candidateWorkExpDao.getById(CandidateWorkExp.class, data.getId());

		UpdateResDto result = null;

		try {
			em().getTransaction().begin();
			work.setId(data.getId());
			work.setPositionName(data.getPositionName());
			work.setCompanyName(data.getCompanyName());
			work.setAddress(data.getAddress());
			work.setResponsibility(data.getResponsibility());
			work.setReasonLeave(data.getReasonLeave());
			work.setLastSalary(BigDecimalUtil.parseToBigDecimal(data.getLastSalary().toString()));
			work.setStartDate(DateUtil.parseStringToLocalDate(data.getStartDate()));
			work.setEndDate(DateUtil.parseStringToLocalDate(data.getEndDate()));

			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			work.setCandidateUser(candidate);
			work.setUpdatedBy(principalService.getAuthPrincipal());

			candidateWorkExpDao.saveAndFlush(work);

			result = new UpdateResDto();
			result.setVersion(work.getVersion());
			result.setMessage("Working Experience record updated!");

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}
	
	public DeleteResDto deleteWorkExperience(String id) {
		final DeleteResDto response = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			final CandidateWorkExp work = candidateWorkExpDao.getById(CandidateWorkExp.class, id);
			candidateWorkExpDao.deleteById(CandidateWorkExp.class, work.getId());
			
			final String candidateWorkingAPI = "http://localhost:8080/candidate-works/deleteWork/";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			
			final HttpEntity<CandidateWorkExp> httpEntity = new HttpEntity<CandidateWorkExp>(headers);

			final ResponseEntity<CandidateWorkExp> responseAdmin = restTemplate.exchange(
					candidateWorkingAPI+work.getWorkingCode(), HttpMethod.DELETE, httpEntity, CandidateWorkExp.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				response.setMessage("Delete Candidate Working Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Deletion Failed");
			}
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return response;
	}
}

