package com.lawencon.jobportalcandidate.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.lawencon.jobportalcandidate.constant.PersonTypes;
import com.lawencon.jobportalcandidate.dao.ApplicantDao;
import com.lawencon.jobportalcandidate.dao.CandidateProfileDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dao.HiringStatusDao;
import com.lawencon.jobportalcandidate.dao.JobDao;
import com.lawencon.jobportalcandidate.dao.PersonTypeDao;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.applicant.ApplicantInsertReqDto;
import com.lawencon.jobportalcandidate.dto.applicant.ApplicantResDto;
import com.lawencon.jobportalcandidate.dto.applicant.ApplicantUpdateReqDto;
import com.lawencon.jobportalcandidate.model.Applicant;
import com.lawencon.jobportalcandidate.model.CandidateProfile;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.model.HiringStatus;
import com.lawencon.jobportalcandidate.model.Job;
import com.lawencon.jobportalcandidate.model.PersonType;
import com.lawencon.jobportalcandidate.util.DateUtil;
import com.lawencon.jobportalcandidate.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class ApplicantService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private ApplicantDao applicantDao;
	@Autowired
	private JobDao jobDao;
	@Autowired
	private HiringStatusDao hiringStatusDao;
	@Autowired
	private PrincipalService<String> principalService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CandidateUserDao candidateUserDao;
	@Autowired
	private CandidateProfileDao candidateProfileDao;
	@Autowired
	private PersonTypeDao personTypeDao;

	public List<ApplicantResDto> getApplicantByCandidate() {
		final List<Applicant> applicantList = applicantDao.getApplicantByCandidate(principalService.getAuthPrincipal());
		final List<ApplicantResDto> applicantListRes = new ArrayList<>();
		for (int i = 0; i < applicantList.size(); i++) {
			final ApplicantResDto applicantRes = new ApplicantResDto();
			applicantRes.setId(applicantList.get(i).getId());
			applicantRes.setApplicantCode(applicantList.get(i).getApplicantCode());
			applicantRes.setAppliedDate(DateUtil.localDateTimeStampToString(applicantList.get(i).getAppliedDate()));
			applicantRes.setStatusId(applicantList.get(i).getStatus().getId());
			applicantRes.setStatusName(applicantList.get(i).getStatus().getStatusName());
			applicantRes.setJobId(applicantList.get(i).getJob().getId());
			applicantRes.setJobName(applicantList.get(i).getJob().getJobName());
			applicantRes.setCompanyName(applicantList.get(i).getJob().getCompany().getCompanyName());
			applicantRes.setCompanyPhotoId(applicantList.get(i).getJob().getCompany().getPhoto().getId());
			applicantListRes.add(applicantRes);
		}
		return applicantListRes;
	}

	public InsertResDto insertApplicant(ApplicantInsertReqDto data) {
		final InsertResDto insertRes = new InsertResDto();

		try {
			em().getTransaction().begin();
			final LocalDateTime currentDate = LocalDateTime.now();

			Applicant applicant = new Applicant();
			applicant.setApplicantCode(GenerateCode.generateCode());
			data.setApplicantCode(applicant.getApplicantCode());

			applicant.setAppliedDate(currentDate);
			data.setAppliedDate(currentDate.toString());

			final Job job = jobDao.getById(Job.class, data.getJobId());
			applicant.setJob(job);
			data.setJobCode(job.getJobCode());

			final HiringStatus hiringStatus = hiringStatusDao
					.getByCode(com.lawencon.jobportalcandidate.constant.HiringStatus.APPLIED.statusCode);
			applicant.setStatus(hiringStatus);
			data.setStatusCode(hiringStatus.getStatusCode());

			final String id = principalService.getAuthPrincipal();

			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, id);
			applicant.setCandidate(candidate);
			data.setCandidateEmail(candidate.getUserEmail());

			applicant = applicantDao.save(applicant);

			final String applicantInsertAdminAPI = "http://localhost:8080/applicants";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<ApplicantInsertReqDto> applicantInsert = RequestEntity.post(applicantInsertAdminAPI)
					.headers(headers).body(data);

			final ResponseEntity<InsertResDto> responseAdmin = restTemplate.exchange(applicantInsert,
					InsertResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.CREATED)) {
				insertRes.setId(applicant.getId());
				insertRes.setMessage("Applicant Insert Success");
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

	public UpdateResDto updateApplicant(ApplicantUpdateReqDto updateData) {
		final UpdateResDto resDto = new UpdateResDto();

		try {
			em().getTransaction().begin();

			Applicant applicant = applicantDao.getByCode(updateData.getApplicantCode());

			if (applicant.getStatus().getStatusCode()
					.equals(com.lawencon.jobportalcandidate.constant.HiringStatus.OFFERING.statusCode)) {
				CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class,
						applicant.getCandidate().getId());
				final PersonType employeeType = personTypeDao.getByCode(PersonTypes.EMPLOYEE.typeCode);
				CandidateProfile candidateProfile = candidateProfileDao.getById(CandidateProfile.class, candidateUser.getCandidateProfile().getId());
				candidateProfile.setPersonType(employeeType);
				candidateProfile = candidateProfileDao.save(candidateProfile);
			}
			
			final HiringStatus hiringStatus = hiringStatusDao.getByCode(updateData.getStatusCode());

			applicant.setStatus(hiringStatus);
			applicant = applicantDao.saveAndFlush(applicant);

			resDto.setVersion(applicant.getVersion());
			resDto.setMessage("Update Application Success");

			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Update Applicant Failed");

		}

		return resDto;

	}

}
