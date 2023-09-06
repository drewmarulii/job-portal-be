package com.lawencon.jobportalcandidate.controller;

import java.util.List;

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

import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.company.CompanyInsertReqDto;
import com.lawencon.jobportalcandidate.dto.company.CompanyResDto;
import com.lawencon.jobportalcandidate.dto.company.CompanyUpdateReqDto;
import com.lawencon.jobportalcandidate.service.CompanyService;

@RestController
@RequestMapping("companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@GetMapping
	public ResponseEntity<List<CompanyResDto>> getCompanies() {
		final List<CompanyResDto> response = companyService.getAllCompany();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/detail")
	public ResponseEntity<CompanyResDto> getDetail(@RequestParam String id) {
		final CompanyResDto response = companyService.getDetail(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertCompany(@RequestBody CompanyInsertReqDto companyData) {
		final InsertResDto response = companyService.insertCompany(companyData);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateCompany(@RequestBody CompanyUpdateReqDto companyUpdateData){
		final UpdateResDto response = companyService.updateCompany(companyUpdateData);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
