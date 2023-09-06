package com.lawencon.jobportaladmin.dto.candidateskill;

public class CandidateSkillInsertReqDto {
	private String skillCode;
	private String skillName;
	private String email;
	private String candidateId;
	
	

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

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
