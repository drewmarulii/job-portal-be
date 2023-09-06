package com.lawencon.jobportalcandidate.service;

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
import com.lawencon.jobportalcandidate.dao.ApplicantDao;
import com.lawencon.jobportalcandidate.dao.CandidateUserDao;
import com.lawencon.jobportalcandidate.dao.QuestionAnswerDao;
import com.lawencon.jobportalcandidate.dao.QuestionDao;
import com.lawencon.jobportalcandidate.dao.QuestionOptionDao;
import com.lawencon.jobportalcandidate.dto.InsertResDto;
import com.lawencon.jobportalcandidate.dto.UpdateResDto;
import com.lawencon.jobportalcandidate.dto.questionanswer.QuestionAnswerResDto;
import com.lawencon.jobportalcandidate.dto.questionanswer.QuestionAnswersInsertReqDto;
import com.lawencon.jobportalcandidate.dto.review.ReviewUpdateScoreReqDto;
import com.lawencon.jobportalcandidate.model.Applicant;
import com.lawencon.jobportalcandidate.model.CandidateUser;
import com.lawencon.jobportalcandidate.model.Question;
import com.lawencon.jobportalcandidate.model.QuestionAnswer;
import com.lawencon.jobportalcandidate.model.QuestionOption;
import com.lawencon.security.principal.PrincipalService;

@Service
public class QuestionAnswerService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private QuestionAnswerDao questionAnswerDao;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private PrincipalService<String> principalService;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private QuestionOptionDao questionOptionDao;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ApplicantDao applicantDao;

	public InsertResDto insertAnswer(QuestionAnswersInsertReqDto data) {
		final InsertResDto resDto = new InsertResDto();

		try {
			em().getTransaction().begin();
			int scores = 0;
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class,
					principalService.getAuthPrincipal());
			
			final Applicant applicant = applicantDao.getByCode(data.getApplicantCode());
			
			data.setApplicantCode(applicant.getApplicantCode());
			
			
			for (int i = 0; i < data.getAnswers().size(); i++) {
				QuestionAnswer answer = new QuestionAnswer();
				final QuestionOption questionOption = questionOptionDao.getById(QuestionOption.class,
						data.getAnswers().get(i).getOptionId());
				final Question question = questionDao.getById(Question.class, questionOption.getQuestion().getId());
				answer.setApplicant(applicant);
				answer.setCandidateUser(candidateUser);
				answer.setQuestion(question);
				answer.setQuestionOption(questionOption);
				answer = questionAnswerDao.save(answer);

				scores += questionOption.getIsCorrect() ? 100 : 0;
			}

			scores = scores / data.getAnswers().size();
			data.setScores(scores);

			final String updateReviewAdminAPI = "http://localhost:8080/reviews";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final ReviewUpdateScoreReqDto reviewUpdateDto = new ReviewUpdateScoreReqDto();
			reviewUpdateDto.setApplicantCode(applicant.getApplicantCode());
			reviewUpdateDto.setScores(scores);
			System.out.println(reviewUpdateDto.getApplicantCode()+ "========");
			final RequestEntity<ReviewUpdateScoreReqDto> updateReview = RequestEntity.patch(updateReviewAdminAPI)
					.headers(headers).body(reviewUpdateDto);

			final ResponseEntity<UpdateResDto> responseAdmin = restTemplate.exchange(updateReview, UpdateResDto.class);

			if (responseAdmin.getStatusCode().equals(HttpStatus.OK)) {
				resDto.setMessage("Insert Answers Success");
				em().getTransaction().commit();
				
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return resDto;
	}
	
	public List<QuestionAnswerResDto> getByApplicant(String code) {
		final Applicant appCode = applicantDao.getByCode(code);
		final List<QuestionAnswerResDto> resDto = new ArrayList<>();
		final List<QuestionAnswer> answer = questionAnswerDao.getAnswerByApplicant(appCode.getId());
		for(int i = 0; i < answer.size() ; i++) {
			final QuestionAnswerResDto res = new QuestionAnswerResDto();
			res.setId(answer.get(i).getId());
			resDto.add(res);
		}				
		return resDto;
	}

}
