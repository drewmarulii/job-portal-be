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
import com.lawencon.jobportaladmin.dto.candidatelanguage.CandidateLanguageInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatelanguage.CandidateLanguageResDto;
import com.lawencon.jobportaladmin.dto.candidatelanguage.CandidateLanguageUpdateReqDto;
import com.lawencon.jobportaladmin.service.CandidateLanguageService;


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
	public ResponseEntity<InsertResDto> insertCandidateLanguage(@RequestBody CandidateLanguageInsertReqDto data) {
		final InsertResDto response = languageService.insertCandidateLanguage(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateCandidateLanguage(@RequestBody CandidateLanguageUpdateReqDto data){
		final UpdateResDto response = languageService.updateCandidateLanguage(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = languageService.deleteCandidateLanguage(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteLanguage/{code}")
	public ResponseEntity<DeleteResDto> deleteFromCandidate(@PathVariable("code") String code) {
		final DeleteResDto data = languageService.deleteCandidateLanguageFromCandidate(code);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
