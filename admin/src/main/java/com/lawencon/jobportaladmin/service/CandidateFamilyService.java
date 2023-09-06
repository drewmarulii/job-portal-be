package com.lawencon.jobportaladmin.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.CandidateFamilyDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidatefamily.CandidateFamilyInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidatefamily.CandidateFamilyResDto;
import com.lawencon.jobportaladmin.dto.candidatefamily.CandidateFamilyUpdateReqDto;
import com.lawencon.jobportaladmin.model.CandidateFamily;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.util.DateUtil;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateFamilyService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	

	@Autowired
	private CandidateFamilyDao candidateFamilyDao;
	
	@Autowired
	private CandidateUserDao candidateUserDao;
	@Autowired
	private PrincipalService<String> principalService;
	
	public List<CandidateFamilyResDto> getFamilyByCandidate(String id) {
		final List<CandidateFamilyResDto> familiesDto = new ArrayList<>();
		final List<CandidateFamily> families = candidateFamilyDao.getFamilyByCandidate(id);
		
		for (int i=0; i<families.size(); i++) {
			final CandidateFamilyResDto family = new CandidateFamilyResDto();
			family.setId(families.get(i).getId());
			family.setFullname(families.get(i).getFullname());
			family.setRelationship(families.get(i).getRelationship());
			family.setDegreeName(families.get(i).getDegreeName());
			family.setOccupation(families.get(i).getOccupation());
			family.setBirthDate(DateUtil.localDateToString(families.get(i).getBirthDate()));
			family.setBirthPlace(families.get(i).getBirthPlace());
			
			familiesDto.add(family);
		}
		
		return familiesDto;
	}
	
	public InsertResDto insertFamily(CandidateFamilyInsertReqDto data) {
		final CandidateFamily family = new CandidateFamily();

		InsertResDto result = null;
		
		try {
			em().getTransaction().begin();
			
			if (data.getFamilyCode() != null) {
				family.setFamilyCode(data.getFamilyCode());
			} else {
				family.setFamilyCode(GenerateCode.generateCode());
			}
			
			family.setFullname(data.getFullname());
			family.setRelationship(data.getRelationship());
			family.setDegreeName(data.getDegreeName());
			family.setOccupation(data.getOccupation());
			family.setBirthDate(LocalDate.parse(data.getBirthDate().toString()));
			family.setBirthPlace(data.getBirthPlace());
			family.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateUser candidate = candidateUserDao.getByEmail(data.getEmail());
			family.setCandidateUser(candidate);
			
			final CandidateFamily familyId = candidateFamilyDao.save(family);
			
			result = new InsertResDto();
			result.setId(familyId.getId());
			result.setMessage("Family record added!");
			
		
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}
	
	public UpdateResDto updateFamily(CandidateFamilyUpdateReqDto data) {
		final CandidateFamily family = candidateFamilyDao.getById(CandidateFamily.class, data.getId());

		UpdateResDto result = null;
		
		try {
			em().getTransaction().begin();
			family.setId(data.getId());
			family.setFullname(data.getFullname());
			family.setRelationship(data.getRelationship());
			family.setDegreeName(data.getDegreeName());
			family.setOccupation(data.getOccupation());
			family.setBirthDate(LocalDate.parse(data.getBirthDate().toString()));
			family.setBirthPlace(data.getBirthPlace());
			family.setUpdatedBy(principalService.getAuthPrincipal());
			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			family.setCandidateUser(candidate);
			candidateFamilyDao.saveAndFlush(family);
			
			result = new UpdateResDto();
			result.setVersion(family.getVersion());
			result.setMessage("Family record updated!");
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}
		
		return result;
	}
	
	public DeleteResDto deleteFamily(String id) {
		final DeleteResDto response = new DeleteResDto();
	
		try {
			em().getTransaction().begin();			
			candidateFamilyDao.deleteById(CandidateFamily.class, id);
			response.setMessage("Family has been removed");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return response;
	}
	
	public DeleteResDto deleteFamilyFromCandidate(String code) {
		final DeleteResDto response = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			final CandidateFamily family = candidateFamilyDao.getByCode(code);
			candidateFamilyDao.deleteById(CandidateFamily.class, family.getId());
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return response;
	}
}
