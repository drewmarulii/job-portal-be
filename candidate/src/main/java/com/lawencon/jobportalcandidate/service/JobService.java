package com.lawencon.jobportalcandidate.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.dao.AssignedJobQuestionDao;
import com.lawencon.jobportalcandidate.dao.CompanyDao;
import com.lawencon.jobportalcandidate.dao.EmploymentTypeDao;
import com.lawencon.jobportalcandidate.dao.FileDao;
import com.lawencon.jobportalcandidate.dao.JobDao;
import com.lawencon.jobportalcandidate.dao.QuestionDao;
import com.lawencon.jobportalcandidate.dao.SavedJobDao;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.job.JobInsertReqDto;
import com.lawencon.jobportalcandidate.dto.job.JobResDto;
import com.lawencon.jobportalcandidate.dto.job.JobUpdateReqDto;
import com.lawencon.jobportalcandidate.model.AssignedJobQuestion;
import com.lawencon.jobportalcandidate.model.Company;
import com.lawencon.jobportalcandidate.model.EmploymentType;
import com.lawencon.jobportalcandidate.model.File;
import com.lawencon.jobportalcandidate.model.Job;
import com.lawencon.jobportalcandidate.model.Question;
import com.lawencon.jobportalcandidate.util.BigDecimalUtil;
import com.lawencon.jobportalcandidate.util.DateUtil;
import com.lawencon.jobportalcandidate.util.MoneyUtil;
import com.lawencon.security.principal.PrincipalService;

@Service
public class JobService {

	@Autowired
	private JobDao jobDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private EmploymentTypeDao employmentTypeDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private AssignedJobQuestionDao assignedJobQuestionDao;
	
	@Autowired
	private SavedJobDao savedJobDao;
	
	@Autowired
	private PrincipalService<String> principalService;

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<JobResDto> getJobs() {
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
			job.setDescription(jobs.get(i).getDescription());
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMax()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());
			job.setFileId(jobs.get(i).getJobPicture().getId());
			job.setIsBookmark(savedJobDao.checkBookmark(jobs.get(i).getId(), principalService.getAuthPrincipal()));
			job.setCompanyPhotoId(jobs.get(i).getCompany().getPhoto().getId());
			jobsDto.add(job);
		}

		return jobsDto;
	}

	public List<JobResDto> getJobsByCompany(String code) {
		final List<JobResDto> jobsDto = new ArrayList<>();
		final List<Job> jobs = jobDao.getByCompany(code);
		
		System.out.println("Job length = "+ jobs.size());
		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setDescription(jobs.get(i).getDescription());
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMax()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());
			job.setFileId(jobs.get(i).getJobPicture().getId());
			if(jobs.get(i).getCompany().getPhoto()!=null) {
				
				job.setCompanyPhotoId(jobs.get(i).getCompany().getPhoto().getId());
			}
			jobsDto.add(job);
		}

		
		
		return jobsDto;
	}

	public List<JobResDto> getJobsBySalary(Float salary) {
		final List<JobResDto> jobsDto = new ArrayList<>();
		final List<Job> jobs = jobDao.getBySalary(salary);

		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setDescription(jobs.get(i).getDescription());
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMax()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());
			job.setFileId(jobs.get(i).getJobPicture().getId());
			job.setCompanyPhotoId(jobs.get(i).getCompany().getPhoto().getId());
			jobsDto.add(job);
		}

		return jobsDto;
	}
	
	public List<JobResDto> getTopThreeSalary() {
		final List<JobResDto> jobsDto = new ArrayList<>();
		final List<Job> jobs = jobDao.getByTopThreeSalary();

		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setDescription(jobs.get(i).getDescription());
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMax()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());
			job.setFileId(jobs.get(i).getJobPicture().getId());
			jobsDto.add(job);
		}

		return jobsDto;
	}

	public InsertResDto insertJob(JobInsertReqDto job) {

		final InsertResDto insertResDto = new InsertResDto();

		try {
			em().getTransaction().begin();

			Job newJob = new Job();
			newJob.setJobName(job.getJobName());
			newJob.setJobCode(job.getJobCode());

			final Company company = companyDao.getByCode(job.getCompanyCode());
			newJob.setCompany(company);

			newJob.setStartDate(DateUtil.parseStringToLocalDate(job.getStartDate().toString()));
			newJob.setEndDate(DateUtil.parseStringToLocalDate(job.getEndDate().toString()));
			newJob.setDescription(job.getDescription());
			newJob.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(job.getExpectedSalaryMin().toString()));
			newJob.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(job.getExpectedSalaryMax().toString()));

			final EmploymentType employmentType = employmentTypeDao.getByCode(job.getEmploymentTypeCode());
			newJob.setEmploymentType(employmentType);

			File photo = new File();
			photo.setFileName(job.getFile());
			photo.setFileExtension(job.getFileExtension());

			photo = fileDao.save(photo);
			newJob.setJobPicture(photo);
			newJob = jobDao.save(newJob);

			if (job.getQuestions() != null) {

				for (int i = 0; i < job.getQuestions().size(); i++) {
					final Question question = questionDao.getByCode(job.getQuestions().get(i).getQuestionCode());
					AssignedJobQuestion assignQuestion = new AssignedJobQuestion();
					assignQuestion.setQuestion(question);
					assignQuestion.setJob(newJob);
					assignedJobQuestionDao.save(assignQuestion);
				}
			}

			insertResDto.setId(newJob.getId());
			insertResDto.setMessage("Insert job success");
			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertResDto;

	}

	public JobResDto getById(String jobId) {
		final Job job = jobDao.getById(Job.class, jobId);
		final JobResDto jobDto = new JobResDto();

		jobDto.setId(job.getId());
		jobDto.setJobName(job.getJobName());
		jobDto.setCompanyName(job.getCompany().getCompanyName());
		jobDto.setAddress(job.getCompany().getAddress());
		jobDto.setStartDate(DateUtil.localDateToString(job.getStartDate()));
		jobDto.setEndDate(DateUtil.localDateToString(job.getEndDate()));
		jobDto.setDescription(job.getDescription());
		jobDto.setExpectedSalaryMin(MoneyUtil.parseToRupiah(job.getExpectedSalaryMin()));
		jobDto.setExpectedSalaryMax(MoneyUtil.parseToRupiah(job.getExpectedSalaryMax()));
		jobDto.setEmployementTypeName(job.getEmploymentType().getEmploymentTypeName());
		jobDto.setFileId(job.getJobPicture().getId());
		jobDto.setCompanyPhotoId(job.getCompany().getPhoto().getId());
		return jobDto;

	}
	
	public List<JobResDto> filter(String title,String location,BigDecimal salary) {
		final List<Job> jobs = jobDao.filterJob(title, location, salary);
		final List<JobResDto> jobsDto = new ArrayList<>();

		for (int i = 0; i < jobs.size(); i++) {
			final JobResDto job = new JobResDto();
			job.setId(jobs.get(i).getId());
			job.setJobName(jobs.get(i).getJobName());
			job.setCompanyName(jobs.get(i).getCompany().getCompanyName());
			job.setAddress(jobs.get(i).getCompany().getAddress());
			job.setStartDate(DateUtil.localDateToString(jobs.get(i).getStartDate()));
			job.setEndDate(DateUtil.localDateToString(jobs.get(i).getEndDate()));
			job.setDescription(jobs.get(i).getDescription());
			job.setExpectedSalaryMin(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMin()));
			job.setExpectedSalaryMax(MoneyUtil.parseToRupiah(jobs.get(i).getExpectedSalaryMax()));
			job.setEmployementTypeName(jobs.get(i).getEmploymentType().getEmploymentTypeName());
			job.setFileId(jobs.get(i).getJobPicture().getId());
			job.setCompanyPhotoId(jobs.get(i).getCompany().getPhoto().getId());
			jobsDto.add(job);
		}

		return jobsDto;
	}

	
	public UpdateResDto updateJob(JobUpdateReqDto data) {
		UpdateResDto resDto = null;
		
		try {
			em().getTransaction().begin();
			
			final Job jobByCode = jobDao.getByCode(data.getJobCode());
			Job job = jobDao.getById(Job.class, jobByCode.getId());
			
			if(data.getJobName()!= null && !data.getJobName().equals("")) {
				job.setJobName(data.getJobName());
			}
			
			if(data.getStartDate()!=null && !data.getStartDate().equals("")) {
				job.setStartDate(LocalDate.parse(data.getStartDate()));				
			}
			
			if(data.getEndDate()!= null && !data.getEndDate().equals("")) {
				job.setEndDate(LocalDate.parse(data.getEndDate()));		
			}
			
			if(data.getDescription()!=null && !data.getDescription().equals("")) {				
				job.setDescription(data.getDescription());
			}
			
			if(data.getExpectedSalaryMin()!=null && !data.getExpectedSalaryMin().equals("")) {				
				job.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(data.getExpectedSalaryMin()));
			}
			
			if(data.getExpectedSalaryMax()!=null && !data.getExpectedSalaryMax().equals("")) {
				job.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(data.getExpectedSalaryMax()));
			}

			if(data.getEmploymentTypeCode()!=null && !data.getEmploymentTypeCode().equals("")) {
				final EmploymentType type= employmentTypeDao.getByCode(data.getEmploymentTypeCode());
				job.setEmploymentType(type);
			}
			
			if(data.getFile()!= null && data.getFileExtension()!=null && !data.getFile().equals("") && !data.getFileExtension().equals("")) {
				File file = new File();
				file.setFileName(data.getFile());
				file.setFileExtension(data.getFileExtension());
				file = fileDao.save(file);
				final String oldPicture = job.getJobPicture().getId();
				jobDao.deleteById(Job.class, oldPicture);
				job.setJobPicture(file);
			}
			
			job = jobDao.save(job);
			resDto = new UpdateResDto();
			
			resDto.setMessage("Update job success");
			resDto.setVersion(job.getVersion());
			
			em().getTransaction().commit();
			
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		return resDto;
	}
	
	
}
