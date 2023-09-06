package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.candidatestatus.CandidateStatusResDto;
import com.lawencon.jobportaladmin.service.CandidateStatusService;

@RestController
@RequestMapping("candidate-status")
public class CandidateStatusController {

	@Autowired
	private CandidateStatusService candidateStatusService;
	
	@GetMapping
	public ResponseEntity<List<CandidateStatusResDto>> getCandidateStatus() {
		final List<CandidateStatusResDto> response = candidateStatusService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
