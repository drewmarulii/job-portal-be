package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_employment_type")
public class EmploymentType extends BaseEntity{

	@Column(name = "employment_type_code",length = 5, nullable = false,unique = true)
	private String employmentTypeCode;
	
	@Column(name = "employment_type_name",length = 20, nullable = false)
	private String employmentTypeName;

	public String getEmploymentTypeCode() {
		return employmentTypeCode;
	}

	public void setEmploymentTypeCode(String employmentTypeCode) {
		this.employmentTypeCode = employmentTypeCode;
	}

	public String getEmploymentTypeName() {
		return employmentTypeName;
	}

	public void setEmploymentTypeName(String employmentTypeName) {
		this.employmentTypeName = employmentTypeName;
	}
	
	
}
