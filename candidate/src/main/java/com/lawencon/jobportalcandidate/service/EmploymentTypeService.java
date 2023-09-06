package com.lawencon.jobportalcandidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportalcandidate.dao.EmploymentTypeDao;
import com.lawencon.jobportalcandidate.dto.employmenttype.EmploymentTypeGetResDto;
import com.lawencon.jobportalcandidate.model.EmploymentType;

@Service
public class EmploymentTypeService {

	@Autowired
	private EmploymentTypeDao employmentTypeDao;

	public List<EmploymentTypeGetResDto> getTypes() {
		final List<EmploymentTypeGetResDto> employmentTypesDto = new ArrayList<>();
		final List<EmploymentType> employmentTypes = employmentTypeDao.getAll(EmploymentType.class);

		for (int i = 0; i < employmentTypes.size(); i++) {
			final EmploymentTypeGetResDto employmentTypeDto = new EmploymentTypeGetResDto();
			employmentTypeDto.setId(employmentTypes.get(i).getId());
			employmentTypeDto.setEmploymentTypeName(employmentTypes.get(i).getEmploymentTypeName());
			employmentTypesDto.add(employmentTypeDto);
		}

		return employmentTypesDto;

	}
}
