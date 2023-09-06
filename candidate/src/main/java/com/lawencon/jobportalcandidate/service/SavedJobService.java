package com.lawencon.jobportalcandidate.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dao.JobDao;
import com.lawencon.jobportalcandidate.dao.SavedJobDao;
import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.savedjob.SavedJobInsertReqDto;
import com.lawencon.jobportalcandidate.dto.savedjob.SavedJobResDto;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.model.Job;
import com.lawencon.jobportalcandidate.model.SavedJob;
import com.lawencon.jobportalcandidate.util.DateUtil;
import com.lawencon.jobportalcandidate.util.MoneyUtil;
import com.lawencon.security.principal.PrincipalService;

@Service
public class SavedJobService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private SavedJobDao savedJobDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private PrincipalService<String> principalService;

	public List<SavedJobResDto> getSavedJobByCandidate() {
		final List<SavedJobResDto> savedjobsDto = new ArrayList<>();
		final List<SavedJob> savedJobs = savedJobDao.getByCandidate(principalService.getAuthPrincipal());

		for (int i = 0; i < savedJobs.size(); i++) {
			final SavedJobResDto savedJob = new SavedJobResDto();
			savedJob.setId(savedJobs.get(i).getId());
			savedJob.setJobId(savedJobs.get(i).getJob().getId());
			savedJob.setJobName(savedJobs.get(i).getJob().getJobName());
			savedJob.setJobPictureId(savedJobs.get(i).getJob().getJobPicture().getId());
			savedJob.setCompanyName(savedJobs.get(i).getJob().getCompany().getCompanyName());
			savedJob.setAddress(savedJobs.get(i).getJob().getCompany().getAddress());
			savedJob.setStartDate(DateUtil.localDateToString(savedJobs.get(i).getJob().getStartDate()));
			savedJob.setEndDate(DateUtil.localDateToString(savedJobs.get(i).getJob().getEndDate()));
			savedJob.setExpectedSalaryMin(MoneyUtil.parseToRupiah(savedJobs.get(i).getJob().getExpectedSalaryMin()));
			savedJob.setExpectedSalaryMax(MoneyUtil.parseToRupiah(savedJobs.get(i).getJob().getExpectedSalaryMax()));
			savedJob.setEmploymenTypeName(savedJobs.get(i).getJob().getEmploymentType().getEmploymentTypeName());
			savedjobsDto.add(savedJob);
		}

		return savedjobsDto;
	}

	public InsertResDto insertSavedJob(SavedJobInsertReqDto mysavedjob) {
		

		InsertResDto result = null;
		
		try {
			em().getTransaction().begin();
			 SavedJob savedjob = new SavedJob();
			final Job job = jobDao.getById(Job.class, mysavedjob.getJobId());
			savedjob.setJob(job);
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, principalService.getAuthPrincipal());
			savedjob.setCandidateUser(candidate);
			savedjob.setCreatedBy(principalService.getAuthPrincipal());
			
			savedjob= savedJobDao.save(savedjob);

			result = new InsertResDto();
			result.setId(savedjob.getId());
			result.setMessage("Job has been added to your Saved Jobs");
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}
	
	public DeleteResDto removeSavedJob(String id) {
		final DeleteResDto response = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			final SavedJob savedJob = savedJobDao.getByJobAndPrincipal(id, principalService.getAuthPrincipal());
			savedJobDao.deleteById(SavedJob.class, savedJob.getId());
			
			response.setMessage("Job removed from your SavedList");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return response;
	}
}
