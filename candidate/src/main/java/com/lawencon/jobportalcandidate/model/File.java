package com.lawencon.jobportalcandidate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_file")
public class File extends BaseEntity {
	
	@Column(name = "filename", nullable = false)
	private String fileName;
	
	@Column(name = "file_extension",length = 5, nullable = false)
	private String fileExtension;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
}
