package com.lawencon.jobportaladmin.dao;


import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.FileType;

@Repository
public class FileTypeDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public FileType getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" ft ");
			sqlb.append("FROM ");
			sqlb.append(" FileType ft ");
			sqlb.append("WHERE ");
			sqlb.append(" ft.typeCode = :code");
			
		final String sql = "SELECT "
				+ "	ft.id, "
				+ "	ft.typeCode, "
				+ " ft.typeName, "
				+ "	ft.createdBy, "
				+ "	ft.createdAt, "
				+ "	ft.isActive, "
				+ "	ft.version "
				+ "FROM "
				+ "	FileType ft "
				+ "WHERE "
				+ "	ft.typeCode = :code";
		
		final Object fileTypeObj = this.em().createQuery(sql).setParameter("code", code).getSingleResult();
		
		final Object[] fileTypeArr = (Object[]) fileTypeObj;
		FileType fileType = null;
		
		if (fileTypeArr.length > 0) {
			fileType = new FileType();
			
			fileType.setId(fileTypeArr[0].toString());
			fileType.setTypeCode(fileTypeArr[1].toString());
			fileType.setTypeName(fileTypeArr[2].toString());
			fileType.setCreatedBy(fileTypeArr[3].toString());
			fileType.setCreatedAt(LocalDateTime.parse(fileTypeArr[4].toString()));
			fileType.setIsActive(Boolean.valueOf(fileTypeArr[5].toString()));
			fileType.setVersion(Integer.valueOf(fileTypeArr[6].toString()));
		}
		
		return fileType;
	}
	
}
