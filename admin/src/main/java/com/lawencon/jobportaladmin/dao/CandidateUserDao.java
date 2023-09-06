package com.lawencon.jobportaladmin.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.CandidateUser;

@Repository
public class CandidateUserDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	
	public CandidateUser getByEmail(String candidateEmail) {
		final String sql =  "SELECT "
				+ "	cu "
				+ "FROM "
				+ "	CandidateUser cu "
				+ "WHERE "
				+ "	cu.userEmail= :candidateEmail";
	

		
		final CandidateUser candidateUser = this.em().createQuery(sql,CandidateUser.class).setParameter("candidateEmail", candidateEmail).getSingleResult();
		
//		final Object[] candidateUserArr = (Object[]) candidateUserObj;
//		CandidateUser candidateUser = null;
//		
//		if (candidateUserArr.length > 0) {
//			candidateUser = new CandidateUser();
//			candidateUser.setId(candidateUserArr[0].toString());
//			candidateUser.setVersion(Integer.valueOf(candidateUserArr[1].toString()));
//		}
		
		return candidateUser;
		
	}
	
	
}
