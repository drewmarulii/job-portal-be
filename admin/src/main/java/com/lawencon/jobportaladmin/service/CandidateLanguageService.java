package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.CandidateLanguageDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidatelanguage.CandidateLanguageInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatelanguage.CandidateLanguageResDto;
import com.lawencon.jobportaladmin.dto.candidatelanguage.CandidateLanguageUpdateReqDto;
import com.lawencon.jobportaladmin.model.CandidateLanguage;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateLanguageService {
	private EntityManager em() {
		return ConnHandler.getManager();
	}

	
	@Autowired
	private CandidateLanguageDao candidateLanguageDao;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateLanguageResDto> getCandidateLanguageByCandidate(String id) {
		final List<CandidateLanguage> candidateLanguage = candidateLanguageDao.getLanguageByCandidate(id);
		final List<CandidateLanguageResDto> candidateLanguageResList = new ArrayList<>();
		for (int i = 0; i < candidateLanguage.size(); i++) {
			final CandidateLanguageResDto language = new CandidateLanguageResDto();
			language.setLanguageName(candidateLanguage.get(i).getLanguageName());
			language.setId(candidateLanguage.get(i).getId());
			language.setListeningRate(candidateLanguage.get(i).getListeningRate());
			language.setSpeakingRate(candidateLanguage.get(i).getSpeakingRate());
			language.setWritingRate(candidateLanguage.get(i).getWritingRate());

			candidateLanguageResList.add(language);
		}
		return candidateLanguageResList;
	}

	public InsertResDto insertCandidateLanguage(CandidateLanguageInsertReqDto data) {
		InsertResDto insertRes = null;
		try {
			em().getTransaction().begin();
			final CandidateLanguage candidateLanguage = new CandidateLanguage();
			
			if (data.getLanguageCode() != null) {
				candidateLanguage.setLanguageCode(data.getLanguageCode());
			} else {
				candidateLanguage.setLanguageCode(GenerateCode.generateCode());
			}
			
			candidateLanguage.setLanguageName(data.getLanguageName());
			candidateLanguage.setListeningRate(data.getListeningRate());
			candidateLanguage.setSpeakingRate(data.getSpeakingRate());
			candidateLanguage.setWritingRate(data.getWritingRate());
			final CandidateUser candidateUser = candidateUserDao.getByEmail(data.getEmail());
			candidateLanguage.setCandidateUser(candidateUser);
			
			candidateLanguage.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateLanguage languageId = candidateLanguageDao.save(candidateLanguage);
			insertRes = new InsertResDto();
			insertRes.setId(languageId.getId());
			insertRes.setMessage("Insert Candidate Language Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return insertRes;
	}

	public UpdateResDto updateCandidateLanguage(CandidateLanguageUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final CandidateLanguage candidateLanguage = candidateLanguageDao.getById(CandidateLanguage.class,
					data.getId());
			candidateLanguage.setLanguageName(data.getLanguageName());
			candidateLanguage.setListeningRate(data.getLanguageName());
			candidateLanguage.setSpeakingRate(data.getSpeakingRate());
			candidateLanguage.setWritingRate(data.getWritingRate());
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			candidateLanguage.setCandidateUser(candidateUser);
			candidateLanguage.setUpdatedBy(principalService.getAuthPrincipal());
			final CandidateLanguage languageId = candidateLanguageDao.saveAndFlush(candidateLanguage);

			updateResDto.setMessage("Update Candidate Language Success");
			updateResDto.setVersion(languageId.getVersion());
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return updateResDto;
	}

	public DeleteResDto deleteCandidateLanguage(String id) {
		final DeleteResDto deleteRes = new DeleteResDto();
		try {
			em().getTransaction().begin();			
			candidateLanguageDao.deleteById(CandidateLanguage.class, id);
			deleteRes.setMessage("Delete Candidate Language Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return deleteRes;
	}
	
	public DeleteResDto deleteCandidateLanguageFromCandidate(String code) {
		final DeleteResDto deleteRes = new DeleteResDto();
		try {
			em().getTransaction().begin();			
			final CandidateLanguage language = candidateLanguageDao.getByCode(code);
			candidateLanguageDao.deleteById(CandidateLanguage.class, language.getId());
			
			deleteRes.setMessage("Delete Candidate Language Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return deleteRes;
	}	

}
