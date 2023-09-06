package com.lawencon.jobportalcandidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.HiringStatus;

@Repository
public class HiringStatusDao extends AbstractJpaDao  {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
		
	public HiringStatus getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" hs ");
			sqlb.append("FROM ");
			sqlb.append(" HiringStatus hs ");
			sqlb.append(" WHERE ");
			sqlb.append(" hs.statusCode = :code");
			

		final HiringStatus hiringStatus= this.em().createQuery(sqlb.toString(),HiringStatus.class)
				.setParameter("code", code)
				.getSingleResult();

		return hiringStatus;
	}
}
