package com.lawencon.jobportaladmin.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.Applicant;
import com.lawencon.jobportaladmin.model.Assesment;
import com.lawencon.jobportaladmin.model.Review;

@Repository
public class AssesmentDao extends AbstractJpaDao {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	public Assesment getByApplicant(String applicantId) {
	
		final String sql = "SELECT ta.id, "
				+ " ta.assesment_date, "
				+ " ta.assesment_location, "
				+ " ta.notes  "
				+ " FROM "
				+ "	t_assesment ta "
				+ " WHERE "
				+ "	ta.applicant_id = :applicantId";
		
		final Object assObj = em().createNativeQuery(sql).setParameter("applicantId", applicantId)
				.getSingleResult();

		final Object[] assArr = (Object[]) assObj;
		
		Assesment assesment = null ;
		
		try {
			
			if (assArr.length > 0) {
				assesment = new Assesment();
				assesment.setId(assArr[0].toString());
				assesment.setAssesmentDate(Timestamp.valueOf(assArr[1].toString()).toLocalDateTime());
				assesment.setAssesmentLocation(assArr[2].toString());
				if(assArr[3]!=null) {
					
					assesment.setNotes(assArr[3].toString());
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return assesment;
	}
	
	public Assesment getByApplicantCode(String code) {
		Assesment assesment = null ;
		final StringBuilder sql = new StringBuilder();
				sql.append("SELECT ta.id,assesment_date , assesment_location, ");
				sql.append("tr.notes,tr.score,tap.id,tap.applicant_code ");
				sql.append( "FROM t_assesment ta ");
				sql.append( "INNER JOIN t_applicant tap ");
				sql.append( "ON tap.id = ta.applicant_id  ");
				sql.append("INNER JOIN t_review tr ");
				sql.append( "ON tr.applicant_id = tap.id ");
				sql.append( "WHERE tap.applicant_code = :applicantCode");
				try {
					final Object assObj = ConnHandler.getManager().createNativeQuery(sql.toString()).setParameter("applicantCode", code)
							.getSingleResult();

					final Object[] assArr = (Object[]) assObj;

					if (assArr.length > 0) {
						assesment = new Assesment();
						assesment.setId(assArr[0].toString());
						assesment.setAssesmentDate(LocalDateTime.parse(assArr[1].toString()));
						assesment.setAssesmentLocation(assArr[2].toString());
						
						final Applicant applicant = new Applicant();
						applicant.setId(assArr[5].toString());
						applicant.setApplicantCode(assArr[6].toString());
						
						final Review review = new Review();
						review.setNotes(assArr[3].toString());
						review.setScore(Float.valueOf(assArr[4].toString()));
						review.setApplicant(applicant);
						
						assesment.setApplicant(applicant);
						return assesment;
					}

					return assesment;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				
	}

}
