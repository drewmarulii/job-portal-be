package com.lawencon.jobportaladmin.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.interview.InterviewInsertReqDto;
import com.lawencon.jobportaladmin.dto.interview.InterviewResDto;
import com.lawencon.jobportaladmin.service.InterviewService;

@RestController
@RequestMapping("interviews")
public class InterviewController {

	@Autowired
	private InterviewService interviewService;

	@PostMapping
	private ResponseEntity<InsertResDto> insertInterview(@RequestBody InterviewInsertReqDto interviewData) throws MessagingException, UnsupportedEncodingException {
		final InsertResDto response = interviewService.insertInterview(interviewData);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	private ResponseEntity<InterviewResDto> getByApplicant(@RequestParam String applicantId) {
		final InterviewResDto response = interviewService.getByApplicant(applicantId);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
