package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportaladmin.dao.MaritalStatusDao;
import com.lawencon.jobportaladmin.dto.maritalstatus.MaritalResDto;
import com.lawencon.jobportaladmin.model.MaritalStatus;

@Service
public class MaritalStatusService {

	@Autowired
	private MaritalStatusDao maritalStatusDao;
	
	public List<MaritalResDto> getMaritalStatus() {
		final List<MaritalResDto> maritalDto = new ArrayList<>();
		final List<MaritalStatus> maritals = maritalStatusDao.getAll(MaritalStatus.class);
		
		for (int i=0; i<maritals.size(); i++) {
			final MaritalResDto marital = new MaritalResDto();
			marital.setId(maritals.get(i).getId());
			marital.setMaritalCode(maritals.get(i).getMaritalCode());
			marital.setMaritalName(maritals.get(i).getMaritalName());
			
			maritalDto.add(marital);
		}
		
		return maritalDto;
	}
}
