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
import com.lawencon.jobportaladmin.constant.PersonTypes;
import com.lawencon.jobportaladmin.dao.ApplicantDao;
import com.lawencon.jobportaladmin.dao.CandidateProfileDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dao.EmployeeDao;
import com.lawencon.jobportaladmin.dao.HiredDao;
import com.lawencon.jobportaladmin.dao.HiringStatusDao;
import com.lawencon.jobportaladmin.dao.JobDao;
import com.lawencon.jobportaladmin.dao.PersonTypeDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.hired.HiredInsertReqDto;
import com.lawencon.jobportaladmin.model.Applicant;
import com.lawencon.jobportaladmin.model.CandidateProfile;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.model.Employee;
import com.lawencon.jobportaladmin.model.Hired;
import com.lawencon.jobportaladmin.model.HiringStatus;
import com.lawencon.jobportaladmin.model.Job;
import com.lawencon.jobportaladmin.model.PersonType;
import com.lawencon.jobportaladmin.util.DateUtil;
import com.lawencon.jobportaladmin.util.GenerateCode;

@Service
public class HiredService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private HiredDao hiredDao;

	@Autowired
	private ApplicantDao applicantDao;

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private CandidateProfileDao candidateProfileDao;
	
	@Autowired
	private PersonTypeDao personTypeDao;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HiringStatusDao hiringStatusDao;
	
	@Autowired
	private EmailService emailService;
	
	public InsertResDto insertHired(HiredInsertReqDto hiredData) {
		final InsertResDto resDto = new InsertResDto();

		try {
			em().getTransaction().begin();
			Applicant applicant = applicantDao.getById(Applicant.class, hiredData.getApplicantId());
			
			Hired hired = new Hired();
			hired.setApplicant(applicant);
			hired.setStartDate(DateUtil.parseStringToLocalDate(hiredData.getStartDate()));

			if (hiredData.getEndDate() != null) {
				hired.setEndDate(DateUtil.parseStringToLocalDate(hiredData.getEndDate()));
			}

			hired = hiredDao.save(hired);
			
			final HiringStatus hiringStatus = hiringStatusDao.getByCode(com.lawencon.jobportaladmin.constant.HiringStatus.HIRED.statusCode);
			applicant.setStatus(hiringStatus);
			
			hiredData.setApplicantCode(applicant.getApplicantCode());
			hiredData.setStatusCode(hiringStatus.getStatusCode());
			
			final Employee employee = new Employee();
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class,applicant.getCandidate().getId());
			final Job job = jobDao.getById(Job.class, applicant.getJob().getId());
			employee.setCandidate(candidateUser);
			employee.setJob(job);
			employee.setEmployeeCode(GenerateCode.generateCode());
			employeeDao.save(employee);
			
			CandidateProfile candidateProfile = candidateProfileDao.getById(CandidateProfile.class, candidateUser.getCandidateProfile().getId());
			final PersonType personType = personTypeDao.getByCode(PersonTypes.EMPLOYEE.typeCode);
			candidateProfile.setPersonType(personType);
			
			candidateProfile = candidateProfileDao.saveAndFlush(candidateProfile);
			
			applicant = applicantDao.saveAndFlush(applicant);
			
			final String emailSubject = "Here’s to your new career at " + applicant.getJob().getCompany().getCompanyName() + " [" + applicant.getJob().getJobName() + "]";

			emailService.sendEmailNewEmployee(candidateUser, emailSubject, job, hired);
			final String updateApplicantAPI = "http://localhost:8081/applicants";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());
			 
			final RequestEntity<HiredInsertReqDto> applicantUpdate = RequestEntity.patch(updateApplicantAPI).headers(headers).body(hiredData);
			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(applicantUpdate, UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				
				resDto.setId(hired.getId());
				resDto.setMessage("Insert Hired & Employee Success");
				
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");
			}
			
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Insert Hired Failed");
		}

		return resDto;
	}

}
