package com.lawencon.jobportaladmin.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_project_exp")
public class CandidateProjectExp extends BaseEntity {
	
	@Column(name = "project_code",length = 5, nullable = false, unique = true)
	private String projectCode;
	
	@Column(name = "project_name", length = 30, nullable = false)
	private String projectName;

	@Column(name = "project_url", nullable = true)
	private String projectUrl;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@OneToOne
	@JoinColumn(name = "user_id")
	private CandidateUser candidateUser;
	
	

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public CandidateUser getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUser candidateUser) {
		this.candidateUser = candidateUser;
	}

}
