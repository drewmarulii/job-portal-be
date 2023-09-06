package com.lawencon.jobportaladmin.dto.candidateprofile;

import java.math.BigDecimal;

public class CandidateProfileInsertReqDto {

	private String salutation;
	private String fullname;
	private String gender;
	private String experience;
	private BigDecimal expectedSalary;
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

	public BigDecimal getExpectedSalary() {
		return expectedSalary;
	}

	public void setExpectedSalary(BigDecimal expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
}
