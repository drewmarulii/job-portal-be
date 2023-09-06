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
import com.lawencon.jobportalcandidate.dto.assignedjobquestion.AssignedJobQuestionInsertReqDto;
import com.lawencon.jobportalcandidate.dto.assignedjobquestion.AssignedJobQuestionResDto;
import com.lawencon.jobportalcandidate.service.AssignedJobQuestionService;

@RestController
@RequestMapping("assigned-jobs")
public class AssignedJobQuestionController {

	@Autowired
	private AssignedJobQuestionService assignedJobQuestionService;
	
	@GetMapping
	public ResponseEntity<List<AssignedJobQuestionResDto>> getAssignedJobQuestion(@RequestParam("id") String id) {
		final List<AssignedJobQuestionResDto> response = assignedJobQuestionService.getAssignedJobQuestionByJob(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody AssignedJobQuestionInsertReqDto data) {
		final InsertResDto response = assignedJobQuestionService.insertAssignedJobQuestion(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
