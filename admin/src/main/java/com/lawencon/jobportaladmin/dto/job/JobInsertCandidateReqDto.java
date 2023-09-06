package com.lawencon.jobportaladmin.dto.job;

import java.math.BigDecimal;

public class JobInsertCandidateReqDto {

	private String jobName;
	private String companyId;
	private String startDate;
	private String endDate;
	private String description;
	private String hrId;
	private String picId;
	private BigDecimal expectedSalaryMin;
	private BigDecimal expectedSalaryMax;
	private String employmentTypeId;
	private String file;
	private String fileExtension;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmploymentTypeId() {
		return employmentTypeId;
	}

	public void setEmploymentTypeId(String employmentTypeId) {
		this.employmentTypeId = employmentTypeId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getHrId() {
		return hrId;
	}

	public void setHrId(String hrId) {
		this.hrId = hrId;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public BigDecimal getExpectedSalaryMin() {
		return expectedSalaryMin;
	}

	public void setExpectedSalaryMin(BigDecimal expectedSalaryMin) {
		this.expectedSalaryMin = expectedSalaryMin;
	}

	public BigDecimal getExpectedSalaryMax() {
		return expectedSalaryMax;
	}

	public void setExpectedSalaryMax(BigDecimal expectedSalaryMax) {
		this.expectedSalaryMax = expectedSalaryMax;
	}

}
