package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="t_employee")
public class Employee extends BaseEntity{
	
	@Column(name = "employee_code",length = 5, nullable = false, unique = true)
	private String employeeCode;
	
	@OneToOne
	@JoinColumn(name="candidate_id")
	private CandidateUser candidate;
	
	@OneToOne
	@JoinColumn(name="job_id")
	private Job job;

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public CandidateUser getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateUser candidate) {
		this.candidate = candidate;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
	
	
}
