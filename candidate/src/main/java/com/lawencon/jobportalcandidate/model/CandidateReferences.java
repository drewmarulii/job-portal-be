package com.lawencon.jobportalcandidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_references")
public class CandidateReferences extends BaseEntity {

	@Column(name = "reference_code",length = 5, nullable = false, unique = true)
	private String referenceCode;
	
	@Column(name = "fullname", length = 50, nullable = false)
	private String fullName;

	@Column(name = "relationship", length = 10, nullable = false)
	private String relationship;

	@Column(name = "occupation", length = 20, nullable = false)
	private String occupation;

	@Column(name = "phone_number", length = 20, nullable = false)
	private String phoneNumber;

	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Column(name = "company", length = 50, nullable = false)
	private String company;

	@Column(name = "description", nullable = false)
	private String description;

	@OneToOne
	@JoinColumn(name = "user_id")
	private CandidateUser candidateUser;
	
	

	public String getReferenceCode() {
		return referenceCode;
	}

	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CandidateUser getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUser candidateUser) {
		this.candidateUser = candidateUser;
	}

}
