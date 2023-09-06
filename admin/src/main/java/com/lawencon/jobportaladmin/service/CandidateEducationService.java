package com.lawencon.jobportaladmin.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.CandidateEducationDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidateeducation.CandidateEducationInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateeducation.CandidateEducationResDto;
import com.lawencon.jobportaladmin.dto.candidateeducation.CandidateEducationUpdateReqDto;
import com.lawencon.jobportaladmin.model.CandidateEducation;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.util.DateUtil;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateEducationService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private CandidateEducationDao candidateEducationDao;

	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private PrincipalService<String> principalService;

	public List<CandidateEducationResDto> getEducationByCandidate(String id) {
		final List<CandidateEducationResDto> educationsDto = new ArrayList<>();
		final List<CandidateEducation> educations = candidateEducationDao.getEducationByCandidate(id);

		for (int i = 0; i < educations.size(); i++) {
			final CandidateEducationResDto education = new CandidateEducationResDto();
			education.setId(educations.get(i).getId());
			education.setDegreeName(educations.get(i).getDegreeName());
			education.setInstituitionName(educations.get(i).getInstitutionName());
			education.setMajors(educations.get(i).getMajors());
			education.setCgpa(educations.get(i).getCgpa());
			education.setStartYear(DateUtil.localDateToString(educations.get(i).getStartYear()));
			education.setEndYear(DateUtil.localDateToString(educations.get(i).getEndYear()));

			educationsDto.add(education);
		}

		return educationsDto;
	}

	public InsertResDto insertEducation(CandidateEducationInsertReqDto data) {
		final InsertResDto insertResDto = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateEducation education = new CandidateEducation();
			
			if (data.getEducationCode() != null) {
				education.setEducationCode(data.getEducationCode());
			} else {
				education.setEducationCode(GenerateCode.generateCode());
			}
			
			education.setDegreeName(data.getDegreeName());
			education.setInstitutionName(data.getInstituitionName());
			education.setMajors(data.getMajors());
			education.setCgpa(data.getCgpa());
			education.setStartYear(LocalDate.parse(data.getStartYear().toString()));
			education.setEndYear(LocalDate.parse(data.getEndYear().toString()));

			final CandidateUser candidate = candidateUserDao.getByEmail(data.getEmail());
			education.setCandidateUser(candidate);
			education.setCreatedBy(candidate.getId());
			
			candidateEducationDao.save(education);
			
			insertResDto.setId(education.getId());
			insertResDto.setMessage("Education has been added");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertResDto;
	}

	public UpdateResDto updateEducation(CandidateEducationUpdateReqDto data) {
		final CandidateEducation education = candidateEducationDao.getById(CandidateEducation.class, data.getId());

		UpdateResDto result = null;

		try {
			em().getTransaction().begin();
			education.setId(data.getId());
			education.setDegreeName(data.getDegreeName());
			education.setInstitutionName(data.getInstituitionName());
			education.setMajors(data.getMajors());
			education.setCgpa(data.getCgpa());
			education.setStartYear(LocalDate.parse(data.getStartYear()));
			education.setEndYear(LocalDate.parse(data.getEndYear()));

			final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, "ID Principal");
			education.setCandidateUser(candidate);
			education.setUpdatedBy(principalService.getAuthPrincipal());
			candidateEducationDao.saveAndFlush(education);

			result = new UpdateResDto();
			result.setVersion(education.getVersion());
			result.setMessage("Education has been updated");

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
		}

		return result;
	}

	public DeleteResDto deleteEducation(String id) {
		final DeleteResDto response = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			candidateEducationDao.deleteById(CandidateEducation.class, id);
			response.setMessage("Education has been removed");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return response;
	}
	
	public DeleteResDto deleteEducationFromCandidate(String code) {
		final DeleteResDto response = new DeleteResDto();
		
		try {
			em().getTransaction().begin();
			final CandidateEducation education = candidateEducationDao.getByCode(code);
			candidateEducationDao.deleteById(CandidateEducation.class, education.getId());
			response.setMessage("Education has been removed");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return response;
	}
}
