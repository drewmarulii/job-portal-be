package com.lawencon.jobportaladmin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name ="t_question_answer")
public class QuestionAnswer extends BaseEntity{

	@OneToOne
	@JoinColumn(name ="option_id")
	private QuestionOption questionOption;
	
	@OneToOne
	@JoinColumn(name ="candidate_id")
	private CandidateUser candidateUser;
	
	@OneToOne
	@JoinColumn(name ="question_id")
	private Question question;

	public QuestionOption getQuestionOption() {
		return questionOption;
	}

	public void setQuestionOption(QuestionOption questionOption) {
		this.questionOption = questionOption;
	}

	public CandidateUser getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUser candidateUser) {
		this.candidateUser = candidateUser;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
}
