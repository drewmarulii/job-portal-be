package com.lawencon.jobportalcandidate.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.CandidateProjectExp;

@Repository
public class CandidateProjectExpDao extends AbstractJpaDao{
	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<CandidateProjectExp> getByCandidate(String id) {
		final List<CandidateProjectExp> projects = new ArrayList<>();

		final String sql = "SELECT "
				+ "	tcpe.id AS project_id, "
				+ "	project_name, "
				+ "	project_url, "
				+ "	description, "
				+ "	start_date, "
				+ "	end_date "
				+ "FROM "
				+ "	t_candidate_project_exp tcpe "
				+ "WHERE "
				+ "	user_id = :candidate";
		

		final List<?> projectObjs = em().createNativeQuery(sql)
				.setParameter("candidate", id)
				.getResultList();
		
		if (projectObjs.size() > 0) {
			for (Object projectObj : projectObjs) {
				final Object[] projectArr = (Object[]) projectObj;
				final CandidateProjectExp project = new CandidateProjectExp();
				project.setId(projectArr[0].toString());
				project.setProjectName(projectArr[1].toString());
				project.setProjectUrl(projectArr[2].toString());
				project.setDescription(projectArr[3].toString());
				project.setStartDate(LocalDate.parse(projectArr[4].toString()));
				project.setEndDate(LocalDate.parse(projectArr[5].toString()));
				
				projects.add(project);
			}
		}
		
		return projects;
	}
}
