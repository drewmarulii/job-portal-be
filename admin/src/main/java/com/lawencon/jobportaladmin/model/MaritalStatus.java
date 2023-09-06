package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_marital_status")
public class MaritalStatus extends BaseEntity {

	@Column(name = "marital_code", length = 5, nullable = false,unique = true)
	private String maritalCode;

	@Column(name = "marital_name", length = 20, nullable = false)
	private String maritalName;

	public String getMaritalCode() {
		return maritalCode;
	}

	public void setMaritalCode(String maritalCode) {
		this.maritalCode = maritalCode;
	}

	public String getMaritalName() {
		return maritalName;
	}

	public void setMaritalName(String maritalName) {
		this.maritalName = maritalName;
	}

}