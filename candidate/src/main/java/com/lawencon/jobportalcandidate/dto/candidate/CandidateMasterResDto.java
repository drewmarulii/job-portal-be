package com.lawencon.jobportalcandidate.dto.candidate;

import com.lawencon.jobportalcandidate.dto.candidateprofile.CandidateProfileResDto;
import com.lawencon.jobportalcandidate.dto.candidateuser.CandidateUserResDto;

public class CandidateMasterResDto {
	
	private CandidateUserResDto candidateUser;
	private CandidateProfileResDto candidateProfile;

	public CandidateUserResDto getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUserResDto candidateUser) {
		this.candidateUser = candidateUser;
	}

	public CandidateProfileResDto getCandidateProfile() {
		return candidateProfile;
	}

	public void setCandidateProfile(CandidateProfileResDto candidateProfile) {
		this.candidateProfile = candidateProfile;
	}

}
