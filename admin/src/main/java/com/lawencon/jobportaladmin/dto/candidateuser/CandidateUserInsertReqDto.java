package com.lawencon.jobportaladmin.dto.candidateuser;

import com.lawencon.jobportaladmin.dto.candidateprofile.CandidateProfileInsertReqDto;

public class CandidateUserInsertReqDto {

	private String userEmail;
	private CandidateProfileInsertReqDto profile;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public CandidateProfileInsertReqDto getProfile() {
		return profile;
	}

	public void setProfile(CandidateProfileInsertReqDto profile) {
		this.profile = profile;
	}
}
