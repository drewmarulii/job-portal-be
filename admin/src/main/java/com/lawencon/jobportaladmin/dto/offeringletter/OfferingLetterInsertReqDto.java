package com.lawencon.jobportaladmin.dto.offeringletter;

import java.math.BigDecimal;

public class OfferingLetterInsertReqDto {

	private String applicantId;
	private String applicantCode;
	private String statusCode;
	private String address;
	private BigDecimal salary;
	private String convertedMoney;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getConvertedMoney() {
		return convertedMoney;
	}

	public void setConvertedMoney(String convertedMoney) {
		this.convertedMoney = convertedMoney;
	}

}
