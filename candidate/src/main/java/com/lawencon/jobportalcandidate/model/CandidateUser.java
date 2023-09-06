package com.lawencon.jobportalcandidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_user")
public class CandidateUser extends BaseEntity{
	@Column(name = "user_email",length = 50, nullable = false,unique = true)
	private String userEmail;
	
	@Column(name = "user_password",nullable = false)
	private String userPassword;
	
	@OneToOne
	@JoinColumn(name = "profile_id")
	private CandidateProfile candidateProfile;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public CandidateProfile getCandidateProfile() {
		return candidateProfile;
	}

	public void setCandidateProfile(CandidateProfile candidateProfile) {
		this.candidateProfile = candidateProfile;
	}

}
