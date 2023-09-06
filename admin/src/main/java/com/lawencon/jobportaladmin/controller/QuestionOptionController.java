package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.questionoption.QuestionOptionResDto;
import com.lawencon.jobportaladmin.dto.questionoption.QuestionOptionUpdateReqDto;
import com.lawencon.jobportaladmin.service.QuestionOptionService;

@RestController
@RequestMapping("options")
public class QuestionOptionController {
	@Autowired
	private QuestionOptionService questionOptionService;
	
	@GetMapping
	public ResponseEntity<List<QuestionOptionResDto>> getByQuestion(@RequestParam String id){
		final List<QuestionOptionResDto> response = questionOptionService.getByQuestion(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateOption(@RequestBody QuestionOptionUpdateReqDto data){
		final UpdateResDto response = questionOptionService.updateOption(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/detail")
	public ResponseEntity<QuestionOptionResDto> getById(@RequestParam String id){
		final QuestionOptionResDto response = questionOptionService.getById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
}
