package com.lawencon.jobportalcandidate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;


import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;

import com.lawencon.jobportalcandidate.model.CandidateReferences;

@Repository
public class CandidateReferencesDao extends AbstractJpaDao {
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<CandidateReferences> getByCandidate(String id) {
		final List<CandidateReferences> references = new ArrayList<>();
		
		final String sql = "SELECT "
				+ "	tcr.id AS ref_id, "
				+ "	fullname, "
				+ "	relationship, "
				+ "	occupation, "
				+ "	phone_number, "
				+ "	email, "
				+ "	company, "
				+ "	description "
				+ "FROM "
				+ "	t_candidate_references tcr "
				+ "WHERE "
				+ "	user_id = :candidate";
		

		final List<?> refObjs = em().createNativeQuery(sql)
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
}