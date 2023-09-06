package com.lawencon.jobportaladmin.constant;

public enum CandidateStatus {

	ACTIVE("CS-01", "Active"), ONPROCESS("CS-02", "On Process"),
	BLACKLIST("CS-03","Blacklist");
	
	
	public final String typeCode;
	public final String typeName;

	CandidateStatus(String typeCode, String typeName) {
		this.typeCode = typeCode;
		this.typeName = typeName;
	}
}
