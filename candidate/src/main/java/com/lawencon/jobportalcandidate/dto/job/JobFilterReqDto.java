package com.lawencon.jobportalcandidate.dto.job;

import java.math.BigDecimal;

public class JobFilterReqDto {
	private String title;
	private String location;
	private BigDecimal salary;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

}
