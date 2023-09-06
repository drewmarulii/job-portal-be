package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.benefit.BenefitInsertReqDto;
import com.lawencon.jobportaladmin.dto.benefit.BenefitResDto;
import com.lawencon.jobportaladmin.service.BenefitService;

@RestController
@RequestMapping("benefits")
public class BenefitController {

	@Autowired
	private BenefitService benefitService;
	
	@GetMapping
	public ResponseEntity<List<BenefitResDto>> getAll(){
		
		final List<BenefitResDto> response = benefitService.getAll();
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertBenefit(@RequestBody BenefitInsertReqDto benefitData){
		final InsertResDto response = benefitService.insertBenefit(benefitData);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/job")
	public ResponseEntity<List<BenefitResDto>> getByJob(@RequestParam String id){
		
		final List<BenefitResDto> response = benefitService.getByJob(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
	}
	
}
