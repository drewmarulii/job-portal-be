package com.lawencon.jobportaladmin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.CandidateDocuments;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.FileType;

@Repository
public class CandidateDocumentsDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<CandidateDocuments> getCandidateDocumentsByCandidate (String id){
		final StringBuilder sql = new StringBuilder();
				sql.append("SELECT   ")
				.append("	tcd.id as documentId ,  ")
				.append("	tcd.doc_name,  ")
				.append( "	tcu.user_email,  ")
				.append( " 	tf.id AS fileId, ")
				.append( "	tft.type_code,  ")
				.append( "	tft.type_name  ")
				.append( "FROM   ")
				.append( "	t_candidate_documents tcd   ")
				.append("INNER JOIN   ")
				.append( "	t_candidate_user tcu   ")
				.append("ON  ")
				.append( "	tcd.user_id  = tcu.id   ")
				.append( "INNER JOIN   ")
				.append( "	t_file_type tft   ")
				.append( "ON   ")
				.append( "	tcd.file_type_id = tft.id   ")
				.append( "INNER JOIN t_file tf ON tf.id = tcd.file_id ")
				.append( "WHERE   ")
				.append( "	tcd.user_id  = :candidate");

		final List<?>documentObjs = em().createNativeQuery(sql.toString())
				.setParameter("candidate", id)
				.getResultList();
		final List<CandidateDocuments> candidateDocumentsList = new ArrayList<>();
		if(documentObjs.size() > 0) {
			for(Object documentObj : documentObjs ) {
				final Object[] documentArr = (Object[]) documentObj;
				final CandidateDocuments candidateDocument = new CandidateDocuments();
				candidateDocument.setId(documentArr[0].toString());
				candidateDocument.setDocName(documentArr[1].toString());
				
			
				final CandidateUser candidateUser = new CandidateUser();
				candidateUser.setUserEmail(documentArr[2].toString());
				candidateDocument.setCandidateUser(candidateUser);
				
				final File file = new File();
				file.setId(documentArr[3].toString());
				candidateDocument.setFile(file);
				
				
				
				final FileType fileType = new FileType();

				fileType.setTypeCode(documentArr[4].toString());
				fileType.setTypeName(documentArr[5].toString());
				
				candidateDocument.setFileType(fileType);
				candidateDocumentsList.add(candidateDocument);
				
			}
		}
		
		return candidateDocumentsList;
	
	}

	public List<CandidateDocuments> getByCandidateEmail(String email) {
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT cd ");
		sql.append("FROM ");
		sql.append("CandidateDocuments cd ");
		sql.append("INNER JOIN CandidateUser cu ");
		sql.append("WHERE cu.userEmail = :email");

		final List<CandidateDocuments> documentList = em().createNamedQuery(sql.toString(), CandidateDocuments.class)
				.setParameter("email", email).getResultList();
		return documentList;
	}
	
	public CandidateDocuments getByCode(String code) {
		final StringBuilder sqlb = new StringBuilder();
		sqlb.append("SELECT ")
			.append(" cd ")
			.append("FROM ")
			.append(" CandidateDocuments cd ")
			.append("WHERE ")
			.append(" cd.docCode = :code");
		
		final CandidateDocuments document = this.em().createQuery(sqlb.toString(), CandidateDocuments.class)
					.setParameter("code", code).getSingleResult();
		
		return document;
	}
}
