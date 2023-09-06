package com.lawencon.jobportalcandidate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = { "com.lawencon" })
@EntityScan(basePackages = { "com.lawencon" })
@SpringBootApplication
public class JobPortalCandidate {

	public static void main (String[] args) {
		
		SpringApplication.run(JobPortalCandidate.class,args);
	}
}
