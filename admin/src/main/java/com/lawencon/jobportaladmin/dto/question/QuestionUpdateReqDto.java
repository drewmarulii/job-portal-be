package com.lawencon.jobportaladmin.dto.question;

import java.util.List;

import com.lawencon.jobportaladmin.dto.questionoption.QuestionOptionUpdateReqDto;

public class QuestionUpdateReqDto {
	private String id;
	private String questionDetail;
	private String questionCode;
	private List<QuestionOptionUpdateReqDto> options;

	public String getQuestionDetail() {
		return questionDetail;
	}

	public void setQuestionDetail(String questionDetail) {
		this.questionDetail = questionDetail;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<QuestionOptionUpdateReqDto> getOptions() {
		return options;
	}

	public void setOptions(List<QuestionOptionUpdateReqDto> options) {
		this.options = options;
	}

}
