package com.lawencon.jobportalcandidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportalcandidate.dao.FileTypeDao;
import com.lawencon.jobportalcandidate.dto.filetype.FileTypesResDto;
import com.lawencon.jobportalcandidate.model.FileType;

@Service
public class FileTypeService {

	@Autowired
	private FileTypeDao fileTypeDao;

	public List<FileTypesResDto> getFileTypes() {
		final List<FileTypesResDto> fileTypesDto = new ArrayList<>();
		final List<FileType> fileTypes = fileTypeDao.getAll(FileType.class);

		for (int i = 0; i < fileTypes.size(); i++) {
			final FileTypesResDto fileTypeDto = new FileTypesResDto();
			fileTypeDto.setId(fileTypes.get(i).getId());
			fileTypeDto.setTypeName(fileTypes.get(i).getTypeName());
			fileTypeDto.setTypeCode(fileTypes.get(i).getTypeCode());
			fileTypesDto.add(fileTypeDto);
		}

		return fileTypesDto;

	}

}
