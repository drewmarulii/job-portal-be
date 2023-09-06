package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.CandidateSkillDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidateskill.CandidateSkillInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateskill.CandidateSkillResDto;
import com.lawencon.jobportaladmin.dto.candidateskill.CandidateSkillUpdateReqDto;
import com.lawencon.jobportaladmin.model.CandidateSkill;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateSkillService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private CandidateSkillDao candidateSkillDao;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateSkillResDto> getSkillsByCandidate(String id) {
		final List<CandidateSkillResDto> skillsDto = new ArrayList<>();
		final List<CandidateSkill> skills = candidateSkillDao.getByCandidate(id);

		for (int i = 0; i < skills.size(); i++) {
			final CandidateSkillResDto skill = new CandidateSkillResDto();
			skill.setId(skills.get(i).getId());
			skill.setSkillName(skills.get(i).getSkillName());

			skillsDto.add(skill);
		}

		return skillsDto;
	}

	public InsertResDto insertSkill(CandidateSkillInsertReqDto data) {
		final InsertResDto result = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateSkill skill = new CandidateSkill();
			
			if (data.getSkillCode() != null) {
				skill.setSkillCode(data.getSkillCode());
			} else {
				skill.setSkillCode(GenerateCode.generateCode());
			}
			
			skill.setSkillName(data.getSkillName());
			final CandidateUser candidate = candidateUserDao.getByEmail(data.getEmail());
			skill.setCandidateUser(candidate);
			skill.setCreatedBy(candidate.getId());

			candidateSkillDao.save(skill);

			result.setId(skill.getId());
			result.setMessage("Skill has been added");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}

	public UpdateResDto updateSkill(CandidateSkillUpdateReqDto data) {
		final CandidateSkill skill = candidateSkillDao.getById(CandidateSkill.class, data.getId());

		UpdateResDto result = null;

		try {
			em().getTransaction().begin();
			skill.setId(data.getId());
			skill.setSkillName(data.getSkillName());

			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			skill.setCandidateUser(candidate);
			skill.setUpdatedBy(principalService.getAuthPrincipal());
			candidateSkillDao.save(skill);

			result = new UpdateResDto();
			result.setVersion(skill.getVersion());
			result.setMessage("Skill record updated!");

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return result;
	}

	public DeleteResDto deleteSkill(String id) {
		final DeleteResDto response = new DeleteResDto();
	
		try {
			em().getTransaction().begin();			
			candidateSkillDao.deleteById(CandidateSkill.class, id);
			response.setMessage("Skill has been removed");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return response;
	}
	
	public DeleteResDto deleteSkillFromCandidate(String code) {
		final DeleteResDto response = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			final CandidateSkill skill = candidateSkillDao.getByCode(code);
			candidateSkillDao.deleteById(CandidateSkill.class, skill.getId());

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return response;
	}
}
