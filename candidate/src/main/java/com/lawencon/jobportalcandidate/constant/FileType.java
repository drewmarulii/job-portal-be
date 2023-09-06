package com.lawencon.jobportalcandidate.constant;

public enum FileType {

	CURICULUMVITAE("FE-01", "CURICULUM VITAE"),
	FAMILYCARD("FE-02", "FAMILY CARD"),
	RESUME("FE-03", "RESUME"),
	TRANSCRIPT("FE-04", "TRANSCRIPT"),
	CERTIFICATE("FE-05", "CERTIFICATE"),
	CITIZENCARD("FE-06", "CITIZEN CARD"),
	OTHERS("FE-07", "OTHERS");
	
	public final String typeCode;
	public final String typeName;

	FileType(String typeCode, String typeName) {
		this.typeCode = typeCode;
		this.typeName = typeName;
	}

	
}
