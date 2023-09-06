package com.lawencon.jobportaladmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.hired.HiredInsertReqDto;
import com.lawencon.jobportaladmin.service.HiredService;

@RestController
@RequestMapping("hired")
public class HiredController {

	@Autowired
	private HiredService hiredService;
	
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertHired(@RequestBody HiredInsertReqDto hiredData){
		final InsertResDto response = hiredService.insertHired(hiredData);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
	}
	
}
