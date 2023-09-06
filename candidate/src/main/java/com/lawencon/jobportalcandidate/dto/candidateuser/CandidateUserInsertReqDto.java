package com.lawencon.jobportalcandidate.dto.candidateuser;

import com.lawencon.jobportalcandidate.dto.candidateprofile.CandidateProfileInsertReqDto;

public class CandidateUserInsertReqDto {

	private String userEmail;
	private String userPassword;
	private CandidateProfileInsertReqDto profile;

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

	public CandidateProfileInsertReqDto getProfile() {
		return profile;
	}

	public void setProfile(CandidateProfileInsertReqDto profile) {
		this.profile = profile;
	}
}
