package com.lawencon.jobportalcandidate.dto.question;

import java.util.List;

import com.lawencon.jobportalcandidate.dto.questionoption.QuestionOptionResDto;

public class QuestionResDto {

	private String id;
	private String questionDetail;
	private List<QuestionOptionResDto> options;

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

	public List<QuestionOptionResDto> getOptions() {
		return options;
	}

	public void setOptions(List<QuestionOptionResDto> options) {
		this.options = options;
	}

}
