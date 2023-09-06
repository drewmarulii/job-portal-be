package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportaladmin.dao.ReligionDao;
import com.lawencon.jobportaladmin.dto.religion.ReligionsResDto;
import com.lawencon.jobportaladmin.model.Religion;

@Service
public class ReligionService {

	@Autowired
	private ReligionDao religionDao;

	public List<ReligionsResDto> getReligions() {
		final List<ReligionsResDto> religionsDto = new ArrayList<>();
		final List<Religion> religions = religionDao.getAll(Religion.class);

		for (int i = 0; i < religions.size(); i++) {
			final ReligionsResDto religionDto = new ReligionsResDto();
			religionDto.setId(religions.get(i).getId());
			religionDto.setReligionName(religions.get(i).getReligionName());
			religionDto.setReligionCode(religions.get(i).getReligionCode());
			religionsDto.add(religionDto);
		}

		return religionsDto;

	}

}
