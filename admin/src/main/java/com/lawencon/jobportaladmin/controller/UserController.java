package com.lawencon.jobportaladmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.changepassword.ChangePasswordReqDto;
import com.lawencon.jobportaladmin.dto.profile.ProfileResDto;
import com.lawencon.jobportaladmin.dto.profile.ProfileUpdateReqDto;
import com.lawencon.jobportaladmin.dto.user.UserInsertReqDto;
import com.lawencon.jobportaladmin.dto.user.UsersResDto;
import com.lawencon.jobportaladmin.service.UserService;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/filter")
	public ResponseEntity<List<UsersResDto>> getUsersByRoleCode(@RequestParam String roleCode) {
		final List<UsersResDto> response = userService.getUsersByRoleCode(roleCode);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<UsersResDto>> getAll() {
		final List<UsersResDto> response = userService.getAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insertUser(@RequestBody UserInsertReqDto userData) {
		final InsertResDto response = userService.registerUser(userData);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PatchMapping
	public ResponseEntity<UpdateResDto> updatePassword(@RequestBody ChangePasswordReqDto data){
		final UpdateResDto response = userService.changePassword(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/detail")
	public ResponseEntity<UsersResDto>getById(@RequestParam String id){
		final UsersResDto response = userService.getById(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<ProfileResDto>getProfile(@RequestParam String id){
		final ProfileResDto response = userService.getProfile(id);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PatchMapping("/profile")
	public ResponseEntity<UpdateResDto>updateProfile(@RequestBody ProfileUpdateReqDto data){
		final UpdateResDto response = userService.updateProfile(data);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
