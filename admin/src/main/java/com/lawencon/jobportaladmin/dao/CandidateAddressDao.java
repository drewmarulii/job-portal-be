package com.lawencon.jobportaladmin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.CandidateAddress;
import com.lawencon.jobportaladmin.model.CandidateProfile;
import com.lawencon.jobportaladmin.model.CandidateUser;

@Repository
public class CandidateAddressDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<CandidateAddress> getByCandidateId(String id){
		final StringBuilder sql = new StringBuilder(); 
				sql.append ("SELECT  ");
				sql.append ( "tca.id, ");
				sql.append ("address, ");
				sql.append ("residence_type, ");
				sql.append ("country, ");
				sql.append ("province, ");
				sql.append ("city, ");
				sql.append ("postal_code, ");
				sql.append ("user_id,");
				sql.append (" tcp.fullname ");
				sql.append ("FROM  ");
				sql.append ("	t_candidate_address tca  ");
				sql.append ("INNER JOIN  ");
				sql.append ("	t_candidate_user tcu  ");
				sql.append ("ON  ");
				sql.append ("	tcu.id = tca.user_id  ");
				sql.append ("INNER JOIN ");
				sql.append ("	t_candidate_profile tcp  ");
				sql.append ("ON ");
				sql.append ("	tcp.id = tcu.profile_id  ");
				sql.append ("WHERE  ");
				sql.append ("	tca.user_id  = :candidate");
		
		final List<?> candidateAddressObjs = em().createNativeQuery(sql.toString())
				.setParameter("candidate", id)
				.getResultList();
		
		final List<CandidateAddress> addresses = new ArrayList<>();
		CandidateAddress candidateAddress = null;
		if(candidateAddressObjs.size() > 0) {
			for(Object candidateAddressObj : candidateAddressObjs) {
				final Object[] candidateAddressArr = (Object[]) candidateAddressObj;
				candidateAddress = new CandidateAddress();
				candidateAddress.setId(candidateAddressArr[0].toString());
				candidateAddress.setAddress(candidateAddressArr[1].toString());
				candidateAddress.setResidenceType(candidateAddressArr[2].toString());
				candidateAddress.setCountry(candidateAddressArr[3].toString());
				candidateAddress.setProvince(candidateAddressArr[4].toString());
				candidateAddress.setCity(candidateAddressArr[5].toString());
				candidateAddress.setPostalCode(candidateAddressArr[6].toString());
				
				final CandidateProfile candidateProfile = new CandidateProfile();
				candidateProfile.setFullname(candidateAddressArr[8].toString());
				
				final CandidateUser candidateUser = new CandidateUser();
				candidateUser.setId(candidateAddressArr[7].toString());
				candidateUser.setCandidateProfile(candidateProfile);
				candidateAddress.setCandidateUser(candidateUser);
				
				addresses.add(candidateAddress);
			}
		}
		
		
		return addresses;
		
	}

	public CandidateAddress getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" ca ");
			sqlb.append("FROM ");
			sqlb.append(" CandidateAddress ca ");
			sqlb.append("WHERE ");
			sqlb.append(" ca.addressCode = :code");
		
		final CandidateAddress address = this.em().createQuery(sqlb.toString(),CandidateAddress.class).setParameter("code", code).getSingleResult();
	
		return address;
	}
}
