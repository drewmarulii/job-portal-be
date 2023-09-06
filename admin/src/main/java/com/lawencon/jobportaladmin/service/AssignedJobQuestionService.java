package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.AssignedJobQuestionDao;
import com.lawencon.jobportaladmin.dao.QuestionDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.assignedjobquestion.AssignedJobQuestionInsertReqDto;
import com.lawencon.jobportaladmin.dto.assignedjobquestion.AssignedJobQuestionResDto;
import com.lawencon.jobportaladmin.model.AssignedJobQuestion;
import com.lawencon.jobportaladmin.model.Question;
import com.lawencon.security.principal.PrincipalService;


@Service
public class AssignedJobQuestionService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private AssignedJobQuestionDao assignedJobQuestionDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private PrincipalService<String> principalService;
	
	public List<AssignedJobQuestionResDto> getAssignedJobQuestionByJob(String jobId){
		final List<AssignedJobQuestion> assignedJobQuestion = assignedJobQuestionDao.getByJob(jobId);
		final List<AssignedJobQuestionResDto> jobQuestionRes = new ArrayList<>();
		for(int i = 0 ; i < assignedJobQuestion.size() ; i++) {
			final AssignedJobQuestionResDto assignedQuestion = new AssignedJobQuestionResDto();
			assignedQuestion.setId(assignedJobQuestion.get(i).getId());
			assignedQuestion.setQuestionId(assignedJobQuestion.get(i).getQuestion().getId());
			assignedQuestion.setQuestionDetail(assignedJobQuestion.get(i).getQuestion().getQuestionDetail());
			jobQuestionRes.add(assignedQuestion);
		}
		
		
		return jobQuestionRes;
	}
	
	public InsertResDto insertAssignedJobQuestion(AssignedJobQuestionInsertReqDto data) {
		final InsertResDto insertRes = new InsertResDto();
		try {
			em().getTransaction().begin();
			final AssignedJobQuestion jobQuestion = new AssignedJobQuestion();
			final Question question = questionDao.getById(Question.class, data.getQuestionId());
			jobQuestion.setQuestion(question);
			jobQuestion.setCreatedBy(principalService.getAuthPrincipal());
			final AssignedJobQuestion jobQuestionId = assignedJobQuestionDao.save(jobQuestion);
			insertRes.setId(jobQuestionId.getId());
			insertRes.setMessage("Assigned Job Question Insert Success");
 			em().getTransaction().commit();
		}catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return insertRes;
	}
	
	
	
}
