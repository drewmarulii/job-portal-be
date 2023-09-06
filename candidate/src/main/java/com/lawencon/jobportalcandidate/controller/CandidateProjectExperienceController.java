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
import com.lawencon.jobportalcandidate.dto.candidateprojectexp.CandidateProjectExpInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateprojectexp.CandidateProjectExpResDto;
import com.lawencon.jobportalcandidate.dto.candidateprojectexp.CandidateProjectExpUpdateReqDto;
import com.lawencon.jobportalcandidate.service.CandidateProjectExpService;

@RestController
@RequestMapping("candidate-projects")
public class CandidateProjectExperienceController {

	@Autowired
	private CandidateProjectExpService candidateProjectExpService;
	
	@GetMapping
	public ResponseEntity<List<CandidateProjectExpResDto>>getAllByCandidate(@RequestParam("id")String id){
		final List<CandidateProjectExpResDto> result = candidateProjectExpService.getProjectExpByCandidate(id);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody CandidateProjectExpInsertReqDto data) {
		final InsertResDto result = candidateProjectExpService.insertCandidateProjectExp(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody CandidateProjectExpUpdateReqDto data) {
		final UpdateResDto result = candidateProjectExpService.updateCandidateProjectExp(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = candidateProjectExpService.deleteCandidateProjectExp(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
