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
import com.lawencon.jobportalcandidate.dto.candidateskill.CandidateSkillInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateskill.CandidateSkillResDto;
import com.lawencon.jobportalcandidate.dto.candidateskill.CandidateSkillUpdateReqDto;
import com.lawencon.jobportalcandidate.service.CandidateSkillService;

@RestController
@RequestMapping("candidate-skills")
public class CandidateSkillController {

	@Autowired
	private CandidateSkillService candidateSkillService;
	
	
	@GetMapping
	public ResponseEntity<List<CandidateSkillResDto>> getAllByCandidate(@RequestParam("id") String id) {
		final List<CandidateSkillResDto> result = candidateSkillService.getSkillsByCandidate(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insert(@RequestBody CandidateSkillInsertReqDto data) {
		final InsertResDto result = candidateSkillService.insertSkill(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody CandidateSkillUpdateReqDto data) {
		final UpdateResDto result = candidateSkillService.updateSkill(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResDto> delete(@PathVariable("id") String id) {
		final DeleteResDto data = candidateSkillService.deleteSkill(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
