package com.lawencon.jobportalcandidate.dao;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.Question;

@Repository
public class QuestionDao extends AbstractJpaDao{
	
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public Question getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" q ");
			sqlb.append("FROM ");
			sqlb.append(" Question q ");
			sqlb.append(" WHERE ");
			sqlb.append(" q.questionCode = :code");
		
		final String sql = "SELECT q.id, q.questionCode,q.questionDetail, "
				+ "q.createdBy , q.createdAt , q.isActive , q.version "
				+ "FROM Question q "
				+ "WHERE q.questionCode = :code";
		
		final Question question = em().createQuery(sqlb.toString(),Question.class)
				.setParameter("code", code)
				.getSingleResult();
		
		return question;
		
				
	}
	
	
}
