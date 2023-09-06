package com.lawencon.jobportaladmin.dto.review;


public class ReviewUpdateScoreReqDto {

	private String applicantCode;
	private Integer scores;

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	public Integer getScores() {
		return scores;
	}

	public void setScores(Integer scores) {
		this.scores = scores;
	}
	
}
