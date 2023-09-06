package com.lawencon.jobportaladmin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.PersonType;

@Repository
public class PersonTypeDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public PersonType getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
		sqlb.append("SELECT ");
		sqlb.append(" pt ");
		sqlb.append("FROM ");
		sqlb.append(" PersonType pt ");
		sqlb.append("WHERE ");
		sqlb.append(" pt.typeCode = :code");

		final String sql = "SELECT " + "	pt.id, " + "	pt.typeCode, " + " pt.typeName, " + "	pt.version "
				+ "FROM " + "	PersonType pt " + "WHERE " + "	pt.typeCode = :code";

		final Object personTypeObj = this.em().createQuery(sql).setParameter("code", code)
				.getSingleResult();

		final Object[] personTypeArr = (Object[]) personTypeObj;

		PersonType personType = null;
		
		if (personTypeArr.length > 0) {			
			personType = new PersonType();
			personType.setId(personTypeArr[0].toString());
			personType.setTypeCode(personTypeArr[1].toString());
			personType.setTypeName(personTypeArr[2].toString());
			personType.setVersion(Integer.valueOf(personTypeArr[3].toString()));
		}

		return personType;
	}

}
