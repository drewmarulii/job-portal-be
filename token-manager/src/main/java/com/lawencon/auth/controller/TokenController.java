package com.lawencon.auth.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.auth.dto.TokenReqDto;
import com.lawencon.auth.service.JwtService;

@RestController
@RequestMapping("token")
public class TokenController {

	@Autowired
	private JwtService jwtService;
	
	@PostMapping
	public ResponseEntity<String> getToken(@RequestBody TokenReqDto tokenDto){
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);
		
		final Map<String, Object> claims = new HashMap<>();
		claims.put("exp", cal.getTime());
		claims.put("id",tokenDto.getId());
		final String response = jwtService.generateJwt(claims);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	@PostMapping("/validate")
	public ResponseEntity<String> validateToken(){
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null || auth.getPrincipal() == null)
			throw new RuntimeException("Invalid Login");
		
		
		return new ResponseEntity<String>(auth.getPrincipal().toString(), HttpStatus.OK);
	}
	
}
