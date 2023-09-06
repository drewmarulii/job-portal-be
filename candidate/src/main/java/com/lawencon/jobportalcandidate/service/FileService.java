package com.lawencon.jobportalcandidate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportalcandidate.dao.FileDao;
import com.lawencon.jobportalcandidate.model.File;


@Service
public class FileService {
	@Autowired
	private FileDao fileDao;


	public File getById(String id)  {
		final File files = fileDao.getById(File.class,id);
		return files;
		
	}


	public File insert(File file)  {
		final File files = fileDao.save(file);
		return files;
	}


	public Boolean deleteById(Long id)  {
		final Boolean files = fileDao.deleteById(File.class,id);
		return files;
	}

}
