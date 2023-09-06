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
import com.lawencon.jobportaladmin.dto.employee.EmployeInsertReqAdminDto;
import com.lawencon.jobportaladmin.dto.employee.EmployeeResDto;
import com.lawencon.jobportaladmin.service.EmployeeService;

@RestController
@RequestMapping("employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	public ResponseEntity<List<EmployeeResDto>> getAll(){
		final List<EmployeeResDto> response = employeeService.getAll();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertEmployeeFromAdmin(@RequestBody EmployeInsertReqAdminDto data) {
		final InsertResDto response = employeeService.insertEmployeeFromAdmin(data);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/id")
	public ResponseEntity<EmployeeResDto> getById(@RequestParam String id) {
		final EmployeeResDto response = employeeService.getById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
