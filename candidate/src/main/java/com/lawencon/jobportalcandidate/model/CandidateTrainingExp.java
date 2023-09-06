package com.lawencon.jobportalcandidate.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_training_exp")
public class CandidateTrainingExp extends BaseEntity {
	
	@Column(name= "training_code",length = 5, nullable = false, unique = true)
	private String trainingCode;
	
	@Column(name = "organization_name", length = 20, nullable = false)
	private String organizationName;

	@Column(name = "training_name", length = 20, nullable = false)
	private String trainingName;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@OneToOne
	@JoinColumn(name = "user_id")
	private CandidateUser candidateUser;
	
	

	public String getTrainingCode() {
		return trainingCode;
	}

	public void setTrainingCode(String trainingCode) {
		this.trainingCode = trainingCode;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
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
