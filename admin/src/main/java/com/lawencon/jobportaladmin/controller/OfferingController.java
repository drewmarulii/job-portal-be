package com.lawencon.jobportaladmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.offeringletter.OfferingLetterInsertReqDto;
import com.lawencon.jobportaladmin.service.OfferingLetterService;

@RestController
@RequestMapping("offerings")
public class OfferingController {

	@Autowired
	private OfferingLetterService offeringLetterService;

	@PostMapping
	public ResponseEntity<InsertResDto> insertOfferingLetter(@RequestBody OfferingLetterInsertReqDto offeringData) {
		final InsertResDto response = offeringLetterService.insertOfferingLetter(offeringData);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
