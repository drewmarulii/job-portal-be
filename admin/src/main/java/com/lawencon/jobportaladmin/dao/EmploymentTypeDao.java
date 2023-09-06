package com.lawencon.jobportaladmin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.EmploymentType;

@Repository
public class EmploymentTypeDao extends AbstractJpaDao{
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	public EmploymentType getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
				sql.append("SELECT ");
				sql.append ("et.id, ");
				sql.append ("et.employmentTypeCode, ");
				sql.append (" et.employmentTypeName, ");
				sql.append ("et.createdBy, ");
				sql.append ("et.createdAt, ");
				sql.append ("et.isActive, ");
				sql.append ("et.version ");
				sql.append ("FROM ");
				sql.append ("EmploymentType et ");
				sql.append ("WHERE ");
				sql.append ("et.employmentTypeCode = :code");
		

				final EmploymentType empTypeObj = em().createQuery(sql.toString(),EmploymentType.class).setParameter("code", code).getSingleResult();
//				final Object empTypeObj = em().createQuery(sql.toString()).setParameter("code", code).getSingleResult();
//		final Object[] empTypeArr = (Object[]) empTypeObj;
//		EmploymentType empType = null;
//		
//		if (empTypeArr.length > 0) {
//			empType = new EmploymentType();
//			
//			empType.setId(empTypeArr[0].toString());
//			empType.setEmploymentTypeCode(empTypeArr[1].toString());
//			empType.setEmploymentTypeName(empTypeArr[2].toString());
//			empType.setCreatedBy(empTypeArr[3].toString());
//			empType.setCreatedAt(LocalDateTime.parse(empTypeArr[4].toString()));
//			empType.setIsActive(Boolean.valueOf(empTypeArr[5].toString()));
//			empType.setVersion(Integer.valueOf(empTypeArr[6].toString()));
//		}
		
		return empTypeObj;
	}
}
