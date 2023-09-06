package com.lawencon.jobportaladmin.constant;

public enum PersonTypes {

	CANDIDATE("PT-01", "Candidate"), EMPLOYEE("PT-02", "Employee");
	
	public final String typeCode;
	public final String typeName;

	PersonTypes(String typeCode, String typeName) {
		this.typeCode = typeCode;
		this.typeName = typeName;
	}
	
}
