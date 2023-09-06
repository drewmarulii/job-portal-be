package com.lawencon.jobportaladmin.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_hired")
public class Hired extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;
	
	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;
	
	@Column(name = "end_date", nullable = true)
	private LocalDate endDate;


	public Applicant getApplicant() {
		return applicant;
	}


	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
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



	

}
