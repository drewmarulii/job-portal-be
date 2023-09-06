package com.lawencon.jobportalcandidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalcandidate.dto.employmenttype.EmploymentTypeGetResDto;
import com.lawencon.jobportalcandidate.service.EmploymentTypeService;

@RestController
@RequestMapping("employment-types")
public class EmploymentTypeController {

	@Autowired
	private EmploymentTypeService employmenTypeService;
	
	@GetMapping
	public ResponseEntity<List<EmploymentTypeGetResDto>> getEmploymenTypes() {
		final List<EmploymentTypeGetResDto> response = employmenTypeService.getTypes();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
} 
