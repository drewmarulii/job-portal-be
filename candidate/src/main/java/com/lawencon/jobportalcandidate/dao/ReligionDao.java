package com.lawencon.jobportalcandidate.dao;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.Religion;

@Repository
public class ReligionDao extends AbstractJpaDao  {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Religion getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" tr ");
			sqlb.append("FROM ");
			sqlb.append(" Religion tr ");
			sqlb.append("WHERE ");
			sqlb.append(" tr.religionCode = :code");

		final String sql = "SELECT r.id,r.religionCode, "
				+ "r.religionName,r.createdBy,r.createdAt,r.isActive,r.version "
				+ "FROM Religion r "
				+ "WHERE r.religionCode = :code ";
		
		final Object religionObj = em().createQuery(sqlb.toString())
				.setParameter("code", code)
				.getSingleResult();
		
		final Object[] religionArr = (Object[])religionObj;
		final Religion religion = new Religion();
		religion.setId(religionArr[0].toString());
		religion.setReligionCode(religionArr[1].toString());
		religion.setReligionName(religionArr[2].toString());
		religion.setCreatedBy(religionArr[3].toString());
		religion.setCreatedAt(LocalDateTime.parse(religionArr[4].toString()));
		religion.setIsActive(Boolean.valueOf(religionArr[5].toString()));
		religion.setVersion(Integer.valueOf(religionArr[6].toString()));
		
		return religion;
		
		
	}
}
