package com.lawencon.jobportalcandidate.dao;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.EmploymentType;

@Repository
public class EmploymentTypeDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public EmploymentType getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append ("et.id, ");
			sqlb.append ("et.employmentTypeCode, ");
			sqlb.append (" et.employmentTypeName, ");
			sqlb.append ("et.createdBy, ");
			sqlb.append ("et.createdAt, ");
			sqlb.append ("et.isActive, ");
			sqlb.append ("et.version ");
			sqlb.append ("FROM ");
			sqlb.append ("EmploymentType et ");
			sqlb.append ("WHERE ");
			sqlb.append ("et.employmentTypeCode = :code");
		
		final String sql = "SELECT "
				+ "	et.id, "
				+ "	et.employmentTypeCode, "
				+ " et.employmentTypeName, "
				+ "	et.createdBy, "
				+ "	et.createdAt, "
				+ "	et.isActive, "
				+ "	et.version "
				+ "FROM "
				+ "	EmploymentType et "
				+ "WHERE "
				+ "	et.employmentTypeCode = :code";
		
		final Object empTypeObj = this.em().createQuery(sqlb.toString())
				.setParameter("code", code)
				.getSingleResult();
		
		final Object[] empTypeArr = (Object[]) empTypeObj;
		EmploymentType empType = null;
		
		if (empTypeArr.length > 0) {
			empType = new EmploymentType();
			
			empType.setId(empTypeArr[0].toString());
			empType.setEmploymentTypeCode(empTypeArr[1].toString());
			empType.setEmploymentTypeName(empTypeArr[2].toString());
			empType.setCreatedBy(empTypeArr[3].toString());
			empType.setCreatedAt(LocalDateTime.parse(empTypeArr[4].toString()));
			empType.setIsActive(Boolean.valueOf(empTypeArr[5].toString()));
			empType.setVersion(Integer.valueOf(empTypeArr[6].toString()));
		}
		
		return empType;
	}
}
