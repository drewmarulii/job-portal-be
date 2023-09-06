package com.lawencon.jobportaladmin.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.CandidateWorkExp;
import com.lawencon.jobportaladmin.util.BigDecimalUtil;

@Repository
public class CandidateWorkExpDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<CandidateWorkExp> getByCandidate(String id) {
		final List<CandidateWorkExp> works = new ArrayList<>();

		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append("	tcwe.id AS work_id, ");
		sql.append("	position_name, ");
		sql.append("	company_name, ");
		sql.append("	address, ");
		sql.append("	responsibility, ");
		sql.append("	reason_leave, ");
		sql.append("	last_salary, ");
		sql.append("	start_date, ");
		sql.append("	end_date ");
		sql.append("FROM ");
		sql.append("	t_candidate_work_exp tcwe ");
		sql.append("WHERE ");
		sql.append("	user_id = :candidate");

		final List<?> workObjs = em().createNativeQuery(sql.toString()).setParameter("candidate", id).getResultList();

		if (workObjs.size() > 0) {
			for (Object workObj : workObjs) {
				final Object[] workArr = (Object[]) workObj;
				final CandidateWorkExp work = new CandidateWorkExp();
				work.setId(workArr[0].toString());
				work.setPositionName(workArr[1].toString());
				work.setCompanyName(workArr[2].toString());
				work.setAddress(workArr[3].toString());
				work.setResponsibility(workArr[4].toString());
				work.setReasonLeave(workArr[5].toString());
				work.setLastSalary(BigDecimalUtil.parseToBigDecimal(workArr[6].toString()));
				work.setStartDate(LocalDate.parse(workArr[7].toString()));
				work.setEndDate(LocalDate.parse(workArr[8].toString()));
				works.add(work);
			}
		}

		return works;
	}

	public List<CandidateWorkExp> getByCandidateEmail(String email) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT cwe ");
		sql.append("FROM CandidateWorkExp cwe ");
		sql.append("INNER JOIN CandidateUser cu ");
		sql.append("WHERE cu.userEmail = :email");
		final List<CandidateWorkExp> workExpList = em().createQuery(sql.toString(), CandidateWorkExp.class)
				.setParameter("email", email).getResultList();
		return workExpList;
	}

	public CandidateWorkExp getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
		sqlb.append("SELECT ");
		sqlb.append(" cw ");
		sqlb.append("FROM ");
		sqlb.append(" CandidateWorkExp cw ");
		sqlb.append("WHERE ");
		sqlb.append(" cw.workingCode = :code");

		final CandidateWorkExp work = this.em().createQuery(sqlb.toString(), CandidateWorkExp.class)
				.setParameter("code", code).getSingleResult();

		return work;
	}
}
