package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name ="t_review")
public class Review extends BaseEntity{

	@Column(name ="notes",  nullable = false)
	private String notes;
	
	@Column(name ="score",  nullable = false)
	private Float score;
	
	@OneToOne
	@JoinColumn(name ="applicant_id")
	private Applicant applicant;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	
}
