package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.CandidateReferencesDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidatereferences.CandidateReferencesInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatereferences.CandidateReferencesResDto;
import com.lawencon.jobportaladmin.dto.candidatereferences.CandidateReferencesUpdateReqDto;
import com.lawencon.jobportaladmin.model.CandidateReferences;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateReferencesService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	
	@Autowired
	private CandidateReferencesDao candidateRefDao;
	
	@Autowired
	private CandidateUserDao candidateUserDao;

	
	@Autowired
	private PrincipalService<String> principalService;
	
	public List<CandidateReferencesResDto> getReferencesByCandidate(String id) {
		final List<CandidateReferencesResDto> referencesDto = new ArrayList<>();
		final List<CandidateReferences> references = candidateRefDao.getByCandidate(id);
		
		for (int i=0; i<references.size(); i++) {
			final CandidateReferencesResDto reference = new CandidateReferencesResDto();
			reference.setId(references.get(i).getId());
			reference.setFullname(references.get(i).getFullName());
			reference.setRelationship(references.get(i).getRelationship());
			reference.setOccupation(references.get(i).getOccupation());
			reference.setPhoneNumber(references.get(i).getPhoneNumber());
			reference.setEmail(references.get(i).getEmail());
			reference.setCompany(references.get(i).getCompany());
			reference.setDescription(references.get(i).getDescription());
			
			referencesDto.add(reference);
		}
		
		return referencesDto;
	}
	
	public InsertResDto insertReference(CandidateReferencesInsertReqDto data) {
		final CandidateReferences reference = new CandidateReferences();

		InsertResDto result = null;
		
		try {
			em().getTransaction().begin();
			
			if (data.getReferenceCode() != null) {
				reference.setReferenceCode(data.getReferenceCode());
			} else {
				reference.setReferenceCode(GenerateCode.generateCode());
			}
			
			reference.setFullName(data.getFullname());
			reference.setRelationship(data.getRelationship());
			reference.setOccupation(data.getOccupation());
			reference.setPhoneNumber(data.getPhoneNumber());
			reference.setEmail(data.getEmail());
			reference.setCompany(data.getCompany());
			reference.setDescription(data.getDescription());
			reference.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateUser candidate = candidateUserDao.getByEmail(data.getCandidateEmail());
			reference.setCandidateUser(candidate);
			final CandidateReferences  refId = candidateRefDao.save(reference);
			result = new InsertResDto();
			result.setId(refId.getId());
			result.setMessage("Reference record added!");
			
			em().getTransaction().commit();
		} catch (Exception e) {
			
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return result;
	}
	
	public UpdateResDto updateReferences(CandidateReferencesUpdateReqDto data) {
		final CandidateReferences reference = candidateRefDao.getById(CandidateReferences.class, data.getId());

		UpdateResDto result = null;
		
		try {
			em().getTransaction().begin();
			reference.setFullName(data.getFullname());
			reference.setRelationship(data.getRelationship());
			reference.setOccupation(data.getOccupation());
			reference.setPhoneNumber(data.getPhoneNumber());
			reference.setEmail(data.getEmail());
			reference.setCompany(data.getCompany());
			reference.setDescription(data.getDescription());
			reference.setUpdatedBy(principalService.getAuthPrincipal());
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			reference.setCandidateUser(candidate);
			
			result = new UpdateResDto();
			result.setVersion(reference.getVersion());
			result.setMessage("Reference record updated!");
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}
	
	public DeleteResDto deleteReference(String id) {
		final DeleteResDto response = new DeleteResDto();

		try {
			em().getTransaction().begin();
			candidateRefDao.deleteById(CandidateReferences.class, id);
			response.setMessage("Reference has been removed");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return response;
	}
	
	public DeleteResDto deleteResDtoFromCandidate(String code) {
		final DeleteResDto response = new DeleteResDto();

		try {
			em().getTransaction().begin();
			final CandidateReferences reference = candidateRefDao.getByCode(code);
			candidateRefDao.deleteById(CandidateReferences.class, reference.getId());

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return response;
	}
}
