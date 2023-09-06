package com.lawencon.jobportalcandidate.dto.job;

import java.math.BigDecimal;
import java.util.List;

import com.lawencon.jobportalcandidate.dto.assignedjobquestion.AssignedJobQuestionInsertReqDto;

public class JobInsertReqDto {

	private String jobName;
	private String jobCode;
	private String companyCode;
	private String startDate;
	private String endDate;
	private String description;
	private List<AssignedJobQuestionInsertReqDto> questions;
	private BigDecimal expectedSalaryMin;
	private BigDecimal expectedSalaryMax;
	private String employmentTypeCode;
	private String file;
	private String fileExtension;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
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

	

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getEmploymentTypeCode() {
		return employmentTypeCode;
	}

	public void setEmploymentTypeCode(String employmentTypeCode) {
		this.employmentTypeCode = employmentTypeCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public List<AssignedJobQuestionInsertReqDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<AssignedJobQuestionInsertReqDto> questions) {
		this.questions = questions;
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
