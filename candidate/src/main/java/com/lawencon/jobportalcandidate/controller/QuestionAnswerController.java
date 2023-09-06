package com.lawencon.jobportalcandidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.questionanswer.QuestionAnswerResDto;
import com.lawencon.jobportalcandidate.dto.questionanswer.QuestionAnswersInsertReqDto;
import com.lawencon.jobportalcandidate.service.QuestionAnswerService;

@RestController
@RequestMapping("question-answers")
public class QuestionAnswerController {

	@Autowired
	private QuestionAnswerService questionAnswerService;
	
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertAnswers(@RequestBody QuestionAnswersInsertReqDto data){
		final InsertResDto response = questionAnswerService.insertAnswer(data);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity <List<QuestionAnswerResDto>> getByApplicant(@RequestParam String code){
		final List<QuestionAnswerResDto> response = questionAnswerService.getByApplicant(code);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
		
}
