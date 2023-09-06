package com.lawencon.jobportalcandidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportalcandidate.dao.ReligionDao;
import com.lawencon.jobportalcandidate.dto.religion.ReligionResDto;
import com.lawencon.jobportalcandidate.model.Religion;

@Service
public class ReligionService {

	@Autowired
	private ReligionDao religionDao;
	
	public List<ReligionResDto> getReligions() {
		final List<ReligionResDto> religionsDto = new ArrayList<>();
		final List<Religion> religions = religionDao.getAll(Religion.class);
		
		for (int i=0; i<religions.size(); i++) {
			final ReligionResDto religion = new ReligionResDto();
			religion.setId(religions.get(i).getId());
			religion.setReligionCode(religions.get(i).getReligionCode());
			religion.setReligionName(religions.get(i).getReligionName());
			
			religionsDto.add(religion);
		}
		
		return religionsDto;
	}
	
}
