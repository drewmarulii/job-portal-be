package com.lawencon.jobportaladmin.dto.candidate;

import java.util.List;

import com.lawencon.jobportaladmin.dto.candidateaddress.CandidateAddressInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatedocument.CandidateDocumentInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateeducation.CandidateEducationInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatefamily.CandidateFamilyInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatelanguage.CandidateLanguageInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateprojectexp.CandidateProjectExpInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatereferences.CandidateReferencesInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateskill.CandidateSkillInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatetrainingexp.CandidateTrainingExpInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateworkexp.CandidateWorkExpInsertReqDto;

public class CandidateMasterInsertReqDto {
	private String userEmail;
	private String salutation;
	private String fullname;
	private String gender;
	private String experience;
	private String expectedSalary;
	private String phoneNumber;
	private String mobileNumber;
	private String nik;
	private String birthDate;
	private String birthPlace;
	private String maritalStatusId;
	private String religionId;
	private String personTypeId;
	private String file;
	private String fileExtension;
	private String candidateStatusId;
	private List<CandidateAddressInsertReqDto> candidateAddress;
	private List<CandidateDocumentInsertReqDto> candidateDocuments;
	private List<CandidateEducationInsertReqDto> candidateEducations;
	private List<CandidateFamilyInsertReqDto> candidateFamily;
	private List<CandidateLanguageInsertReqDto> candidateLanguage;
	private List<CandidateProjectExpInsertReqDto> candidateProjectExp;
	private List<CandidateReferencesInsertReqDto> candidateReferences;
	private List<CandidateSkillInsertReqDto> candidateSkill;
	private List<CandidateTrainingExpInsertReqDto> candidateTrainingExp;
	private List<CandidateWorkExpInsertReqDto> candidateWorkExp;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getMaritalStatusId() {
		return maritalStatusId;
	}

	public void setMaritalStatusId(String maritalStatusId) {
		this.maritalStatusId = maritalStatusId;
	}

	public String getReligionId() {
		return religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getPersonTypeId() {
		return personTypeId;
	}

	public void setPersonTypeId(String personTypeId) {
		this.personTypeId = personTypeId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getCandidateStatusId() {
		return candidateStatusId;
	}

	public void setCandidateStatusId(String candidateStatusId) {
		this.candidateStatusId = candidateStatusId;
	}

	public List<CandidateAddressInsertReqDto> getCandidateAddress() {
		return candidateAddress;
	}

	public void setCandidateAddress(List<CandidateAddressInsertReqDto> candidateAddress) {
		this.candidateAddress = candidateAddress;
	}

	public List<CandidateDocumentInsertReqDto> getCandidateDocuments() {
		return candidateDocuments;
	}

	public void setCandidateDocuments(List<CandidateDocumentInsertReqDto> candidateDocuments) {
		this.candidateDocuments = candidateDocuments;
	}

	public List<CandidateEducationInsertReqDto> getCandidateEducations() {
		return candidateEducations;
	}

	public void setCandidateEducations(List<CandidateEducationInsertReqDto> candidateEducations) {
		this.candidateEducations = candidateEducations;
	}

	public List<CandidateFamilyInsertReqDto> getCandidateFamily() {
		return candidateFamily;
	}

	public void setCandidateFamily(List<CandidateFamilyInsertReqDto> candidateFamily) {
		this.candidateFamily = candidateFamily;
	}

	public List<CandidateLanguageInsertReqDto> getCandidateLanguage() {
		return candidateLanguage;
	}

	public void setCandidateLanguage(List<CandidateLanguageInsertReqDto> candidateLanguage) {
		this.candidateLanguage = candidateLanguage;
	}

	public List<CandidateProjectExpInsertReqDto> getCandidateProjectExp() {
		return candidateProjectExp;
	}

	public void setCandidateProjectExp(List<CandidateProjectExpInsertReqDto> candidateProjectExp) {
		this.candidateProjectExp = candidateProjectExp;
	}

	public List<CandidateReferencesInsertReqDto> getCandidateReferences() {
		return candidateReferences;
	}

	public void setCandidateReferences(List<CandidateReferencesInsertReqDto> candidateReferences) {
		this.candidateReferences = candidateReferences;
	}

	public List<CandidateSkillInsertReqDto> getCandidateSkill() {
		return candidateSkill;
	}

	public void setCandidateSkill(List<CandidateSkillInsertReqDto> candidateSkill) {
		this.candidateSkill = candidateSkill;
	}

	public List<CandidateTrainingExpInsertReqDto> getCandidateTrainingExp() {
		return candidateTrainingExp;
	}

	public void setCandidateTrainingExp(List<CandidateTrainingExpInsertReqDto> candidateTrainingExp) {
		this.candidateTrainingExp = candidateTrainingExp;
	}

	public List<CandidateWorkExpInsertReqDto> getCandidateWorkExp() {
		return candidateWorkExp;
	}

	public void setCandidateWorkExp(List<CandidateWorkExpInsertReqDto> candidateWorkExp) {
		this.candidateWorkExp = candidateWorkExp;
	}

	public String getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}

}
