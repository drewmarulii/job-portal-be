package com.lawencon.jobportalcandidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalcandidate.dto.religion.ReligionResDto;
import com.lawencon.jobportalcandidate.service.ReligionService;

@RestController
@RequestMapping("religions")
public class ReligionController {
	@Autowired
	private ReligionService religionService;
	

	@GetMapping
	public ResponseEntity<List<ReligionResDto>> getAll() {
		final List<ReligionResDto> data = religionService.getReligions();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	


}
