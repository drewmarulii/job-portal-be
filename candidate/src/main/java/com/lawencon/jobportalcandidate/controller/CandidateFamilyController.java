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
import com.lawencon.jobportalcandidate.dto.candidatefamily.CandidateFamilyInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidatefamily.CandidateFamilyResDto;
import com.lawencon.jobportalcandidate.dto.candidatefamily.CandidateFamilyUpdateReqDto;
import com.lawencon.jobportalcandidate.service.CandidateFamilyService;

@RestController
@RequestMapping("candidate-family")
public class CandidateFamilyController {
	
	@Autowired
	private CandidateFamilyService familyService;
	
	@GetMapping
	public ResponseEntity<List<CandidateFamilyResDto>> getFamilyByCandidate(@RequestParam("id") String id) {
		final List<CandidateFamilyResDto> response = familyService.getFamilyByCandidate(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insertCandidateDocument(@RequestBody CandidateFamilyInsertReqDto data) {
		final InsertResDto response = familyService.insertFamily(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateCandidateDocument(@RequestBody CandidateFamilyUpdateReqDto data){
		final UpdateResDto response = familyService.updateFamily(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = familyService.deleteFamily(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
