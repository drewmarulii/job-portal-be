package com.lawencon.jobportaladmin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.AssignedJobQuestion;
import com.lawencon.jobportaladmin.model.Question;

@Repository
public class AssignedJobQuestionDao extends AbstractJpaDao {

	
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	public List<AssignedJobQuestion> getByJob(String id){
		final String sql = "SELECT tq.id, tq.question_detail FROM t_assigned_job_question tajq "
				+ " INNER JOIN t_question tq on tq.id = tajq.question_id "
				+ " WHERE tajq.job_id =:id ";
		
		final List<?> assignedJobQuestionObjs = em().createNativeQuery(sql).setParameter("id", id).getResultList();
		
		final List<AssignedJobQuestion> assignedJobQuestions = new ArrayList<>();
		
		if(assignedJobQuestionObjs.size()>0) {
			for(Object assignedJobQuestionObj: assignedJobQuestionObjs) {
				final Object[] assignedJobQuestionObjArr= (Object[]) assignedJobQuestionObj;
				
				final AssignedJobQuestion assignedJobQuestion = new AssignedJobQuestion();
				
				final Question question = new Question();
				question.setId(assignedJobQuestionObjArr[0].toString());
				question.setQuestionDetail(assignedJobQuestionObjArr[1].toString());
				
				assignedJobQuestion.setQuestion(question);
				assignedJobQuestions.add(assignedJobQuestion);
			}
		}
		return assignedJobQuestions;
		
	}
	
	public List<AssignedJobQuestion> getByJobCode(String code){
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT tq.id, tq.question_detail FROM t_assigned_job_question tajq ");
		sql.append(" INNER JOIN t_question tq on tq.id =tajq.question_id");
		sql.append("INNER JOIN t_job tj ON tajq.job_id = tj.id ");
		sql.append(" WHERE tj.job_code =:code ");
final List<?> assignedJobQuestionObjs = em().createNativeQuery(sql.toString()).setParameter("code", code).getResultList();
		
		final List<AssignedJobQuestion> assignedJobQuestions = new ArrayList<>();
		
		if(assignedJobQuestionObjs.size()>0) {
			for(Object assignedJobQuestionObj: assignedJobQuestionObjs) {
				final Object[] assignedJobQuestionObjArr= (Object[]) assignedJobQuestionObj;
				
				final AssignedJobQuestion assignedJobQuestion = new AssignedJobQuestion();
				
				final Question question = new Question();
				question.setId(assignedJobQuestionObjArr[0].toString());
				question.setQuestionDetail(assignedJobQuestionObjArr[1].toString());
				
				assignedJobQuestion.setQuestion(question);
				assignedJobQuestions.add(assignedJobQuestion);
			}
		}
		return assignedJobQuestions;
	}
}
