package com.lawencon.jobportaladmin.constant;

public enum Role {

	ADMIN("R-001", "Admin"), HUMANRESOURCES("R-002", "HR"), PIC("R-003", "PIC");
	
	public final String roleCode;
	public final String roleName;

	Role(String roleCode, String roleName) {
		this.roleCode = roleCode;
		this.roleName = roleName;
	}
	
}
