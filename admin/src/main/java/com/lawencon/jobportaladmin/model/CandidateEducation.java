package com.lawencon.jobportaladmin.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_education")
public class CandidateEducation extends BaseEntity {

	@Column(name = "education_code", nullable = false, unique = true)
	private String educationCode;
	
	@Column(name = "degree_name", length = 50, nullable = false)
	private String degreeName;

	@Column(name = "institution_name", length = 50, nullable = false)
	private String institutionName;

	@Column(name = "majors", length = 50, nullable = false)
	private String majors;

	@Column(name = "cgpa", nullable = false)
	private Float cgpa;

	@Column(name = "start_year", nullable = false)
	private LocalDate startYear;

	@Column(name = "end_year", nullable = false)
	private LocalDate endYear;

	@OneToOne
	@JoinColumn(name = "user_id")
	private CandidateUser candidateUser;

	public String getEducationCode() {
		return educationCode;
	}

	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}
	
	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getMajors() {
		return majors;
	}

	public void setMajors(String majors) {
		this.majors = majors;
	}

	public Float getCgpa() {
		return cgpa;
	}

	public void setCgpa(Float cgpa) {
		this.cgpa = cgpa;
	}

	public LocalDate getStartYear() {
		return startYear;
	}

	public void setStartYear(LocalDate startYear) {
		this.startYear = startYear;
	}

	public LocalDate getEndYear() {
		return endYear;
	}

	public void setEndYear(LocalDate endYear) {
		this.endYear = endYear;
	}

	public CandidateUser getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUser candidateUser) {
		this.candidateUser = candidateUser;
	}

}
