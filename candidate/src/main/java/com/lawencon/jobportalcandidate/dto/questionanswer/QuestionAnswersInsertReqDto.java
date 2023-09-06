package com.lawencon.jobportalcandidate.dto.questionanswer;

import java.util.List;

public class QuestionAnswersInsertReqDto {

	private List<QuestionAnswerInsertReqDto> answers;
	private Integer scores;
	private String applicantId;
	private String applicantCode;

	public List<QuestionAnswerInsertReqDto> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAnswerInsertReqDto> answers) {
		this.answers = answers;
	}

	public Integer getScores() {
		return scores;
	}

	public void setScores(Integer scores) {
		this.scores = scores;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}
	
}
