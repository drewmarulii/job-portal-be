package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_file_type",uniqueConstraints = {@UniqueConstraint(name="codeAndName",columnNames= {"type_code","type_name"})})
public class FileType extends BaseEntity{
	
	@Column(name = "type_code",length = 5, nullable = false,unique = true)
	private String typeCode;
	
	@Column(name = "type_name",length = 20, nullable = false,unique = true)
	private String typeName;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
