package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.employmenttype.EmploymentTypeGetResDto;
import com.lawencon.jobportaladmin.service.EmploymentTypeService;

@RestController
@RequestMapping("employment-types")
public class EmploymentTypeController {

	@Autowired
	private EmploymentTypeService employmentTypeService;
	
	
	@GetMapping
	public ResponseEntity<List<EmploymentTypeGetResDto>> getEmploymentTypes(){
		final List<EmploymentTypeGetResDto> types = employmentTypeService.getTypes();
		return new ResponseEntity<>(types, HttpStatus.OK);
	}
	
}
