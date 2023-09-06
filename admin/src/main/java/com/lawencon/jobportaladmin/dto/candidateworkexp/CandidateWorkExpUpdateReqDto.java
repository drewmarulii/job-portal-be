package com.lawencon.jobportaladmin.dto.candidateworkexp;

public class CandidateWorkExpUpdateReqDto {
	private String id;
	private String positionName;
	private String companyName;
	private String address;
	private String responsibility;
	private String reasonLeave;
	private Float lastSalary;
	private String startDate;
	private String endDate;
	private String candidateId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Float getLastSalary() {
		return lastSalary;
	}

	public void setLastSalary(Float lastSalary) {
		this.lastSalary = lastSalary;
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
}
