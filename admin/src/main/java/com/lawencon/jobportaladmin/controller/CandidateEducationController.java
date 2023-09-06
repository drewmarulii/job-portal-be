package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidateeducation.CandidateEducationInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateeducation.CandidateEducationResDto;
import com.lawencon.jobportaladmin.dto.candidateeducation.CandidateEducationUpdateReqDto;
import com.lawencon.jobportaladmin.service.CandidateEducationService;

@RestController
@RequestMapping("candidate-educations")
public class CandidateEducationController {
	
	@Autowired
	private CandidateEducationService educationService;
	
	@GetMapping
	public ResponseEntity<List<CandidateEducationResDto>> getEducationByCandidate(@RequestParam("id") String id) {
		final List<CandidateEducationResDto> response = educationService.getEducationByCandidate(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody CandidateEducationInsertReqDto data) {
		final InsertResDto response = educationService.insertEducation(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody CandidateEducationUpdateReqDto data){
		final UpdateResDto response = educationService.updateEducation(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto response = educationService.deleteEducation(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteEducation/{code}")
	public ResponseEntity<DeleteResDto> deleteFromCandidate(@PathVariable("code") String code) {
		final DeleteResDto response = educationService.deleteEducationFromCandidate(code);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
