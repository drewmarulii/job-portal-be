package com.lawencon.jobportalcandidate.dao;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.MaritalStatus;

@Repository
public class MartialStatusDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	public MaritalStatus getByCode(String code) {
		
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" ms ");
			sqlb.append("FROM ");
			sqlb.append(" MaritalStatus ms ");
			sqlb.append("WHERE ");
			sqlb.append(" ms.maritalCode = :code");
		
		final String sql = "SELECT ms.id,ms.maritalCode,ms.maritalName, "
				+ "ms.createdBy,ms.createdAt,ms.isActive,ms.version  "
				+ "FROM MaritalStatus ms "
				+ "WHERE ms.maritalCode = :code ";
		
		final Object maritalObj = em().createQuery(sqlb.toString())
				.setParameter("code", code)
				.getSingleResult();
		
		final Object[] maritalArr = (Object[]) maritalObj;
		final MaritalStatus marital = new MaritalStatus();
		marital.setId(maritalArr[0].toString());
		marital.setMaritalCode(maritalArr[1].toString());
		marital.setMaritalName(maritalArr[2].toString());
		marital.setCreatedBy(maritalArr[3].toString());
		marital.setCreatedAt(LocalDateTime.parse(maritalArr[4].toString()));
		marital.setIsActive(Boolean.valueOf(maritalArr[5].toString()));
		marital.setVersion(Integer.valueOf(maritalArr[6].toString()));
		
		return marital;
	}
}
