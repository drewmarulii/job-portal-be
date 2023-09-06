package com.lawencon.jobportaladmin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name ="t_owned_benefit")
public class OwnedBenefit extends BaseEntity{

	@OneToOne
	@JoinColumn(name ="benefit_id")
	private Benefit benefit;
	
	@OneToOne
	@JoinColumn(name ="job_id")
	private Job job;

	public Benefit getBenefit() {
		return benefit;
	}

	public void setBenefit(Benefit benefit) {
		this.benefit = benefit;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}
	
}
