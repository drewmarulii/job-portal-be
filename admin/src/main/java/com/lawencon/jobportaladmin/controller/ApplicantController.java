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
import com.lawencon.jobportaladmin.dto.applicant.ApplicantInsertReqDto;
import com.lawencon.jobportaladmin.dto.applicant.ApplicantResDto;
import com.lawencon.jobportaladmin.dto.applicant.ApplicantUpdateReqDto;
import com.lawencon.jobportaladmin.service.ApplicantService;

@RestController
@RequestMapping("applicants")
public class ApplicantController {

	@Autowired
	private ApplicantService applicantService;

	@PostMapping
	public ResponseEntity<InsertResDto> insertApplicant(@RequestBody ApplicantInsertReqDto applicantData) {
		final InsertResDto response = applicantService.insertApplicant(applicantData);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping 
	public ResponseEntity<List<ApplicantResDto>> getByJob(@RequestParam String jobId){
		final List<ApplicantResDto> response = applicantService.getAllApplicantByJob(jobId);
		return new ResponseEntity<>(response, HttpStatus.CREATED); 
	}
	
	@GetMapping("/filter")
	public ResponseEntity<ApplicantResDto> getByApplicant(@RequestParam String id){
		final ApplicantResDto response= applicantService.getById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateApplicant(@RequestBody ApplicantUpdateReqDto updateData){
		final UpdateResDto response = applicantService.updateApplicant(updateData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
