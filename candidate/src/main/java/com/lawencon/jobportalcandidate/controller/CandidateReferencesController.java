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
import com.lawencon.jobportalcandidate.dto.candidatereferences.CandidateReferencesInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidatereferences.CandidateReferencesResDto;
import com.lawencon.jobportalcandidate.dto.candidatereferences.CandidateReferencesUpdateReqDto;
import com.lawencon.jobportalcandidate.service.CandidateReferencesService;

@RestController
@RequestMapping("candidate-references")
public class CandidateReferencesController {

	@Autowired
	private CandidateReferencesService candidateReferencesService;

	@GetMapping
	public ResponseEntity<List<CandidateReferencesResDto>> getAllByCandidate(@RequestParam("id") String id) {
		final List<CandidateReferencesResDto> result = candidateReferencesService.getReferencesByCandidate(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody CandidateReferencesInsertReqDto data) {
		final InsertResDto result = candidateReferencesService.insertReference(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody CandidateReferencesUpdateReqDto data) {
		final UpdateResDto result = candidateReferencesService.updateReferences(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = candidateReferencesService.deleteReference(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
