package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.hiringstatus.HiringStatusGetResDto;
import com.lawencon.jobportaladmin.service.HiringStatusService;

@RestController
@RequestMapping("hiring-status")
public class HiringStatusController {

	@Autowired
	private HiringStatusService hiringStatusService;

	@GetMapping
	public ResponseEntity<List<HiringStatusGetResDto>> getHiringStatus() {
		final List<HiringStatusGetResDto> response = hiringStatusService.getHiringStatus();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
