package com.lawencon.jobportaladmin.dto.candidateworkexp;

import java.math.BigDecimal;

public class CandidateWorkExpInsertReqDto {
	
	private String workingCode;
	private String positionName;
	private String companyName;
	private String address;
	private String responsibility;
	private String reasonLeave;
	private BigDecimal lastSalary;
	private String startDate;
	private String endDate;
	private String candidateId;
	private String email;
	
	public String getWorkingCode() {
		return workingCode;
	}

	public void setWorkingCode(String workingCode) {
		this.workingCode = workingCode;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
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

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getReasonLeave() {
		return reasonLeave;
	}

	public void setReasonLeave(String reasonLeave) {
		this.reasonLeave = reasonLeave;
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

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getLastSalary() {
		return lastSalary;
	}

	public void setLastSalary(BigDecimal lastSalary) {
		this.lastSalary = lastSalary;
	}

}
