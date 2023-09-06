package com.lawencon.jobportalcandidate.dto.candidateskill;

public class CandidateSkillInsertReqDto {
	private String skillCode;
	private String skillName;
	private String email;
	
	
	
	public String getSkillCode() {
		return skillCode;
	}

	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
