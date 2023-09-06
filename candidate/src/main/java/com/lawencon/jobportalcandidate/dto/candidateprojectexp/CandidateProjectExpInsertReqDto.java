package com.lawencon.jobportalcandidate.dto.candidateprojectexp;

public class CandidateProjectExpInsertReqDto {
	private String projectCode;
	private String projectName;
	private String projectUrl;
	private String description;
	private String startDate;
	private String endDate;
	private String email;

	
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
