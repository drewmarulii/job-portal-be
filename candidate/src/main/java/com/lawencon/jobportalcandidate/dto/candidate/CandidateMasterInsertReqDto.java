package com.lawencon.jobportalcandidate.dto.candidate;

import com.lawencon.jobportalcandidate.dto.candidateprofile.CandidateProfileInsertReqDto;
import com.lawencon.jobportalcandidate.dto.candidateuser.CandidateUserInsertReqDto;

public class CandidateMasterInsertReqDto {
	private CandidateUserInsertReqDto candidateUser;
	private CandidateProfileInsertReqDto candidateProfile;
	
	public CandidateUserInsertReqDto getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUserInsertReqDto candidateUser) {
		this.candidateUser = candidateUser;
	}

	public CandidateProfileInsertReqDto getCandidateProfile() {
		return candidateProfile;
	}

	public void setCandidateProfile(CandidateProfileInsertReqDto candidateProfile) {
		this.candidateProfile = candidateProfile;
	}
	
}
