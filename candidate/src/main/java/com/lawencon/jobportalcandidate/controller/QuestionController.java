package com.lawencon.jobportalcandidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.question.QuestionResDto;
import com.lawencon.jobportalcandidate.dto.question.QuestionUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.question.QuestionsInsertReqDto;
import com.lawencon.jobportalcandidate.service.QuestionService;


@RestController
@RequestMapping("questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping
	public ResponseEntity<InsertResDto> insertQuestions(@RequestBody QuestionsInsertReqDto newQuestions) {
		final InsertResDto response = questionService.insertQuestion(newQuestions);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<QuestionResDto>> getByApplicant(@RequestParam String code){
		final List<QuestionResDto> response = questionService.getByApplicant(code);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto>updateQuestion(@RequestBody QuestionUpdateReqDto data){
		final UpdateResDto response = questionService.updateQuestion(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
