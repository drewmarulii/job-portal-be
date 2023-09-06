package com.lawencon.jobportalcandidate.dto.question;

import java.util.List;

public class QuestionsInsertReqDto {

	private List<QuestionInsertReqDto> newQuestions;

	public List<QuestionInsertReqDto> getNewQuestions() {
		return newQuestions;
	}

	public void setNewQuestions(List<QuestionInsertReqDto> newQuestions) {
		this.newQuestions = newQuestions;
	}

}
