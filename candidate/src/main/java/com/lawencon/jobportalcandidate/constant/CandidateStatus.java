package com.lawencon.jobportalcandidate.constant;

public enum CandidateStatus {

	ACTIVE("CS-01", "Active"), ONPROCESS("CS-02", "On Process"),
	BLACKLIST("CS-03","Blacklist");
	
	
	public final String statusCode;
	public final String statusName;

	CandidateStatus(String statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}
}
