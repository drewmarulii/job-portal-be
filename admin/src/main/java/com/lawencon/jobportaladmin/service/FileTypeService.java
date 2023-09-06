package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportaladmin.dao.FileTypeDao;
import com.lawencon.jobportaladmin.dto.filetype.FileTypesResDto;
import com.lawencon.jobportaladmin.model.FileType;

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

	public FileTypesResDto getByCode(String code) {
		final FileTypesResDto fileTypeDto = new FileTypesResDto();
		final FileType fileType = fileTypeDao.getByCode(code);
		
		fileTypeDto.setId(fileType.getId());
		fileTypeDto.setTypeCode(fileType.getTypeCode());
		fileTypeDto.setTypeName(fileType.getTypeName());
		
		return fileTypeDto;
	}
}
