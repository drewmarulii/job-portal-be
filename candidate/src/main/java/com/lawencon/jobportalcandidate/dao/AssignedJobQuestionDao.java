package com.lawencon.jobportalcandidate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.AssignedJobQuestion;
import com.lawencon.jobportalcandidate.model.Question;

@Repository
public class AssignedJobQuestionDao extends AbstractJpaDao {

	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<AssignedJobQuestion> getByJob(String id){
		final String sql = "SELECT tajq.question_id,tajq.ver "
				+ " FROM t_assigned_job_question tajq "
				+ " WHERE tajq.job_id =:id ";
		
		final List<?> assignedJobQuestionObjs = em().createNativeQuery(sql).setParameter("id", id).getResultList();
		
		final List<AssignedJobQuestion> assignedJobQuestions = new ArrayList<>();
		
		if(assignedJobQuestionObjs.size()>0) {
			for(Object assignedJobQuestionObj: assignedJobQuestionObjs) {
				final Object[] assignedJobQuestionObjArr= (Object[]) assignedJobQuestionObj;
	
				final AssignedJobQuestion assignedJobQuestion = new AssignedJobQuestion();
				
				final Question question = new Question();
				question.setId(assignedJobQuestionObjArr[0].toString());
//				question.setQuestionDetail(assignedJobQuestionObjArr[1].toString());
				
				assignedJobQuestion.setQuestion(question);
				assignedJobQuestion.setVersion(Integer.valueOf(assignedJobQuestionObjArr[1].toString()));
				assignedJobQuestions.add(assignedJobQuestion);
			}
		}
		
		return assignedJobQuestions;
		
	}
}
