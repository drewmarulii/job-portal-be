package com.lawencon.jobportaladmin.dto.review;

public class ReviewUpdateNotesReqDto {

	private String applicantId;
	private String notes;

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
