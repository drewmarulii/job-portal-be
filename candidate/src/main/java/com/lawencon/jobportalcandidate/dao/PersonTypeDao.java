package com.lawencon.jobportalcandidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.PersonType;

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
		

		final String sql = "SELECT "
				+ "	pt.id, "
				+ "	pt.typeCode, "
				+ " pt.typeName, "
				+ "	pt.createdBy, "
				+ "	pt.createdAt, "
				+ "	pt.isActive, "
				+ "	pt.version "
				+ "FROM "
				+ "	PersonType pt "
				+ "WHERE "
				+ "	pt.typeCode = :code";

		final PersonType personType = this.em().createQuery(sqlb.toString(), PersonType.class)
				.setParameter("code", code)
				.getSingleResult();

		return personType;
	}
}