package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.role.RoleResDto;
import com.lawencon.jobportaladmin.service.RoleService;

@RestController
@RequestMapping("roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping
	public ResponseEntity<List<RoleResDto>> getRoles() {
		final List<RoleResDto> response = roleService.getRoles();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
