package com.lawencon.jobportaladmin.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.CandidateEducation;
import com.lawencon.jobportaladmin.model.CandidateUser;

@Repository
public class CandidateEducationDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<CandidateEducation> getEducationByCandidate(String id) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT  ");
		sql.append("	tce.id, ");
		sql.append("	degree_name, ");
		sql.append("	institution_name, ");
		sql.append("	majors, ");
		sql.append("	cgpa, ");
		sql.append("	start_year, ");
		sql.append("	end_year, ");
		sql.append("	user_id ");
		sql.append("FROM  ");
		sql.append("	t_candidate_education tce  ");
		sql.append("INNER JOIN  ");
		sql.append("	t_candidate_user tcu  ");
		sql.append("ON ");
		sql.append("	tcu.id = tce.user_id ");
		sql.append("WHERE  ");
		sql.append("	tce.user_id  = :candidate");
		final List<?> educationObjs = em().createNativeQuery(sql.toString()).setParameter("candidate", id)
				.getResultList();
		final List<CandidateEducation> educationList = new ArrayList<>();
		if (educationObjs.size() > 0) {
			for (Object educationObj : educationObjs) {
				final Object[] educationArr = (Object[]) educationObj;
				final CandidateEducation candidateEducation = new CandidateEducation();

				candidateEducation.setId(educationArr[0].toString());
				candidateEducation.setDegreeName(educationArr[1].toString());
				candidateEducation.setInstitutionName(educationArr[2].toString());
				candidateEducation.setMajors(educationArr[3].toString());
				candidateEducation.setCgpa(Float.valueOf(educationArr[4].toString()));
				candidateEducation.setStartYear(LocalDate.parse(educationArr[5].toString()));
				candidateEducation.setEndYear(LocalDate.parse(educationArr[6].toString()));

				final CandidateUser candidateUser = new CandidateUser();
				candidateUser.setId(educationArr[7].toString());
				candidateEducation.setCandidateUser(candidateUser);

				educationList.add(candidateEducation);
			}
		}
		return educationList;
	}

	public List<CandidateEducation> getByCandidateEmail(String email) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT ce ");
		sql.append("FROM CandidateEducation ce ");
		sql.append("INNER JOIN CandidateUser cu ");
		sql.append("WHERE cu.userEmail = :email");

		final List<CandidateEducation> educationList = em().createQuery(sql.toString(), CandidateEducation.class)
				.setParameter("email", email).getResultList();
		return educationList;
	}

	public CandidateEducation getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
		sqlb.append("SELECT ");
		sqlb.append(" ce ");
		sqlb.append("FROM ");
		sqlb.append(" CandidateEducation ce ");
		sqlb.append("WHERE ");
		sqlb.append(" ce.educationCode = :code");

		final CandidateEducation education = this.em().createQuery(sqlb.toString(), CandidateEducation.class)
				.setParameter("code", code).getSingleResult();

		return education;
	}
}
