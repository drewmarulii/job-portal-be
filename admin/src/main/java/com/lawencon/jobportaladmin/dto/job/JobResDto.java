package com.lawencon.jobportaladmin.dto.job;

public class JobResDto {

	private String id;
	private String jobName;
	private String companyName;
	private String address;
	private String startDate;
	private String endDate;
	private String expectedSalaryMin;
	private String expectedSalaryMax;
	private String employementTypeName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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

	public String getEmployementTypeName() {
		return employementTypeName;
	}

	public void setEmployementTypeName(String employementTypeName) {
		this.employementTypeName = employementTypeName;
	}

}
