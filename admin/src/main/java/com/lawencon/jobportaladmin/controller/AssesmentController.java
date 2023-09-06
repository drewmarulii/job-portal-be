package com.lawencon.jobportaladmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.assesment.AssesmentInsertReqDto;
import com.lawencon.jobportaladmin.dto.assesment.AssesmentResDto;
import com.lawencon.jobportaladmin.dto.assesment.AssesmentUpdateReqDto;
import com.lawencon.jobportaladmin.service.AssesmentService;

@RestController
@RequestMapping("assesments")
public class AssesmentController {

	@Autowired
	private AssesmentService assesmentService;
	
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertAssesment(@RequestBody AssesmentInsertReqDto assesmentData){
		final InsertResDto response = assesmentService.insertAssesment(assesmentData);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping
	public ResponseEntity<AssesmentResDto> getByApplicant(@RequestParam String applicantId){
		final AssesmentResDto response= assesmentService.getByApplicant(applicantId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
		
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateNotes(@RequestBody AssesmentUpdateReqDto data){
		final UpdateResDto response = assesmentService.updateAssesment(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
}


