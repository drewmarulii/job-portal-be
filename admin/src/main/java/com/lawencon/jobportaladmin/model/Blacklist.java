package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_blacklist")
public class Blacklist extends BaseEntity {
	@Column(name = "email", length = 50, nullable = false,unique = true)
	private String email;

	@Column(name = "notes", nullable = false)
	private String notes;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
