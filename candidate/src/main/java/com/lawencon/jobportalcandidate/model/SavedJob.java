package com.lawencon.jobportalcandidate.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_saved_job")
public class SavedJob extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "job_id")
	private Job job;

	@OneToOne
	@JoinColumn(name = "user_id")
	private CandidateUser candidateUser;

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public CandidateUser getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUser candidateUser) {
		this.candidateUser = candidateUser;
	}

}
