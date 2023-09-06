package com.lawencon.jobportaladmin.dto.assesment;

public class AssesmentInsertReqDto {

	private String applicantId;
	private String applicantCode;
	private String assesmentLocation;
	private String assesmentDate;

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getAssesmentLocation() {
		return assesmentLocation;
	}

	public void setAssesmentLocation(String assesmentLocation) {
		this.assesmentLocation = assesmentLocation;
	}

	public String getAssesmentDate() {
		return assesmentDate;
	}

	public void setAssesmentDate(String assesmentDate) {
		this.assesmentDate = assesmentDate;
	}

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

}
