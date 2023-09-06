package com.lawencon.jobportaladmin.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportaladmin.dao.BlacklistDao;
import com.lawencon.jobportaladmin.dao.CandidateProfileDao;
import com.lawencon.jobportaladmin.dao.CandidateStatusDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.blacklist.BlacklistInsertReqDto;
import com.lawencon.jobportaladmin.model.Blacklist;
import com.lawencon.jobportaladmin.model.CandidateProfile;
import com.lawencon.jobportaladmin.model.CandidateStatus;
import com.lawencon.jobportaladmin.model.CandidateUser;

@Service
public class BlacklistService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BlacklistDao blacklistDao;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private CandidateProfileDao candidateProfileDao;
	
	@Autowired
	private CandidateStatusDao candidateStatusDao;
	

	public InsertResDto insertBlacklist(BlacklistInsertReqDto data) {
		final InsertResDto resDto = new InsertResDto();
		
		
		try {
			em().getTransaction().begin();
			
			Blacklist blacklist = new Blacklist();
			blacklist.setEmail(data.getCandidateEmail());
			blacklist.setNotes(data.getNotes());
			blacklist = blacklistDao.save(blacklist);
			
			CandidateUser candidateUser = candidateUserDao.getByEmail(data.getCandidateEmail());
			CandidateProfile candidateProfile = candidateProfileDao.getById(CandidateProfile.class, candidateUser.getCandidateProfile().getId());
			final CandidateStatus candidateStatus = candidateStatusDao.getByCode(com.lawencon.jobportaladmin.constant.CandidateStatus.BLACKLIST.typeCode);
			candidateProfile.setCandidateStatus(candidateStatus);
			data.setStatusCode(candidateStatus.getStatusCode());
			data.setCandidateEmail(candidateUser.getUserEmail());
			
			candidateProfile = candidateProfileDao.save(candidateProfile);
			candidateUser.setIsActive(false);
			candidateUser = candidateUserDao.save(candidateUser);			
			data.setIsActive(false);
			
			final String updateCandidateStatusAPI = "http://localhost:8081/candidate-user/blacklist";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			final RequestEntity<BlacklistInsertReqDto> candidateUpdate = RequestEntity.patch(updateCandidateStatusAPI)
					.headers(headers).body(data);
			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(candidateUpdate,
					UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				resDto.setId(blacklist.getId());
				resDto.setMessage("Insert Blacklist success");
				em().getTransaction().commit();

			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Assesment and Update Applicant Failed");
			}			
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Insert Blacklist failed");
		}
		
	return resDto;
	}

}
