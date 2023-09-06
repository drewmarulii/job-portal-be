package com.lawencon.jobportaladmin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.CandidateReferences;

@Repository
public class CandidateReferencesDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<CandidateReferences> getByCandidate(String id) {
		final List<CandidateReferences> references = new ArrayList<>();
		
		final StringBuilder sql = new StringBuilder();
				sql.append ("SELECT ");
				sql.append ("	tcr.id AS ref_id, ");
				sql.append ("	fullname, ");
				sql.append ("	relationship, ");
				sql.append ("	occupation, ");
				sql.append ("	phone_number, ");
				sql.append ("	email, ");
				sql.append ("	company, ");
				sql.append ("	description ");
				sql.append ("FROM ");
				sql.append ("	t_candidate_references tcr ");
				sql.append ("WHERE ");
				sql.append ("	user_id = :candidate");
		
		final List<?> refObjs = em().createNativeQuery(sql.toString())
				.setParameter("candidate", id)
				.getResultList();
		
		if (refObjs.size() > 0) {
			for (Object refObj : refObjs) {
				final Object[] refArr = (Object[]) refObj;
				final CandidateReferences ref = new CandidateReferences();
				ref.setId(refArr[0].toString());
				ref.setFullName(refArr[1].toString());
				ref.setRelationship(refArr[2].toString());
				ref.setOccupation(refArr[3].toString());
				ref.setPhoneNumber(refArr[4].toString());
				ref.setEmail(refArr[5].toString());
				ref.setCompany(refArr[6].toString());
				ref.setDescription(refArr[7].toString());
				
				references.add(ref);
			}
		}
		
		return references;
	}
	
	public List<CandidateReferences> getByCandidateEmail(String email){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT cf ");
		sql.append("FROM CandidateReferences cf ");
		sql.append("INNER JOIN CandidateUser cu ");
		sql.append("WHERE cu.userEmail = :email");
		
		final List<CandidateReferences> referencesList = em().createQuery(sql.toString(),CandidateReferences.class)
				.setParameter("email", email)
				.getResultList();
		return referencesList;
	}
	
	public CandidateReferences getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
		sqlb.append("SELECT ")
			.append(" cr ")
			.append("FROM ")
			.append(" CandidateReferences cr ")
			.append("WHERE ")
			.append(" cr.referenceCode = :code");
		
		final CandidateReferences reference = this.em().createQuery(sqlb.toString(), CandidateReferences.class)
					.setParameter("code", code).getSingleResult();
		
		return reference;
	}
	
}
