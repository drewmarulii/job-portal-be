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
import com.lawencon.jobportaladmin.dto.job.JobDetailResDto;
import com.lawencon.jobportaladmin.dto.job.JobInsertReqDto;
import com.lawencon.jobportaladmin.dto.job.JobResDto;
import com.lawencon.jobportaladmin.dto.job.JobUpdateReqDto;
import com.lawencon.jobportaladmin.service.JobService;

@RestController
@RequestMapping("jobs")
public class JobController {

	@Autowired
	private JobService jobService;

	@GetMapping
	public ResponseEntity<List<JobResDto>> getAll() {
		final List<JobResDto> response = jobService.getAllJobs();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<InsertResDto> insertJob(@RequestBody JobInsertReqDto job) {
		InsertResDto response = jobService.insertJob(job);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping("/person")
	public ResponseEntity<List<JobResDto>> getByPrincipal() {
		final List<JobResDto> data = jobService.getByPrincipal();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/pic")
	public ResponseEntity<List<JobResDto>> getByPic() {
		final List<JobResDto> data = jobService.getByPic();
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/company")
	public ResponseEntity<List<JobResDto>> getByCompany(@RequestParam String code) {
		final List<JobResDto> data = jobService.getByCompany(code);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/detail")
	public ResponseEntity<JobDetailResDto> getDetail(@RequestParam String id) {
		final JobDetailResDto data = jobService.getDetail(id);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<UpdateResDto> update(@RequestBody JobUpdateReqDto data) {
		final UpdateResDto response = jobService.updateJob(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


}
