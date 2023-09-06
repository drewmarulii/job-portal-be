package com.lawencon.jobportalcandidate.controller;

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

import com.lawencon.jobportalcandidate.dto.DeleteResDto;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidateworkexp.CandidateWorkExpInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateworkexp.CandidateWorkExpResDto;
import com.lawencon.jobportalcandidate.dto.candidateworkexp.CandidateWorkExpUpdateReqDto;
import com.lawencon.jobportalcandidate.service.CandidateWorkExpService;

@RestController
@RequestMapping("candidate-works")
public class CandidateWorkExperienceController {

	@Autowired
	private CandidateWorkExpService candidateWorkExpService;
	
	@GetMapping
	public ResponseEntity<List<CandidateWorkExpResDto>> getWorkByCandidate(@RequestParam("id") String id) {
		final List<CandidateWorkExpResDto> response = candidateWorkExpService.getWorkByCandidate(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody CandidateWorkExpInsertReqDto data) {
		final InsertResDto result = candidateWorkExpService.insertWorksExperience(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody CandidateWorkExpUpdateReqDto data) {
		final UpdateResDto result = candidateWorkExpService.updateWorksExperience(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = candidateWorkExpService.deleteWorkExperience(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
