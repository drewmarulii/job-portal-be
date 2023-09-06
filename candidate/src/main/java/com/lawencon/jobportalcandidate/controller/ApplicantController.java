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
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.applicant.ApplicantInsertReqDto;
import com.lawencon.jobportalcandidate.dto.applicant.ApplicantResDto;
import com.lawencon.jobportalcandidate.dto.applicant.ApplicantUpdateReqDto;
import com.lawencon.jobportalcandidate.service.ApplicantService;

@RestController
@RequestMapping("applicants")
public class ApplicantController {

	@Autowired
	private ApplicantService applicantService;
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody ApplicantInsertReqDto data) {
		final InsertResDto result = applicantService.insertApplicant(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}	
	
	@GetMapping
	public ResponseEntity<List<ApplicantResDto>> getByCandidate() {
		final List<ApplicantResDto> response = applicantService.getApplicantByCandidate();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateApplicant(@RequestBody ApplicantUpdateReqDto updateData){
		final UpdateResDto response = applicantService.updateApplicant(updateData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
