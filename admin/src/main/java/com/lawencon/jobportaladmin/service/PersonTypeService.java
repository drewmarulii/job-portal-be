package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportaladmin.dao.PersonTypeDao;
import com.lawencon.jobportaladmin.dto.persontype.PersonTypeGetResDto;
import com.lawencon.jobportaladmin.model.PersonType;

@Service
public class PersonTypeService {

	@Autowired
	private PersonTypeDao personTypeDao;

	public List<PersonTypeGetResDto> getPersonTypes() {
		final List<PersonTypeGetResDto> personTypesDto = new ArrayList<>();
		final List<PersonType> personTypes = personTypeDao.getAll(PersonType.class);

		for (int i = 0; i < personTypes.size(); i++) {
			final PersonTypeGetResDto personTypeDto = new PersonTypeGetResDto();
			personTypeDto.setId(personTypes.get(i).getId());
			personTypeDto.setTypeCode(personTypes.get(i).getTypeCode());
			personTypeDto.setTypeName(personTypes.get(i).getTypeName());
			personTypesDto.add(personTypeDto);
		}

		return personTypesDto;
	}
	
	public PersonTypeGetResDto getById(String id) {
		final PersonTypeGetResDto personTypeDto = new PersonTypeGetResDto();
		final PersonType personType = personTypeDao.getById(PersonType.class, id);
		
		personTypeDto.setId(personType.getId());
		personTypeDto.setTypeCode(personType.getTypeCode());
		personTypeDto.setTypeName(personType.getTypeName());
		
		return personTypeDto;
	}

}
