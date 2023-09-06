package com.lawencon.jobportaladmin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.CandidateLanguage;
import com.lawencon.jobportaladmin.model.CandidateUser;

@Repository
public class CandidateLanguageDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<CandidateLanguage>getLanguageByCandidate(String id){
		final StringBuilder sql = new StringBuilder();
				sql.append("SELECT  ");
				sql.append ("	id, ");
				sql.append ("	language_name, ");
				sql.append ("	writing_rate, ");
				sql.append ("	speaking_rate, ");
				sql.append ("	listening_rate, ");
				sql.append ("	user_id ");
				sql.append ("FROM  ");
				sql.append ("	t_candidate_language tcl ");
				sql.append (" WHERE");
				sql.append (" user_id = :candidate ");
		final List<?> languageObjs = em().createNativeQuery(sql.toString())
				.setParameter("candidate", id)
				.getResultList();
		final List<CandidateLanguage> candidateLanguageList = new ArrayList<>();
		if(languageObjs.size() > 0) {
			for(Object languageObj : languageObjs) {
				final Object[] languageArr = (Object[]) languageObj;
				final CandidateLanguage candidateLanguage = new CandidateLanguage();
				
				candidateLanguage.setId(languageArr[0].toString());
				candidateLanguage.setLanguageName(languageArr[1].toString());
				candidateLanguage.setWritingRate(languageArr[2].toString());
				candidateLanguage.setSpeakingRate(languageArr[3].toString());
				candidateLanguage.setListeningRate(languageArr[4].toString());
				
				final CandidateUser candidateUser = new CandidateUser();
				candidateUser.setId(languageArr[5].toString());
				candidateLanguage.setCandidateUser(candidateUser);
				
				candidateLanguageList.add(candidateLanguage);
				
			}
		}
		return candidateLanguageList;
		
	}
	public List<CandidateLanguage>getByCandidateEmail(String email){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT cl ");
		sql.append("FROM CandidateLanguage cl ");
		sql.append("INNER JOIN CandidateUser cu ");
		sql.append("WHERE cu.userEmail = :email ");
		
		final List<CandidateLanguage>languageList = em().createQuery(sql.toString(), CandidateLanguage.class)
				.setParameter("email", email)
				.getResultList();
		return languageList;
	}
	
	public CandidateLanguage getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
		sqlb.append("SELECT ")
			.append(" cl ")
			.append("FROM ")
			.append(" CandidateLanguage cl ")
			.append("WHERE ")
			.append(" cl.languageCode = :code");
		
		final CandidateLanguage language = this.em().createQuery(sqlb.toString(), CandidateLanguage.class)
					.setParameter("code", code).getSingleResult();
		
		return language;
	}
}
