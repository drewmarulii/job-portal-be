package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.mcu.McuResDto;
import com.lawencon.jobportaladmin.dto.mcu.McusInsertReqDto;
import com.lawencon.jobportaladmin.service.McuService;

@RestController
@RequestMapping("mcus")
public class McuController {

	@Autowired
	private McuService mcuService;

	@PostMapping
	public ResponseEntity<InsertResDto> insertMcuFiles(@RequestBody McusInsertReqDto mcuFiles) {
		final InsertResDto response = mcuService.insertMcuFiles(mcuFiles);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<McuResDto>> getMcuDatas(@RequestParam String applicantId) {
		final List<McuResDto> response = mcuService.getByApplicant(applicantId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
