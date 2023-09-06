package com.lawencon.jobportaladmin.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_interview")
public class Interview extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;

	@Column(name = "interview_location", length = 50, nullable = false)
	private String interviewLocation;

	@Column(name = "interview_date", nullable = false)
	private LocalDateTime interviewDate;

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public String getInterviewLocation() {
		return interviewLocation;
	}

	public void setInterviewLocation(String interviewLocation) {
		this.interviewLocation = interviewLocation;
	}

	public LocalDateTime getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(LocalDateTime interviewDate) {
		this.interviewDate = interviewDate;
	}
}
