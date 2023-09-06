package com.lawencon.jobportalcandidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.CandidateStatus;

@Repository
public class CandidateStatusDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public CandidateStatus getByCode(String statusCode) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT cs FROM CandidateStatus AS cs WHERE cs.statusCode=:statusCode ");

		final CandidateStatus candidateStatus = em()
				.createQuery(sql.toString(), CandidateStatus.class)
				.setParameter("statusCode", statusCode).getSingleResult();

		return candidateStatus;
	}

}
