package com.lawencon.jobportaladmin.dto.question;

import java.util.List;

import com.lawencon.jobportaladmin.dto.questionoption.QuestionOptionInsertReqDto;


public class QuestionInsertReqDto {

	private String questionDetail;
	private String questionCode;
	private List<QuestionOptionInsertReqDto> options;

	public String getQuestionDetail() {
		return questionDetail;
	}

	public void setQuestionDetail(String questionDetail) {
		this.questionDetail = questionDetail;
	}

	public List<QuestionOptionInsertReqDto> getOptions() {
		return options;
	}

	public void setOptions(List<QuestionOptionInsertReqDto> options) {
		this.options = options;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

}
