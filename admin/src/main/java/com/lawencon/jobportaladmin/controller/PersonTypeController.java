package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.persontype.PersonTypeGetResDto;
import com.lawencon.jobportaladmin.service.PersonTypeService;

@RestController
@RequestMapping("person-types")
public class PersonTypeController {

	@Autowired
	private PersonTypeService personTypeService;
	
	@GetMapping
	public ResponseEntity<List<PersonTypeGetResDto>>getPersonTypes(){
		final List<PersonTypeGetResDto> response = personTypeService.getPersonTypes();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/id")
	public ResponseEntity<PersonTypeGetResDto> getById(@RequestParam String id) {
		final PersonTypeGetResDto data = personTypeService.getById(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
