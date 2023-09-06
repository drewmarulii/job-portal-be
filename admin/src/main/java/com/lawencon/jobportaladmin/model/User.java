package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_user")
public class User extends BaseEntity{

	@Column(name ="user_email", length = 50, nullable = false,unique = true)
	private String userEmail;
	
	@Column(name ="user_password",  nullable = false)
	private String userPassword;
	
	@OneToOne
	@JoinColumn(name ="profile_id")
	private Profile profile;
	
	@OneToOne
	@JoinColumn(name ="role_id")
	private Role role;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
