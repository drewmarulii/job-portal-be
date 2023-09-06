package com.lawencon.jobportaladmin.dao;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.CandidateProfile;

@Repository
public class CandidateProfileDao extends AbstractJpaDao{

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public CandidateProfile getProfileByNik(String nik) {
		
		final StringBuilder sql = new StringBuilder(); 
				sql.append("SELECT ");
				sql.append ("	cp ");
				sql.append ("FROM ");
				sql.append ("	CandidateProfile cp ");
				sql.append ("WHERE ");
				sql.append ("	cp.nik = :nik");
		
		final Object profileObj = em().createQuery(sql.toString())
				.setParameter("nik", nik)
				.getSingleResult();
		
		final Object[] profileArr = (Object[]) profileObj;
		final CandidateProfile profile = new CandidateProfile();
		profile.setId(profileArr[0].toString());
		profile.setFullname(profileArr[1].toString());
		profile.setCreatedBy(profileArr[2].toString());
		profile.setCreatedAt(LocalDateTime.parse(profileArr[3].toString()));
		profile.setIsActive(Boolean.valueOf(profileArr[4].toString()));
		profile.setVersion(Integer.valueOf(profileArr[5].toString()));
		
		return profile;
	}
}
