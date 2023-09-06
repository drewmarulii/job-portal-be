package com.lawencon.jobportaladmin.dto.questionoption;

public class QuestionOptionResDto {

	private String id;
	private String optionLabel;
	private Boolean isCorrect;
	
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

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	

}
