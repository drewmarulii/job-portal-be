package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidate.CandidateMasterInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateuser.CandidateUserInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateuser.CandidateUserResDto;
import com.lawencon.jobportaladmin.dto.candidateuser.CandidateUserUpdateReqDto;
import com.lawencon.jobportaladmin.service.CandidateUserService;

@RestController
@RequestMapping("candidate-user")
public class CandidateUserController {

	@Autowired
	private CandidateUserService candidateUserService;

	@PostMapping
	public ResponseEntity<InsertResDto> insertCandidateUser(@RequestBody CandidateUserInsertReqDto candidate) {
		final InsertResDto response = candidateUserService.insertCandidateuser(candidate);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/register")
	public ResponseEntity<InsertResDto> insertCandidateUserFromAdmin(
			@RequestBody CandidateMasterInsertReqDto candidate) {
		final InsertResDto response = candidateUserService.insertCandidateFromAdmin(candidate);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<CandidateUserResDto>> getAll() {
		final List<CandidateUserResDto> response = candidateUserService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/detail")
	public ResponseEntity<CandidateUserResDto> getById(@Param("id") String id) {
		final CandidateUserResDto response = candidateUserService.getById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	

	@PatchMapping("/update")
	public ResponseEntity<UpdateResDto> updateCandidateUserFromAdmin(@RequestBody CandidateUserUpdateReqDto candidate) {
		final UpdateResDto response = candidateUserService.updateCandidateFromAdmin(candidate);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PatchMapping("/update/candidate")
	public ResponseEntity<UpdateResDto> updateCandidateUser(@RequestBody CandidateUserUpdateReqDto candidate) {
		final UpdateResDto response = candidateUserService.updateCandidateUser(candidate);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
