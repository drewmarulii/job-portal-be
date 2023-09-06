package com.lawencon.jobportalcandidate.dto.savedjob;

public class SavedJobResDto {

	private String id;
	private String jobId;
	private String jobName;
	private String jobPictureId;
	private String companyName;
	private String address;
	private String startDate;
	private String endDate;
	private String expectedSalaryMin;
	private String expectedSalaryMax;
	private String employmenTypeName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobPictureId() {
		return jobPictureId;
	}

	public void setJobPictureId(String jobPictureId) {
		this.jobPictureId = jobPictureId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getExpectedSalaryMin() {
		return expectedSalaryMin;
	}

	public void setExpectedSalaryMin(String expectedSalaryMin) {
		this.expectedSalaryMin = expectedSalaryMin;
	}

	public String getExpectedSalaryMax() {
		return expectedSalaryMax;
	}

	public void setExpectedSalaryMax(String expectedSalaryMax) {
		this.expectedSalaryMax = expectedSalaryMax;
	}

	public String getEmploymenTypeName() {
		return employmenTypeName;
	}

	public void setEmploymenTypeName(String employmenTypeName) {
		this.employmenTypeName = employmenTypeName;
	}


}
