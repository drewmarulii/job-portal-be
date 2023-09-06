package com.lawencon.jobportaladmin.dto.employee;

public class EmployeeResDto {
	
	private String id;
	private String candidateName;
	private String candidateEmail;
	private String phoneNumber;
	private String jobName;
	private String companyUrl;
	private String employmentTypeName;
	private String createdBy;
	

	public String getId() {
		return id;	
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getEmploymentTypeName() {
		return employmentTypeName;
	}

	public void setEmploymentTypeName(String employmentTypeName) {
		this.employmentTypeName = employmentTypeName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
