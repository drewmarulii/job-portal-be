package com.lawencon.jobportalcandidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.CandidateProfile;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.model.File;

@Repository
public class CandidateUserDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public CandidateUser getByUsername(String email) {
		final String sql = "SELECT cu FROM CandidateUser cu " 
				+ " WHERE cu.userEmail = :email ";

		
		final CandidateUser userGet = em().createQuery(sql,CandidateUser.class)
				.setParameter("email", email).getSingleResult();
			
			return userGet;
	}
	
	
	
}
