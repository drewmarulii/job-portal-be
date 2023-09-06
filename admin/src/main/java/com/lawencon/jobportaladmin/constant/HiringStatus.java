package com.lawencon.jobportaladmin.constant;

public enum HiringStatus {

	APPLIED("S-001", "APPLIED"),ASSESMENT("S-002", "ASSESMENT"),
	INTERVIEWUSER("S-003", "INTERVIEWUSER"),MCU("S-004", "MCU"),
	OFFERING("S-005", "OFFERING"),HIRED("S-006", "HIRED"),
	REJECT("S-007", "REJECT");
	
	public final String statusCode;
	public final String statusName;

	HiringStatus(String statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}
}
