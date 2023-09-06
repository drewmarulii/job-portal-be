package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportaladmin.dao.AssignedJobQuestionDao;
import com.lawencon.jobportaladmin.dao.QuestionDao;
import com.lawencon.jobportaladmin.dao.QuestionOptionDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.question.QuestionInsertReqDto;
import com.lawencon.jobportaladmin.dto.question.QuestionResDto;
import com.lawencon.jobportaladmin.dto.question.QuestionUpdateReqDto;
import com.lawencon.jobportaladmin.dto.question.QuestionsInsertReqDto;
import com.lawencon.jobportaladmin.dto.questionoption.QuestionOptionInsertReqDto;
import com.lawencon.jobportaladmin.dto.questionoption.QuestionOptionResDto;
import com.lawencon.jobportaladmin.model.AssignedJobQuestion;
import com.lawencon.jobportaladmin.model.Question;
import com.lawencon.jobportaladmin.model.QuestionOption;
import com.lawencon.jobportaladmin.util.GenerateCode;

@Service
public class QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private QuestionOptionDao questionOptionDao;

	@Autowired
	private AssignedJobQuestionDao assignedJobQuestionDao;

	@Autowired
	private RestTemplate restTemplate;

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<QuestionResDto> getAll() {
		final List<QuestionResDto> questionsDto = new ArrayList<>();
		final List<Question> questions = questionDao.getAll(Question.class);

		for (int i = 0; i < questions.size(); i++) {
			final QuestionResDto questionDto = new QuestionResDto();
			questionDto.setId(questions.get(i).getId());
			questionDto.setQuestionDetail(questions.get(i).getQuestionDetail());
			questionsDto.add(questionDto);
		}

		return questionsDto;

	}

	public InsertResDto insertQuestion(QuestionsInsertReqDto newQuestions) {
		final InsertResDto insertRes = new InsertResDto();

		try {
			em().getTransaction().begin();

			for (int i = 0; i < newQuestions.getNewQuestions().size(); i++) {
				final QuestionInsertReqDto question = newQuestions.getNewQuestions().get(i);
				if("".equals(newQuestions.getNewQuestions().get(i).getQuestionDetail())) {
					em().getTransaction().rollback();
					throw new RuntimeException("Question Detail is null");
				}
				Question newQuestion = new Question();
				newQuestion.setQuestionDetail(question.getQuestionDetail());
				newQuestion.setQuestionCode(GenerateCode.generateCode());
				newQuestion = questionDao.save(newQuestion);
				newQuestions.getNewQuestions().get(i).setQuestionCode(newQuestion.getQuestionCode());

				
				if(question.getOptions().size()>=2) {
					Boolean trueChecker =false;
					
					for (int j = 0; j < question.getOptions().size(); j++) {
						final QuestionOptionInsertReqDto option = question.getOptions().get(j);

						if("".equals(question.getOptions().get(j).getOptionLabel())) {
							em().getTransaction().rollback();
							throw new RuntimeException("Question Option -"+(j+1)+" is null");
						}
						
						if(trueChecker && option.getIsCorrect()) {
							em().getTransaction().rollback();
							throw new RuntimeException("Option True Should be only One");
						}
						
						if(option.getIsCorrect()) {
							trueChecker = true;
						}
						
						QuestionOption newOption = new QuestionOption();
						newOption.setOptionLabel(option.getOptionLabel());
						
						newOption.setIsCorrect(option.getIsCorrect());
						newOption.setQuestion(newQuestion);
						newOption = questionOptionDao.save(newOption);
					}
					
					if(!trueChecker) {
						em().getTransaction().rollback();
						throw new RuntimeException("Option should have one correct answer");
					}
					
				}
				else {
					em().getTransaction().rollback();
					throw new RuntimeException("Question Option Minimum is 2");
				}
				
			}

			final String questionInsertCandidateAPI = "http://localhost:8081/questions";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<QuestionsInsertReqDto> questionsInsert = RequestEntity.post(questionInsertCandidateAPI)
					.headers(headers).body(newQuestions);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(questionsInsert,
					InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {

				insertRes.setMessage("Insert Question Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");

			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return insertRes;
	}

	public UpdateResDto updateQuestion(QuestionUpdateReqDto data) {
		final UpdateResDto res = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final Question question = questionDao.getById(Question.class, data.getId());
			question.setQuestionDetail(data.getQuestionDetail());
			final Question save = questionDao.saveAndFlush(question);
			if (data.getOptions() != null) {
				final List<QuestionOption> option = questionOptionDao.getByQuestion(data.getId());
				for (int i = 0; i < data.getOptions().size(); i++) {
					System.out.println("Ini versi == >     "+option.get(i).getVersion());
					option.get(i).setOptionLabel(data.getOptions().get(i).getOptionLabel());
					option.get(i).setIsCorrect(data.getOptions().get(i).getIsCorrect());
					option.get(i).setQuestion(question);
					questionOptionDao.saveAndFlush(option.get(i));
				}
			}
			final String questionUpdateCandidateAPI = "http://localhost:8081/questions";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<QuestionUpdateReqDto> questionsInsert = RequestEntity.patch(questionUpdateCandidateAPI)
					.headers(headers).body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(questionsInsert,
					InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {

				res.setMessage("Update Success");
				res.setVersion(save.getVersion());
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");

			}
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Update Failed");

		}
		return res;
	}

	public QuestionResDto getById(String id) {
		final Question question = questionDao.getById(Question.class, id);
		final QuestionResDto questionDto = new QuestionResDto();

		final List<QuestionOption> options = questionOptionDao.getByQuestion(question.getId());
		final List<QuestionOptionResDto> optionsDto = new ArrayList<>();

		for (int j = 0; j < options.size(); j++) {
			final QuestionOptionResDto optionDto = new QuestionOptionResDto();
			optionDto.setId(options.get(j).getId());
			optionDto.setOptionLabel(options.get(j).getOptionLabel());
			optionsDto.add(optionDto);
		}

		questionDto.setId(question.getId());
		questionDto.setQuestionCode(question.getQuestionCode());
		questionDto.setQuestionDetail(question.getQuestionDetail());
		questionDto.setOptions(optionsDto);
		
		return questionDto;
	}

	public List<QuestionResDto> getByJob(String id) {
		final List<QuestionResDto> questions = new ArrayList<>();

		final List<AssignedJobQuestion> assignedJobQuestions = assignedJobQuestionDao.getByJob(id);

		for (int i = 0; i < assignedJobQuestions.size(); i++) {
			final Question question = questionDao.getById(Question.class,
					assignedJobQuestions.get(i).getQuestion().getId());
			final QuestionResDto questionDto = new QuestionResDto();

			final List<QuestionOption> options = questionOptionDao.getByQuestion(question.getId());
			final List<QuestionOptionResDto> optionsDto = new ArrayList<>();

			for (int j = 0; j < options.size(); j++) {
				final QuestionOptionResDto optionDto = new QuestionOptionResDto();
				optionDto.setId(options.get(j).getId());
				optionDto.setOptionLabel(options.get(j).getOptionLabel());
				optionsDto.add(optionDto);
			}

			questionDto.setId(question.getId());
			questionDto.setQuestionDetail(question.getQuestionDetail());
			questionDto.setOptions(optionsDto);

			questions.add(questionDto);
		}

		return questions;

	}

}
