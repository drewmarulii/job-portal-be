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
import com.lawencon.jobportaladmin.dto.candidatetrainingexp.CandidateTrainingExpInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatetrainingexp.CandidateTrainingExpResDto;
import com.lawencon.jobportaladmin.dto.candidatetrainingexp.CandidateTrainingExpUpdateReqDto;
import com.lawencon.jobportaladmin.service.CandidateTrainingExpService;

@RestController
@RequestMapping("candidate-trainings")
public class CandidateTrainingExpController {

	@Autowired
	private CandidateTrainingExpService candidateTrainingExpService;
	
	@GetMapping
	public ResponseEntity<List<CandidateTrainingExpResDto>> getTrainingExpByCandidate(@RequestParam("id")String id){
		final List<CandidateTrainingExpResDto> result = candidateTrainingExpService.getAllTrainingExpByCandidate(id);
		return new ResponseEntity<>(result,HttpStatus.OK);

	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody CandidateTrainingExpInsertReqDto data) {
		final InsertResDto result = candidateTrainingExpService.insertCandidateTrainingExp(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody CandidateTrainingExpUpdateReqDto data) {
		final UpdateResDto result = candidateTrainingExpService.updateCandidateTrainingExp(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = candidateTrainingExpService.deleteCandidateTrainingExp(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteTraining/{code}")
	public ResponseEntity<DeleteResDto> deleteFromCandidate(@PathVariable("code") String code) {
		final DeleteResDto data = candidateTrainingExpService.deleteCandidateTrainingExpFromCandidate(code);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
