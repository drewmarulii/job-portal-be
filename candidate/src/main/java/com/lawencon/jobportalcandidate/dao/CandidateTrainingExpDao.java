package com.lawencon.jobportalcandidate.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.CandidateTrainingExp;

@Repository
public class CandidateTrainingExpDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<CandidateTrainingExp> getByCandidate(String id) {
		final List<CandidateTrainingExp> trainings = new ArrayList<>();

		final StringBuilder sql = new StringBuilder();
			sql.append("SELECT ")
			.append("	tcte.id AS training_id, ")
			.append("	organization_name, ")
			.append("	training_name, ")
			.append("	description, ")
			.append("	start_date, ")
			.append("	end_date ")
			.append("FROM ")
			.append("	t_candidate_training_exp tcte ")
			.append("WHERE ")
			.append("	user_id = :candidate");

		final List<?> trainingObjs = em().createNativeQuery(sql.toString()).setParameter("candidate", id)
				.getResultList();

		if (trainingObjs.size() > 0) {
			for (Object trainingObj : trainingObjs) {
				final Object[] trainingArr = (Object[]) trainingObj;
				final CandidateTrainingExp training = new CandidateTrainingExp();
				training.setId(trainingArr[0].toString());
				training.setOrganizationName(trainingArr[1].toString());
				training.setTrainingName(trainingArr[2].toString());
				training.setDescription(trainingArr[3].toString());
				training.setStartDate(LocalDate.parse(trainingArr[4].toString()));
				training.setEndDate(LocalDate.parse(trainingArr[5].toString()));

				trainings.add(training);
			}
		}

		return trainings;
	}
}
