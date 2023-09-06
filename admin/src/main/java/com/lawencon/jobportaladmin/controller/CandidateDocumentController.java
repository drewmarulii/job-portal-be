package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.candidatedocument.CandidateDocumentInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatedocument.CandidateDocumentResDto;
import com.lawencon.jobportaladmin.service.CandidateDocumentService;

@RestController
@RequestMapping("candidate-documents")
public class CandidateDocumentController {

	@Autowired
	private CandidateDocumentService documentService;

	@GetMapping
	public ResponseEntity<List<CandidateDocumentResDto>> getDocumentsByCandidate(@RequestParam("id") String id) {
		final List<CandidateDocumentResDto> response = documentService.getCandidateDocumentByCandidate(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insertCandidateDocument(@RequestBody CandidateDocumentInsertReqDto data) {
		final InsertResDto response = documentService.insertCandidateDocument(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
//	@PatchMapping
//	public ResponseEntity<UpdateResDto> updateCandidateDocument(@RequestBody CandidateDocumentUpdateReqDto data){
//		final UpdateResDto response = documentService.updateCandidateDocument(data);
//		return new ResponseEntity<>(response,HttpStatus.OK);
//	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = documentService.deleteCandidateDocument(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteDocument/{code}")
	public ResponseEntity<DeleteResDto> deleteFromCandidate(@PathVariable("code") String code) {
		final DeleteResDto data = documentService.deleteCandidateDocumentFromCandidate(code);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
