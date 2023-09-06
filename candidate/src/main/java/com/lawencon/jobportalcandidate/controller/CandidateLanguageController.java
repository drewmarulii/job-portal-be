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
import com.lawencon.jobportalcandidate.dto.candidatelanguage.CandidateLanguageInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidatelanguage.CandidateLanguageResDto;
import com.lawencon.jobportalcandidate.dto.candidatelanguage.CandidateLanguageUpdateReqDto;
import com.lawencon.jobportalcandidate.service.CandidateLanguageService;


@RestController
@RequestMapping("candidate-language")
public class CandidateLanguageController {
	@Autowired
	private CandidateLanguageService languageService;
	
	@GetMapping
	public ResponseEntity<List<CandidateLanguageResDto>> getLanguageByCandidate(@RequestParam("id") String id) {
		final List<CandidateLanguageResDto> response = languageService.getCandidateLanguageByCandidate(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insertCandidateDocument(@RequestBody CandidateLanguageInsertReqDto data) {
		final InsertResDto response = languageService.insertCandidateLanguage(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateCandidateDocument(@RequestBody CandidateLanguageUpdateReqDto data){
		final UpdateResDto response = languageService.updateCandidateLanguage(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = languageService.DeleteCandidateLanguage(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
