package com.lawencon.jobportaladmin.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_mcu")
public class Mcu extends BaseEntity{

	@OneToOne
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;

	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
