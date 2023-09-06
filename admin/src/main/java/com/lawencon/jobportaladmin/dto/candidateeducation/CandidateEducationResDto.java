package com.lawencon.jobportaladmin.dto.candidateeducation;

public class CandidateEducationResDto {
	private String id;
	private String degreeName;
	private String instituitionName;
	private String majors;
	private Float cgpa;
	private String startYear;
	private String endYear;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getInstituitionName() {
		return instituitionName;
	}

	public void setInstituitionName(String instituitionName) {
		this.instituitionName = instituitionName;
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

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

}
