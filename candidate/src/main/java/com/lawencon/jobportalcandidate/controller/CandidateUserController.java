package com.lawencon.jobportalcandidate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.candidate.CandidateMasterResDto;
import com.lawencon.jobportalcandidate.dto.candidateuser.CandidateCheckDataResDto;
import com.lawencon.jobportalcandidate.dto.candidateuser.CandidateUserBlacklistReqDto;
import com.lawencon.jobportalcandidate.dto.candidateuser.CandidateUserInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateuser.CandidateUserUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidateuser.ChangePasswordReqDto;
import com.lawencon.jobportalcandidate.service.CandidateService;

@RestController
@RequestMapping("candidate-user")
public class CandidateUserController {

	@Autowired
	private CandidateService candidateService;
	
	@GetMapping
	public ResponseEntity<CandidateMasterResDto> getCandidate(@RequestParam("id") String id) {
		final CandidateMasterResDto response = candidateService.getCandidateProfile(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody CandidateUserInsertReqDto data) {
		final InsertResDto result = candidateService.insertCandidate(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody CandidateUserUpdateReqDto data) {
		final UpdateResDto result = candidateService.updateCandidateProfile(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PatchMapping("/password")
	public ResponseEntity<UpdateResDto> changePassword(@RequestBody ChangePasswordReqDto data){
		final UpdateResDto response = candidateService.changePassword(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PatchMapping("/blacklist")
	public ResponseEntity<UpdateResDto> updateBlacklist(@RequestBody CandidateUserBlacklistReqDto data){
		final UpdateResDto response = candidateService.updateBlacklist(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("checks")
	public ResponseEntity<CandidateCheckDataResDto> checkCandidateData(){
		final CandidateCheckDataResDto response = candidateService.checkCandidateDatas();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
