package com.lawencon.jobportaladmin.dto.job;

import java.util.List;

import com.lawencon.jobportaladmin.dto.assignedjobquestion.AssignedJobQuestionInsertReqDto;
import com.lawencon.jobportaladmin.dto.ownedbenefit.OwnedBenefitInsertReqDto;

public class JobInsertReqDto {

	private String jobName;
	private String jobCode;
	private String companyId;
	private String companyCode;
	private String startDate;
	private String endDate;
	private String description;
	private String hrId;
	private String picId;
	private String expectedSalaryMin;
	private String expectedSalaryMax;
	private List<OwnedBenefitInsertReqDto> benefits;
	private List<AssignedJobQuestionInsertReqDto> questions;
	private String employmentTypeId;
	private String employmentTypeCode;
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

	public List<OwnedBenefitInsertReqDto> getBenefits() {
		return benefits;
	}

	public void setBenefits(List<OwnedBenefitInsertReqDto> benefits) {
		this.benefits = benefits;
	}

	public List<AssignedJobQuestionInsertReqDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<AssignedJobQuestionInsertReqDto> questions) {
		this.questions = questions;
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


}
