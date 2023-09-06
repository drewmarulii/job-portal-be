package com.lawencon.jobportalcandidate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.model.QuestionAnswer;

@Repository
public class QuestionAnswerDao extends AbstractJpaDao{
	private EntityManager em() {	
		return ConnHandler.getManager();
	}

	public List<QuestionAnswer> getAnswerByApplicant(String id){
		final StringBuilder sql = new StringBuilder();
				sql.append(	"select  ")
				.append( "	tqa.id as answerId, ")
				.append(" tqa.candidate_id as candidateId ")
				.append( "from  ")
				.append( "	t_question_answer tqa ")
				.append( "where tqa.applicant_id  = :appId " );
				
			final List<?>answerObjs = em().createNativeQuery(sql.toString()).setParameter("appId", id).getResultList();
			final List<QuestionAnswer> answerList = new ArrayList<>();
			if(answerObjs.size() > 0) {
				for(Object answerObj : answerObjs) {
					final Object[] answerArr = (Object[])answerObj;
					final QuestionAnswer answer = new QuestionAnswer();
					answer.setId(answerArr[0].toString());
					
					final CandidateUser candidate = new CandidateUser();
					candidate.setId(answerArr[1].toString());
					answer.setCandidateUser(candidate);
					
					answerList.add(answer);
				}
			}
			return answerList;
	}
}
