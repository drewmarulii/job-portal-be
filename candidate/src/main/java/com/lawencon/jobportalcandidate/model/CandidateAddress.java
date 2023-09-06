package com.lawencon.jobportalcandidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_address")
public class CandidateAddress extends BaseEntity {

	@Column(name= "address_code", nullable= false, unique= true)
	private String addressCode;
	
	@Column(name = "address", nullable= false)
	private String address;
	
	@Column(name = "residence_type", length=10, nullable= false)
	private String residenceType;
	
	@Column(name= "country", length=20, nullable= false)
	private String country;
	
	@Column(name= "province", length=20, nullable= false)
	private String province;

	@Column(name = "city", length = 20, nullable = false)
	private String city;

	@Column(name = "postal_code", length = 10, nullable = false)

	private String postalCode;

	@OneToOne
	@JoinColumn(name= "user_id")
	private CandidateUser candidateUser;

	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(String residenceType) {
		this.residenceType = residenceType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public CandidateUser getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUser candidateUser) {
		this.candidateUser = candidateUser;
	}

}
