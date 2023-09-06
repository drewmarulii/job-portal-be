package com.lawencon.jobportalcandidate.dto.candidate;

import java.util.List;

import com.lawencon.jobportalcandidate.dto.candidateaddress.CandidateAddressUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidatedocument.CandidateDocumentUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidateeducation.CandidateEducationUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidatefamily.CandidateFamilyUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidatelanguage.CandidateLanguageUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidateprofile.CandidateProfileUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidateprojectexp.CandidateProjectExpUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidatereferences.CandidateReferencesUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidateskill.CandidateSkillUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidatetrainingexp.CandidateTrainingExpUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidateuser.CandidateUserUpdateReqDto;
import com.lawencon.jobportalcandidate.dto.candidateworkexp.CandidateWorkExpUpdateReqDto;

public class CandidateMasterUpdateReqDto {

	private String candidateId;

	private CandidateUserUpdateReqDto candidateUser;
	private CandidateProfileUpdateReqDto candidateProfile;
	private CandidateAddressUpdateReqDto candidateAddress;
	private List<CandidateDocumentUpdateReqDto> candidateDocuments;
	private List<CandidateEducationUpdateReqDto> candidateEducations;
	private List<CandidateFamilyUpdateReqDto> candidateFamily;
	private List<CandidateLanguageUpdateReqDto> candidateLanguage;
	private List<CandidateProjectExpUpdateReqDto> candidateProjectExp;
	private List<CandidateReferencesUpdateReqDto> candidateReferences;
	private List<CandidateSkillUpdateReqDto> candidateSkill;
	private List<CandidateTrainingExpUpdateReqDto> candidateTrainingExp;
	private List<CandidateWorkExpUpdateReqDto> candidateWorkExp;

	
	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public void setCandidateProfile(CandidateProfileUpdateReqDto candidateProfile) {
		this.candidateProfile = candidateProfile;
	}

	public void setCandidateAddress(CandidateAddressUpdateReqDto candidateAddress) {
		this.candidateAddress = candidateAddress;
	}

	public void setCandidateDocuments(List<CandidateDocumentUpdateReqDto> candidateDocuments) {
		this.candidateDocuments = candidateDocuments;
	}

	public void setCandidateEducations(List<CandidateEducationUpdateReqDto> candidateEducations) {
		this.candidateEducations = candidateEducations;
	}

	public void setCandidateFamily(List<CandidateFamilyUpdateReqDto> candidateFamily) {
		this.candidateFamily = candidateFamily;
	}

	public void setCandidateLanguage(List<CandidateLanguageUpdateReqDto> candidateLanguage) {
		this.candidateLanguage = candidateLanguage;
	}

	public void setCandidateProjectExp(List<CandidateProjectExpUpdateReqDto> candidateProjectExp) {
		this.candidateProjectExp = candidateProjectExp;
	}

	public void setCandidateReferences(List<CandidateReferencesUpdateReqDto> candidateReferences) {
		this.candidateReferences = candidateReferences;
	}

	public void setCandidateSkill(List<CandidateSkillUpdateReqDto> candidateSkill) {
		this.candidateSkill = candidateSkill;
	}

	public void setCandidateTrainingExp(List<CandidateTrainingExpUpdateReqDto> candidateTrainingExp) {
		this.candidateTrainingExp = candidateTrainingExp;
	}

	public void setCandidateWorkExp(List<CandidateWorkExpUpdateReqDto> candidateWorkExp) {
		this.candidateWorkExp = candidateWorkExp;
	}

	public CandidateProfileUpdateReqDto getCandidateProfile() {
		return candidateProfile;
	}

	public CandidateAddressUpdateReqDto getCandidateAddress() {
		return candidateAddress;
	}

	public List<CandidateDocumentUpdateReqDto> getCandidateDocuments() {
		return candidateDocuments;
	}

	public List<CandidateEducationUpdateReqDto> getCandidateEducations() {
		return candidateEducations;
	}

	public List<CandidateFamilyUpdateReqDto> getCandidateFamily() {
		return candidateFamily;
	}

	public List<CandidateLanguageUpdateReqDto> getCandidateLanguage() {
		return candidateLanguage;
	}

	public List<CandidateProjectExpUpdateReqDto> getCandidateProjectExp() {
		return candidateProjectExp;
	}

	public List<CandidateReferencesUpdateReqDto> getCandidateReferences() {
		return candidateReferences;
	}

	public List<CandidateSkillUpdateReqDto> getCandidateSkill() {
		return candidateSkill;
	}

	public List<CandidateTrainingExpUpdateReqDto> getCandidateTrainingExp() {
		return candidateTrainingExp;
	}

	public List<CandidateWorkExpUpdateReqDto> getCandidateWorkExp() {
		return candidateWorkExp;
	}
}
