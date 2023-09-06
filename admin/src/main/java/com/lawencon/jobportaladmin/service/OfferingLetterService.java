package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.lawencon.jobportaladmin.dao.ApplicantDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dao.HiringStatusDao;
import com.lawencon.jobportaladmin.dao.OfferingLetterDao;
import com.lawencon.jobportaladmin.dao.OwnedBenefitDao;
import com.lawencon.jobportaladmin.dao.UserDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.jasper.JasperReqDto;
import com.lawencon.jobportaladmin.dto.offeringletter.OfferingLetterInsertReqDto;
import com.lawencon.jobportaladmin.model.Applicant;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.model.HiringStatus;
import com.lawencon.jobportaladmin.model.OfferingLetter;
import com.lawencon.jobportaladmin.model.OwnedBenefit;
import com.lawencon.jobportaladmin.model.User;
import com.lawencon.jobportaladmin.util.BigDecimalUtil;
import com.lawencon.jobportaladmin.util.MoneyUtil;
import com.lawencon.security.principal.PrincipalService;
import com.lawencon.util.JasperUtil;

@Service
public class OfferingLetterService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private OfferingLetterDao offeringLetterDao;

	@Autowired
	private ApplicantDao applicantDao;

	@Autowired
	private HiringStatusDao hiringStatusDao;

	@Autowired
	private OwnedBenefitDao ownedBenefitDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PrincipalService<String> principalService;
	
	@Autowired
	private JasperUtil jasperUtil;

	public InsertResDto insertOfferingLetter(OfferingLetterInsertReqDto offeringData) {
		final InsertResDto resDto = new InsertResDto();

		try {
			em().getTransaction().begin();
			OfferingLetter offeringLetter = new OfferingLetter();
			final Applicant applicant = applicantDao.getById(Applicant.class, offeringData.getApplicantId());
			offeringData.setApplicantCode(applicant.getApplicantCode());

			offeringLetter.setAddress(offeringData.getAddress());
			offeringLetter.setSalary(BigDecimalUtil.parseToBigDecimal(offeringData.getSalary().toString()));
			offeringLetter.setApplicant(applicant);

			final HiringStatus hiringStatus = hiringStatusDao
					.getByCode(com.lawencon.jobportaladmin.constant.HiringStatus.OFFERING.statusCode);
			applicant.setStatus(hiringStatus);
			offeringData.setStatusCode(hiringStatus.getStatusCode());

			final List<OwnedBenefit> ownedBenefits = ownedBenefitDao.getByJob(applicant.getJob().getId());
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
					applicant.getCandidate().getId());
			
			final Map<String, Object> offeringDatas = new HashMap<>();
			
			offeringLetter = offeringLetterDao.save(offeringLetter);

			final String emailSubject = "Offering Letter: [" + applicant.getJob().getJobName() + "] at " + applicant.getJob().getCompany().getCompanyName();
			
			offeringDatas.put("fullname",candidate.getCandidateProfile().getFullname());
			offeringDatas.put("positionName", applicant.getJob().getJobName());
			offeringDatas.put("companyName", applicant.getJob().getCompany().getCompanyName());
			
			final User admin = userDao.getById(User.class, principalService.getAuthPrincipal());
			
			offeringDatas.put("adminName", admin.getProfile().getFullName());

			offeringData.setConvertedMoney(MoneyUtil.parseToRupiah(offeringData.getSalary()).toString());
			offeringDatas.put("salary", offeringData.getConvertedMoney());
			
			final List<JasperReqDto> jasperBenefits = new ArrayList<>();
			if (ownedBenefits.size() > 0) {
				for (int i = 0; i < ownedBenefits.size(); i++) {
					final JasperReqDto jasperBenefit = new JasperReqDto();
					jasperBenefit.setBenefitName(ownedBenefits.get(i).getBenefit().getBenefitName());
					jasperBenefits.add(jasperBenefit);
				}
			}

			final byte[] jasperData = jasperUtil.responseToByteArray(jasperBenefits, offeringDatas, "OfferingLetter");
		
			emailService.sendEmailOfferingLetter(emailSubject, candidate, offeringLetter, applicant, jasperData, "OfferingLetter");
			
			final String updateApplicantAPI = "http://localhost:8081/applicants";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<OfferingLetterInsertReqDto> applicantUpdate = RequestEntity.patch(updateApplicantAPI)
					.headers(headers).body(offeringData);
			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(applicantUpdate,
					UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				resDto.setId(offeringLetter.getId());
				resDto.setMessage("Insert Offering Letter Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Offering Letter Failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Insert Offering Letter Failed");
		}

		return resDto;
	}

}
