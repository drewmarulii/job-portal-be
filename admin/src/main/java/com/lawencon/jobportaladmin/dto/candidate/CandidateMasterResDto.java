package com.lawencon.jobportaladmin.dto.candidate;

import java.util.List;

import com.lawencon.jobportaladmin.dto.candidateaddress.CandidateAddressInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatedocument.CandidateDocumentInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateeducation.CandidateEducationInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatefamily.CandidateFamilyInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatelanguage.CandidateLanguageInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateprofile.CandidateProfileInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateprojectexp.CandidateProjectExpInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatereferences.CandidateReferencesInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateskill.CandidateSkillInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatetrainingexp.CandidateTrainingExpInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateworkexp.CandidateWorkExpInsertReqDto;

public class CandidateMasterResDto {
	private CandidateProfileInsertReqDto candidateProfile;
	private CandidateAddressInsertReqDto candidateAddress;
	private List<CandidateDocumentInsertReqDto> candidateDocuments;
	private List<CandidateEducationInsertReqDto> candidateEducations;
	private List<CandidateFamilyInsertReqDto> candidateFamily;
	private List<CandidateLanguageInsertReqDto> candidateLanguage;
	private List<CandidateProjectExpInsertReqDto> candidateProjectExp;
	private List<CandidateReferencesInsertReqDto> candidateReferences;
	private List<CandidateSkillInsertReqDto> candidateSkill;
	private List<CandidateTrainingExpInsertReqDto> candidateTrainingExp;
	private List<CandidateWorkExpInsertReqDto> candidateWorkExp;

	public CandidateProfileInsertReqDto getCandidateProfile() {
		return candidateProfile;
	}

	public CandidateAddressInsertReqDto getCandidateAddress() {
		return candidateAddress;
	}

	public List<CandidateDocumentInsertReqDto> getCandidateDocuments() {
		return candidateDocuments;
	}

	public List<CandidateEducationInsertReqDto> getCandidateEducations() {
		return candidateEducations;
	}

	public List<CandidateFamilyInsertReqDto> getCandidateFamily() {
		return candidateFamily;
	}

	public List<CandidateLanguageInsertReqDto> getCandidateLanguage() {
		return candidateLanguage;
	}

	public List<CandidateProjectExpInsertReqDto> getCandidateProjectExp() {
		return candidateProjectExp;
	}

	public List<CandidateReferencesInsertReqDto> getCandidateReferences() {
		return candidateReferences;
	}

	public List<CandidateSkillInsertReqDto> getCandidateSkill() {
		return candidateSkill;
	}

	public List<CandidateTrainingExpInsertReqDto> getCandidateTrainingExp() {
		return candidateTrainingExp;
	}

	public List<CandidateWorkExpInsertReqDto> getCandidateWorkExp() {
		return candidateWorkExp;
	}

}
