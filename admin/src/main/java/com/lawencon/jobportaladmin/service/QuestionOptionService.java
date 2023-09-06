package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.QuestionOptionDao;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.questionoption.QuestionOptionResDto;
import com.lawencon.jobportaladmin.dto.questionoption.QuestionOptionUpdateReqDto;
import com.lawencon.jobportaladmin.model.QuestionOption;

@Service
public class QuestionOptionService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private QuestionOptionDao questionOptionDao;

	public List<QuestionOptionResDto> getByQuestion(String id) {
		final List<QuestionOption> options = questionOptionDao.getByQuestion(id);
		final List<QuestionOptionResDto> optionsDto = new ArrayList<>();

		for (int j = 0; j < options.size(); j++) {
			final QuestionOptionResDto optionDto = new QuestionOptionResDto();
			optionDto.setId(options.get(j).getId());
			optionDto.setOptionLabel(options.get(j).getOptionLabel());
			optionDto.setIsCorrect(options.get(j).getIsCorrect());
			optionsDto.add(optionDto);
		}
		return optionsDto;
	}
	
	public QuestionOptionResDto getById(String id) {
		final QuestionOption options = questionOptionDao.getById(QuestionOption.class,id);
		final QuestionOptionResDto optionDto = new QuestionOptionResDto();
		optionDto.setId(options.getId());
		optionDto.setOptionLabel(options.getOptionLabel());
		optionDto.setIsCorrect(options.getIsCorrect());
		return optionDto;
	}

	public UpdateResDto updateOption(QuestionOptionUpdateReqDto data) {
		final UpdateResDto res = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final QuestionOption option = questionOptionDao.getById(QuestionOption.class, data.getId());
			option.setOptionLabel(data.getOptionLabel());
			option.setIsCorrect(data.getIsCorrect());
			final QuestionOption ver = questionOptionDao.saveAndFlush(option);
			res.setVersion(ver.getVersion());
			res.setMessage("Update Question Option Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Update Question Option Failed");
		}
		return res;
	}

}
