package com.lawencon.jobportalcandidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_hiring_status")
public class HiringStatus extends BaseEntity {
	@Column(name = "status_code",length = 5 , nullable =false,unique = true)
	private String statusCode;
	
	@Column(name = "status_name",length = 20, nullable = false)
	private String statusName;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}	
