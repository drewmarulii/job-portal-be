package com.lawencon.jobportaladmin.service;

import java.time.LocalDate;
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
import com.lawencon.jobportaladmin.dao.AssignedJobQuestionDao;
import com.lawencon.jobportaladmin.dao.BenefitDao;
import com.lawencon.jobportaladmin.dao.CompanyDao;
import com.lawencon.jobportaladmin.dao.EmploymentTypeDao;
import com.lawencon.jobportaladmin.dao.FileDao;
import com.lawencon.jobportaladmin.dao.JobDao;
import com.lawencon.jobportaladmin.dao.OwnedBenefitDao;
import com.lawencon.jobportaladmin.dao.QuestionDao;
import com.lawencon.jobportaladmin.dao.UserDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.job.JobDetailResDto;
import com.lawencon.jobportaladmin.dto.job.JobInsertReqDto;
import com.lawencon.jobportaladmin.dto.job.JobResDto;
import com.lawencon.jobportaladmin.dto.job.JobUpdateReqDto;
import com.lawencon.jobportaladmin.model.AssignedJobQuestion;
import com.lawencon.jobportaladmin.model.Benefit;
import com.lawencon.jobportaladmin.model.Company;
import com.lawencon.jobportaladmin.model.EmploymentType;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.Job;
import com.lawencon.jobportaladmin.model.OwnedBenefit;
import com.lawencon.jobportaladmin.model.Question;
import com.lawencon.jobportaladmin.model.User;
import com.lawencon.jobportaladmin.util.BigDecimalUtil;
import com.lawencon.jobportaladmin.util.DateUtil;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.jobportaladmin.util.MoneyUtil;
import com.lawencon.security.principal.PrincipalService;

@Service
public class JobService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private JobDao jobDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private EmploymentTypeDao employmentTypeDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private PrincipalService<String> principalService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BenefitDao benefitDao;

	@Autowired
	private OwnedBenefitDao ownedBenefitDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private AssignedJobQuestionDao assignedJobQuestionDao;

	public List<JobResDto> getAllJobs() {
		final List<JobResDto> jobsDto = new ArrayList<>();
		final List<Job> jobs = jobDao.getAll(Job.class);

		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMax()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());

			jobsDto.add(job);
		}

		return jobsDto;
	}

	public List<JobResDto> getByCompany(String code) {
		final List<JobResDto> jobsDto = new ArrayList<>();
		final List<Job> jobs = jobDao.getByCompany(code);

		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());

			jobsDto.add(job);
		}

		return jobsDto;
	}

	public List<JobResDto> getByPrincipal() {
		final List<JobResDto> jobsDto = new ArrayList<>();
		final List<Job> jobs = jobDao.getByPerson(principalService.getAuthPrincipal());
		
		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());
			jobsDto.add(job);
		}
		return jobsDto;
	}

	public InsertResDto insertJob(JobInsertReqDto jobDto) {

		InsertResDto result = new InsertResDto();

		try {
			em().getTransaction().begin();
			if("".equals(jobDto.getFile())|| "".equals(jobDto.getFileExtension())) {
				em().getTransaction().rollback();
				throw new NullPointerException("File is Empty");
			}
			Job job = new Job();
			job.setJobName(jobDto.getJobName());

			final Company company = companyDao.getById(Company.class, jobDto.getCompanyId());
			job.setCompany(company);
			jobDto.setCompanyCode(company.getCompanyCode());

			job.setStartDate(DateUtil.parseStringToLocalDate(jobDto.getStartDate()));
			job.setEndDate(DateUtil.parseStringToLocalDate(jobDto.getEndDate()));
			job.setDescription(jobDto.getDescription());
			job.setJobCode(GenerateCode.generateCode());

			jobDto.setJobCode(job.getJobCode());

			final User hr = userDao.getById(User.class, jobDto.getHrId());
			final User pic = userDao.getById(User.class, jobDto.getPicId());
			job.setHr(hr);
			job.setPic(pic);
			job.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(jobDto.getExpectedSalaryMin()));
			job.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(jobDto.getExpectedSalaryMax()));

			final EmploymentType type = employmentTypeDao.getById(EmploymentType.class, jobDto.getEmploymentTypeId());
			job.setEmploymentType(type);
			jobDto.setEmploymentTypeCode(type.getEmploymentTypeCode());
			
			File file = new File();
			file.setFileName(jobDto.getFile());
			file.setFileExtension(jobDto.getFileExtension());
			file = fileDao.save(file);
			job.setJobPicture(file);
			job = jobDao.save(job);

			if (jobDto.getBenefits() != null) {
				for (int i = 0; i < jobDto.getBenefits().size(); i++) {

					OwnedBenefit ownedBenefit = new OwnedBenefit();
					final Benefit benefit = benefitDao.getById(Benefit.class,
							jobDto.getBenefits().get(i).getBenefitId());
					ownedBenefit.setBenefit(benefit);
					ownedBenefit.setJob(job);
					ownedBenefitDao.save(ownedBenefit);
				}
			}

			if (jobDto.getQuestions() != null) {
				for (int i = 0; i < jobDto.getQuestions().size(); i++) {

					AssignedJobQuestion assignQuestion = new AssignedJobQuestion();
					final Question question = questionDao.getById(Question.class,
							jobDto.getQuestions().get(i).getQuestionId());
					assignQuestion.setQuestion(question);
					assignQuestion.setJob(job);
					jobDto.getQuestions().get(i).setQuestionCode(question.getQuestionCode());
					assignedJobQuestionDao.save(assignQuestion);
				}
			}

			final String jobInsertCandidateAPI = "http://localhost:8081/jobs";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<JobInsertReqDto> jobInsert = RequestEntity.post(jobInsertCandidateAPI).headers(headers)
					.body(jobDto);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(jobInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				result.setId(job.getId());
				result.setMessage("New job vacancy added!");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");

			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return result;
	}

	public UpdateResDto updateJob(JobUpdateReqDto jobDto) {
		

		UpdateResDto result = null;

		try {
			em().getTransaction().begin();
			Job job = jobDao.getById(Job.class, jobDto.getId());
			jobDto.setJobCode(job.getJobCode());
			
			if(jobDto.getJobName()!=null && !jobDto.getJobName().equals("")) {
				job.setJobName(jobDto.getJobName());			
			}
			
			if(jobDto.getStartDate()!=null && !jobDto.getStartDate().equals("")) {
				job.setStartDate(LocalDate.parse(jobDto.getStartDate()));				
			}
			
			if(jobDto.getEndDate()!=null && !jobDto.getEndDate().equals("")) {
				job.setEndDate(LocalDate.parse(jobDto.getEndDate()));		
			}
			
			if(jobDto.getDescription()!=null && !jobDto.getDescription().equals("")) {				
				job.setDescription(jobDto.getDescription());
			}
			
			if(jobDto.getExpectedSalaryMin()!=null && !jobDto.getExpectedSalaryMin().equals("")) {				
				job.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(jobDto.getExpectedSalaryMin()));
			}
			
			if(jobDto.getExpectedSalaryMax()!=null && !jobDto.getExpectedSalaryMax().equals("")) {
				job.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(jobDto.getExpectedSalaryMax()));
			}

			if(jobDto.getEmploymentTypeId()!=null && !jobDto.getEmploymentTypeId().equals("")) {
				final EmploymentType type = employmentTypeDao.getById(EmploymentType.class, jobDto.getEmploymentTypeId());
				job.setEmploymentType(type);
				jobDto.setEmploymentTypeCode(type.getEmploymentTypeCode());
			}
			
			if (jobDto.getFile() != null && !jobDto.getFile().equals("") && jobDto.getFileExtension()!=null && !jobDto.getFileExtension().equals("")) {
				File file = new File();
				file.setFileName(jobDto.getFile());
				file.setFileExtension(jobDto.getFileExtension());
				file.setCreatedBy(principalService.getAuthPrincipal());
				file = fileDao.save(file);
				String oldPicture = job.getPic().getId();
				jobDao.deleteById(Job.class, oldPicture);
				job.setJobPicture(file);
			}
			
			job = jobDao.saveAndFlush(job);
			
			final String jobUpdateAPI = "http://localhost:8081/jobs";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<JobUpdateReqDto> jobUpdate = RequestEntity.patch(jobUpdateAPI)
					.headers(headers).body(jobDto);

			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(jobUpdate,
					UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				result = new UpdateResDto();
				result.setVersion(job.getVersion());
				result.setMessage("Job Updated!");
				em().getTransaction().commit();
				
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");
			}
			
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return result;
	}

	public List<JobResDto> getByHr(String id) {
		final List<JobResDto> jobsDto = new ArrayList<>();
		final List<Job> jobs = jobDao.getByAssignedHR(id);

		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());

			jobsDto.add(job);
		}

		return jobsDto;
	}

	public List<JobResDto> getByPic() {
		final List<JobResDto> jobsDto = new ArrayList<>();
		final List<Job> jobs = jobDao.getByAssignedPIC(principalService.getAuthPrincipal());

		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());

			jobsDto.add(job);
		}

		return jobsDto;
	}

	public JobDetailResDto getDetail(String id) {
		final JobDetailResDto job = new JobDetailResDto();
		final Job jobDb = jobDao.getById(Job.class, id);
		
		job.setId(jobDb.getId());
		job.setJobName(jobDb.getJobName());
		job.setCompanyId(jobDb.getCompany().getId());
		job.setCompanyName(jobDb.getCompany().getCompanyName());
		job.setAddress(jobDb.getCompany().getAddress());
		job.setStartDate(DateUtil.localDateToString(jobDb.getStartDate()));
		job.setDescription(jobDb.getDescription());
		job.setEndDate(DateUtil.localDateToString(jobDb.getEndDate()));
		job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobDb.getExpectedSalaryMin()));
		job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobDb.getExpectedSalaryMax()));
		job.setEmploymentTypeId(jobDb.getEmploymentType().getId());
		job.setEmployementTypeName(jobDb.getEmploymentType().getEmploymentTypeName());
		job.setFileId(jobDb.getJobPicture().getId());
		job.setPicId(jobDb.getPic().getId());
		job.setHrId(jobDb.getHr().getId());
		job.setCompanyPhotoId(jobDb.getCompany().getPhoto().getId());
		job.setCreatedBy(jobDb.getCreatedBy());
		return job;
	}
	
}
