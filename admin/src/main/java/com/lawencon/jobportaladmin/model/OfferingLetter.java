package com.lawencon.jobportaladmin.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_offering_letter")
public class OfferingLetter extends BaseEntity {
	@OneToOne
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;

	@Column(name = "address", length = 50, nullable = false)
	private String address;

	@Column(name = "salary", nullable = false)
	private BigDecimal salary;

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

}
