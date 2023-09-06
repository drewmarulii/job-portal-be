package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.CandidateProjectExpDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidateprojectexp.CandidateProjectExpInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateprojectexp.CandidateProjectExpResDto;
import com.lawencon.jobportaladmin.dto.candidateprojectexp.CandidateProjectExpUpdateReqDto;
import com.lawencon.jobportaladmin.model.CandidateProjectExp;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.util.DateUtil;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateProjectExpService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private CandidateProjectExpDao projectExpDao;

	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateProjectExpResDto> getProjectExpByCandidate(String id) {
		final List<CandidateProjectExpResDto> projectExpResList = new ArrayList<>();
		final List<CandidateProjectExp> projectExp = projectExpDao.getByCandidate(id);
		for (int i = 0; i < projectExp.size(); i++) {
			final CandidateProjectExpResDto projectExpRes = new CandidateProjectExpResDto();
			projectExpRes.setDescription(projectExp.get(i).getDescription());
			projectExpRes.setEndDate(DateUtil.localDateToString(projectExp.get(i).getEndDate()));
			projectExpRes.setStartDate(DateUtil.localDateToString(projectExp.get(i).getStartDate()));
			projectExpRes.setProjectName(projectExp.get(i).getProjectName());
			projectExpRes.setProjectUrl(projectExp.get(i).getProjectUrl());
			projectExpRes.setId(projectExp.get(i).getId());

			projectExpResList.add(projectExpRes);
		}

		return projectExpResList;
	}

	public InsertResDto insertCandidateProjectExp(CandidateProjectExpInsertReqDto data) {
		final InsertResDto insertRes = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateProjectExp projectExp = new CandidateProjectExp();
			
			if (data.getProjectCode() != null) {
				projectExp.setProjectCode(data.getProjectCode());
			} else {
				projectExp.setProjectCode(GenerateCode.generateCode());
			}
			
			projectExp.setProjectName(data.getProjectName());
			projectExp.setDescription(data.getDescription());
			projectExp.setProjectUrl(data.getProjectUrl());
			projectExp.setStartDate(DateUtil.parseStringToLocalDate(data.getStartDate()));
			projectExp.setEndDate(DateUtil.parseStringToLocalDate(data.getEndDate()));

			final CandidateUser candidateUser = candidateUserDao.getByEmail(data.getEmail());
			projectExp.setCandidateUser(candidateUser);
			projectExp.setCreatedBy(principalService.getAuthPrincipal());

			projectExpDao.save(projectExp);
			
			insertRes.setId(projectExp.getId());
			insertRes.setMessage("Candidate Project Exp Insert Success");
			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertRes;
	}

	public UpdateResDto updateCandidateProjectExp(CandidateProjectExpUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final CandidateProjectExp projectExp = projectExpDao.getById(CandidateProjectExp.class, data.getId());
			projectExp.setProjectName(data.getProjectName());
			projectExp.setDescription(data.getDescription());
			projectExp.setProjectUrl(data.getProjectUrl());
			projectExp.setStartDate(DateUtil.parseStringToLocalDate(data.getStartDate()));
			projectExp.setEndDate(DateUtil.parseStringToLocalDate(data.getEndDate()));
			
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			projectExp.setCandidateUser(candidateUser);
			projectExp.setUpdatedBy(principalService.getAuthPrincipal());
			
			final CandidateProjectExp projectExpId = projectExpDao.saveAndFlush(projectExp);
			updateResDto.setVersion(projectExpId.getVersion());
			updateResDto.setMessage("Candidate Project Exp Update Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return updateResDto;
	}

	public DeleteResDto deleteCandidateProjectExp(String id) {
		final DeleteResDto deleteRes = new DeleteResDto();

		try {
			em().getTransaction().begin();			
			projectExpDao.deleteById(CandidateProjectExp.class, id);
			deleteRes.setMessage("Delete Candidate Project Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return deleteRes;
	}
	
	public DeleteResDto deteCandidateProjectExpFromCandidate(String code) {
		final DeleteResDto deleteRes = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			final CandidateProjectExp project = projectExpDao.getByCode(code);
			projectExpDao.deleteById(CandidateProjectExp.class, project.getId());
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return deleteRes;
	}

}
