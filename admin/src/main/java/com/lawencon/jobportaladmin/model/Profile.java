package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name ="t_profile")
public class Profile extends BaseEntity{

	@Column(name ="full_name", length = 50,  nullable = false)
	private String fullName;
	
	@OneToOne
	@JoinColumn(name ="photo_id")
	private File photo;
	
	@Column(name ="phone_number",  length = 13, nullable = true,unique = true)
	private String phoneNumber;
	
	@Column(name ="address",  nullable = true)
	private String address;
	
	@OneToOne
	@JoinColumn(name ="person_type_id")
	private PersonType personType;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public PersonType getPersonType() {
		return personType;
	}

	public void setPersonType(PersonType personType) {
		this.personType = personType;
	}
	
	
}
