package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.religion.ReligionsResDto;
import com.lawencon.jobportaladmin.service.ReligionService;

@RestController
@RequestMapping("religions")
public class ReligionController {

	@Autowired
	private ReligionService religionService;
	
	@GetMapping
	public ResponseEntity<List<ReligionsResDto>>getReligions(){
		final List<ReligionsResDto> response = religionService.getReligions();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
}
