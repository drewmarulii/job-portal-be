package com.lawencon.jobportaladmin.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.Benefit;

@Repository
public class BenefitDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<Benefit> getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT b ");
		sql.append("FROM Benefit b ");
		sql.append("WHERE b.benefitCode = :code ");
		final List<Benefit> benefitList = em().createQuery(sql.toString(), Benefit.class).setParameter("code", code)
				.getResultList();
		return benefitList;

	}
	


}
