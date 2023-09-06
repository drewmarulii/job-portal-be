package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_skill")
public class CandidateSkill extends BaseEntity {
	
	@Column(name = "skill_code",length = 5, nullable = false, unique = true)
	private String skillCode;

	@Column(name = "skill_name", nullable = false)
	private String skillName;

	@OneToOne
	@JoinColumn(name = "user_id")
	private CandidateUser candidateUser;
	
	

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

	public CandidateUser getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUser candidateUser) {
		this.candidateUser = candidateUser;
	}

}
