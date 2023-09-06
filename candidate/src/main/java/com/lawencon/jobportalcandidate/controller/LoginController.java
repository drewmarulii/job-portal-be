package com.lawencon.jobportalcandidate.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lawencon.jobportalcandidate.dto.token.TokenReqDto;
import com.lawencon.jobportalcandidate.login.LoginReqDto;
import com.lawencon.jobportalcandidate.login.LoginResDto;
import com.lawencon.jobportalcandidate.service.CandidateService;

@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private CandidateService candidateService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping
	public ResponseEntity<LoginResDto> login(@Valid @RequestBody final LoginReqDto user) throws JsonProcessingException {
		final Authentication auth = new UsernamePasswordAuthenticationToken(user.getUserEmail(),
				user.getUserPassword());

		authenticationManager.authenticate(auth);
		final LoginResDto userDb = candidateService.login(user);
		
		final String tokenURl = "http://localhost:8082/token";
		
		final TokenReqDto tokenReqDto = new TokenReqDto();
		tokenReqDto.setId(userDb.getUserId());	
		
		final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		
		final RequestEntity<TokenReqDto> token = RequestEntity.post(tokenURl).headers(headers).body(tokenReqDto);

		final ResponseEntity<String> response = restTemplate.exchange(token,String.class);
		System.out.println(response.getBody());

		final LoginResDto loginRes = new LoginResDto();
		loginRes.setUserId(userDb.getUserId());
		loginRes.setFullName(userDb.getFullName());
		loginRes.setProfileId(userDb.getProfileId());
		loginRes.setPhotoId(userDb.getPhotoId());
		loginRes.setToken(response.getBody());
		loginRes.setNik(userDb.getNik());
		
		return new ResponseEntity<>(loginRes, HttpStatus.OK);
	}
	
	
}
