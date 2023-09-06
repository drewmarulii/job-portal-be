package com.lawencon.jobportaladmin.controller;

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

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.question.QuestionResDto;
import com.lawencon.jobportaladmin.dto.question.QuestionUpdateReqDto;
import com.lawencon.jobportaladmin.dto.question.QuestionsInsertReqDto;
import com.lawencon.jobportaladmin.service.QuestionService;

@RestController
@RequestMapping("questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@GetMapping
	public ResponseEntity<List<QuestionResDto>> getAll(){
		final List<QuestionResDto> response = questionService.getAll();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertQuestion(@RequestBody QuestionsInsertReqDto data) {
		final InsertResDto response = questionService.insertQuestion(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<QuestionResDto> getById(@RequestParam String id){
		final QuestionResDto response = questionService.getById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto>updateQuestion(@RequestBody QuestionUpdateReqDto data){
		final UpdateResDto response = questionService.updateQuestion(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

	
	
}
