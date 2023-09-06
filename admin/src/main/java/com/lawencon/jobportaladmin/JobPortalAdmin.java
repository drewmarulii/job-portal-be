package com.lawencon.jobportaladmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "com.lawencon" })
@EntityScan(basePackages = { "com.lawencon" })
//@SpringBootApplication	
@SpringBootApplication(exclude = { ThymeleafAutoConfiguration.class })
public class JobPortalAdmin {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalAdmin.class, args);
	}

}
