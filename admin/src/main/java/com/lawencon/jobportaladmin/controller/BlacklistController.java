package com.lawencon.jobportaladmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.blacklist.BlacklistInsertReqDto;
import com.lawencon.jobportaladmin.service.BlacklistService;

@RestController
@RequestMapping("blacklists")
public class BlacklistController {

	@Autowired
	private BlacklistService blacklistService;
	
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertBlacklist(@RequestBody BlacklistInsertReqDto data) {
		final InsertResDto response = blacklistService.insertBlacklist(data);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
}
