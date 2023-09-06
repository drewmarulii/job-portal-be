package com.lawencon.jobportaladmin.dao;

import java.sql.Timestamp;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.Interview;

@Repository
public class InterviewDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public Interview getByApplicant(String id) {
		final StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ti.interview_date, ti.interview_location ");
		sql.append(" FROM t_interview ti WHERE ti.applicant_id = :id ");
		System.out.println("ID Applicant => " + id);
		try {
			final Object interviewObj = em().createNativeQuery(sql.toString()).setParameter("id", id).getSingleResult();

			final Object[] interviewArr = (Object[]) interviewObj;
			Interview interview = null;

			if (interviewArr.length > 0) {
				interview = new Interview();
				interview.setInterviewDate(Timestamp.valueOf(interviewArr[0].toString()).toLocalDateTime());
				interview.setInterviewLocation(interviewArr[1].toString());
			}

			return interview;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
