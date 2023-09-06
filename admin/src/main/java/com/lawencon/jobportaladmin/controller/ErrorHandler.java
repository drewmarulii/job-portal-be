package com.lawencon.jobportaladmin.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.transaction.RollbackException;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lawencon.jobportaladmin.dto.ErrorResDto;
import com.lawencon.jobportaladmin.exception.JobRoad;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;


@ControllerAdvice
public class ErrorHandler {
	
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorResDto<String>> handleNullPointerException(NullPointerException npe) {
		npe.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(npe.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<ErrorResDto<String>> handleMessagingPointerException(MessagingException me) {
		me.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(me.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ErrorResDto<String>> handleIOException(IOException ie) {
		ie.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(ie.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(AuthorizationServiceException.class)
	public ResponseEntity<ErrorResDto<String>> handleAuthorizeException(AuthorizationServiceException ie) {
		ie.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(ie.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(PSQLException.class)
	public ResponseEntity<ErrorResDto<String>> handlePSQLException(PSQLException pe) {
		pe.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<String>();
		resDto.setMessage(pe.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResDto<String>> handleBadCredentialsException(BadCredentialsException bce) {
		bce.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage("Wrong Email / Password  " + bce.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResDto<String>> handleNoSuchElementException(NoSuchElementException nse) {
		nse.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage( nse.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResDto<String>> handleUsernameNotFoundException(UsernameNotFoundException nse) {
		nse.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage( nse.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ErrorResDto<String>> handleMalformedJwtException(MalformedJwtException nse) {
		nse.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage( nse.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(OptimisticLockException.class)
	public ResponseEntity<ErrorResDto<String>> handleOptimisticLockException(OptimisticLockException nse) {
		nse.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage("Asset Has Been Updated by Other Users" +  nse.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResDto<String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException nse) {
		nse.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(nse.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ErrorResDto<String>> handleSignatureException(SignatureException nse) {
		nse.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(nse.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<ErrorResDto<String>> handleNoResultException(NoResultException nre) {
		nre.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(nre.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResDto<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve) {
		final List<String> errors = manve.getBindingResult().getFieldErrors()
				.stream()
				.map(v -> v.getDefaultMessage())
				.collect(Collectors.toList());
		manve.printStackTrace();
		final ErrorResDto<List<String>> resDto = new ErrorResDto<>();
		resDto.setMessage(errors);
		return new ResponseEntity<>(resDto,HttpStatus.BAD_REQUEST);
	}
	

	
	@ExceptionHandler(JobRoad.class)
	public ResponseEntity<ErrorResDto<String>>handleRunTimeException(JobRoad jr){
		jr.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(jr.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(RollbackException.class)
	public ResponseEntity<ErrorResDto<String>> handleRollbackException(RollbackException re) {
		re.printStackTrace();
		final ErrorResDto<String> resDto = new ErrorResDto<>();
		resDto.setMessage(re.getMessage());
		return new ResponseEntity<>(resDto,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	


}
