package com.lawencon.jobportalcandidate.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.CandidateFamily;

@Repository
public class CandidateFamilyDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<CandidateFamily>getFamilyByCandidate(String id){
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		final String sql = "SELECT  "
				+ "	tcf.id, "
				+ "	fullname, "
				+ "	relationship, "
				+ "	degree_name, "
				+ "	occupation, "
				+ "	birth_date, "
				+ "	birth_place "
				+ "FROM  "
				+ "	t_candidate_family tcf  "
				+ "WHERE  "
				+ "	user_id = :candidate"
				;

		final List<?>familyObjs = em().createNativeQuery(sql)
				.setParameter("candidate", id)
				.getResultList();
		final List<CandidateFamily> candidateFamilyList = new ArrayList<>();
		if(familyObjs.size() > 0) {
			for(Object familyObj : familyObjs) {
				final Object[] familyArr = (Object[]) familyObj;
				final CandidateFamily candidateFamily = new CandidateFamily();
				candidateFamily.setId(familyArr[0].toString());
				candidateFamily.setFullname(familyArr[1].toString());
				candidateFamily.setRelationship(familyArr[2].toString());
				candidateFamily.setDegreeName(familyArr[3].toString());
				candidateFamily.setOccupation(familyArr[4].toString());
				candidateFamily.setBirthDate(LocalDate.parse(familyArr[5].toString(),formatter));
				candidateFamily.setBirthPlace(familyArr[6].toString());
				
				candidateFamilyList.add(candidateFamily);
				
			}
		}
		return candidateFamilyList;
		
	}
	
	public List<CandidateFamily>getByCandidateEmail(String email){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT cf ");
		sql.append("FROM CandidateFamily ");
		sql.append("INNER JOIN CandidateUser cu ");
		sql.append("WHERE cu.userEmail = :email ");
		
		final List<CandidateFamily> familyList = em().createQuery(sql.toString(), CandidateFamily.class)
				.setParameter("email", email).getResultList();
		return familyList;
		
	}
}
