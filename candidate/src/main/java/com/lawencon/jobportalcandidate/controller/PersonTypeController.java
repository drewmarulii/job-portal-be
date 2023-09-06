package com.lawencon.jobportalcandidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportalcandidate.dto.persontype.PersonTypeGetResDto;
import com.lawencon.jobportalcandidate.service.PersonTypeService;

@RestController
@RequestMapping("person-types")
public class PersonTypeController {

	@Autowired
	private PersonTypeService personTypeService;
	
	@GetMapping
	public ResponseEntity<List<PersonTypeGetResDto>> getAlls() {
		final List<PersonTypeGetResDto> data = personTypeService.getPersonTypes();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
