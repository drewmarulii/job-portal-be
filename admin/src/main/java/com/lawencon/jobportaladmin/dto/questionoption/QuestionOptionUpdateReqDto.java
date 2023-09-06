package com.lawencon.jobportaladmin.dto.questionoption;

public class QuestionOptionUpdateReqDto {

	private String id;
	private String optionLabel;
	private Boolean isCorrect;
	private String questionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	

}
