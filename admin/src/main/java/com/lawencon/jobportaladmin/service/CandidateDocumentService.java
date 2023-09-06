package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.CandidateDocumentsDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dao.FileDao;
import com.lawencon.jobportaladmin.dao.FileTypeDao;
import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.candidatedocument.CandidateDocumentInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatedocument.CandidateDocumentResDto;
import com.lawencon.jobportaladmin.model.CandidateDocuments;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.FileType;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateDocumentService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private CandidateDocumentsDao candidateDocumentDao;
	
	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private FileTypeDao fileTypeDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateDocumentResDto> getCandidateDocumentByCandidate(String id) {
		final List<CandidateDocuments> candidateDocuments = candidateDocumentDao.getCandidateDocumentsByCandidate(id);
		final List<CandidateDocumentResDto> candidateDocumentResList = new ArrayList<>();
		for(int i = 0 ; i < candidateDocuments.size() ; i++) {
			final CandidateDocumentResDto document = new CandidateDocumentResDto();
			document.setDocName(candidateDocuments.get(i).getDocName());
			document.setCandidateId(id);
			document.setId(candidateDocuments.get(i).getId());
			final File file = fileDao.getById(File.class, candidateDocuments.get(i).getFile().getId());
			document.setFileId(file.getId());
			document.setFileExtension(file.getFileExtension());
			document.setFileTypeName(candidateDocuments.get(i).getFileType().getTypeName());
			
			
			candidateDocumentResList.add(document);
		}
		return candidateDocumentResList;
	}

	public InsertResDto insertCandidateDocument(CandidateDocumentInsertReqDto data) {

		final InsertResDto insertRes = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateDocuments candidateDocument = new CandidateDocuments();
			candidateDocument.setDocName(data.getDocName());
			
			if (data.getDocCode() != null) {
				candidateDocument.setDocCode(data.getDocCode());
			} else {
				candidateDocument.setDocCode(GenerateCode.generateCode());
			}
			
			final CandidateUser candidateUser = candidateUserDao.getByEmail(data.getEmail());
			candidateDocument.setCandidateUser(candidateUser);

			final FileType fileType = fileTypeDao.getByCode(data.getFileTypeCode());
			candidateDocument.setFileType(fileType);

			final File file = new File();
			file.setFileName(data.getFileName());
			file.setFileExtension(data.getFileExtension());
			file.setCreatedBy(principalService.getAuthPrincipal());
			fileDao.save(file);

			candidateDocument.setFile(file);
			candidateDocument.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateDocuments candidateDocumentId = candidateDocumentDao.save(candidateDocument);
			insertRes.setId(candidateDocumentId.getId());
			insertRes.setMessage("Document Insert Success");

			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertRes;

	}

//	public UpdateResDto updateCandidateDocument(CandidateDocumentUpdateReqDto data) {
//		final UpdateResDto updateResDto = new UpdateResDto();
//		try {
//			em().getTransaction().begin();
//			final CandidateDocuments candidateDocument = candidateDocumentDao.getById(CandidateDocuments.class,
//					data.getId());
//			candidateDocument.setDocName(data.getDocName());
//			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, data.getCandidateId());
//			candidateDocument.setCandidateUser(candidateUser);
//
//			final FileType fileType = fileTypeDao.getById(FileType.class, data.getFileTypeId());
//			candidateDocument.setFileType(fileType);
//
//			final File file = new File();
//			file.setFileName(data.getFileName());
//			file.setFileExtension(data.getFileExtension());
//			file.setCreatedBy(principalService.getAuthPrincipal());
//			fileDao.save(file);
//
//			candidateDocument.setFile(file);
//			candidateDocument.setCreatedBy(principalService.getAuthPrincipal());
//			final CandidateDocuments candidateDocumentId = candidateDocumentDao.saveAndFlush(candidateDocument);
//			fileDao.deleteById(File.class, candidateDocument.getFile().getId());
//			updateResDto.setVersion(candidateDocumentId.getVersion());
//			updateResDto.setMessage("Document Update Success");
//			em().getTransaction().commit();
//		} catch (Exception e) {
//			em().getTransaction().rollback();
//			e.printStackTrace();
//		}
//		return updateResDto;
//	}
//
	public DeleteResDto deleteCandidateDocument(String id) {
		final DeleteResDto deleteRes = new DeleteResDto();
		try {
			em().getTransaction().begin();
			final CandidateDocuments document = candidateDocumentDao.getById(CandidateDocuments.class, id);
			final String fileId = document.getFile().getId();
			candidateDocumentDao.deleteById(CandidateDocuments.class, id);
			fileDao.deleteById(File.class, fileId);
			deleteRes.setMessage("Delete Candidate Document Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return deleteRes;
	}
	
	public DeleteResDto deleteCandidateDocumentFromCandidate(String code) {
		final DeleteResDto deleteRes = new DeleteResDto();
		try {
			em().getTransaction().begin();
			final CandidateDocuments document = candidateDocumentDao.getByCode(code);
			final String fileId = document.getFile().getId();
			candidateDocumentDao.deleteById(CandidateDocuments.class, document.getId());
			fileDao.deleteById(File.class, fileId);
			deleteRes.setMessage("Delete Candidate Document Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return deleteRes;
	}
}
