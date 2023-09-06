package com.lawencon.jobportalcandidate.dto.assignedjobquestion;

public class AssignedJobQuestionResDto {
	private String id;
	private String questionId;
	private String questionDetail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionDetail() {
		return questionDetail;
	}

	public void setQuestionDetail(String questionDetail) {
		this.questionDetail = questionDetail;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
}
