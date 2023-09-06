package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name ="t_question")
public class Question extends BaseEntity{

	@Column(name ="question_code", length = 5, nullable = false,unique = true)
	private String questionCode;
	
	@Column(name ="question_detail",  nullable = false)
	private String questionDetail;

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getQuestionDetail() {
		return questionDetail;
	}

	public void setQuestionDetail(String questionDetail) {
		this.questionDetail = questionDetail;
	}
}
