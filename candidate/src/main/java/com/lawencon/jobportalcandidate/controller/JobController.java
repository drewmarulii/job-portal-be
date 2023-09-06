package com.lawencon.jobportalcandidate.controller;

import java.math.BigDecimal;
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

import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.job.JobInsertReqDto;
import com.lawencon.jobportalcandidate.dto.job.JobResDto;
import com.lawencon.jobportalcandidate.dto.job.JobUpdateReqDto;
import com.lawencon.jobportalcandidate.service.JobService;

@RestController
@RequestMapping("jobs")
public class JobController {

	@Autowired
	private JobService jobService;
	
	@GetMapping
	public ResponseEntity<List<JobResDto>> getAll() {
		final List<JobResDto> data = jobService.getJobs();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/company")
	public ResponseEntity<List<JobResDto>> getByCompany(@RequestParam("code")String code) {
		final List<JobResDto> data = jobService.getJobsByCompany(code);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/salary")
	public ResponseEntity<List<JobResDto>> getBySalary(@RequestParam("salary")Float salary) {
		final List<JobResDto> data = jobService.getJobsBySalary(salary);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<InsertResDto> insertJob(@RequestBody JobInsertReqDto job){
		final InsertResDto response = jobService.insertJob(job);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	@GetMapping("/detail")
	public ResponseEntity<JobResDto> getById(@RequestParam String jobId){
		final JobResDto response = jobService.getById(jobId);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

	@GetMapping("/filter")
	public ResponseEntity<List<JobResDto>> filter(@RequestParam("title")String title,@RequestParam("location")String location,@RequestParam("salary")BigDecimal salary) {
		final List<JobResDto> response = jobService.filter(title,location,salary);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	

	@GetMapping("/topsalary")
	public ResponseEntity<List<JobResDto>> getTopThreeSalary() {
		final List<JobResDto> data = jobService.getTopThreeSalary();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@PatchMapping
	public ResponseEntity<UpdateResDto> updateJob(@RequestBody JobUpdateReqDto data) {
		final UpdateResDto response = jobService.updateJob(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
