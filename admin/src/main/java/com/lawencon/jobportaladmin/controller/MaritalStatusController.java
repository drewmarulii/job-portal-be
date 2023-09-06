package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.maritalstatus.MaritalResDto;
import com.lawencon.jobportaladmin.service.MaritalStatusService;

@RestController
@RequestMapping("marital-status")
public class MaritalStatusController {

	@Autowired
	private MaritalStatusService maritalStatusService;

	@GetMapping
	public ResponseEntity<List<MaritalResDto>> getAll() {
		final List<MaritalResDto> data = maritalStatusService.getMaritalStatus();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
}
