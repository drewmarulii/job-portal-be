package com.lawencon.jobportaladmin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name ="t_review_detail")
public class ReviewDetail extends BaseEntity{

	@OneToOne
	@JoinColumn(name ="review_id")
	private Review review;
	
	@OneToOne
	@JoinColumn(name ="question_id")
	private Question question;

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
}
