package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.constant.PersonTypes;
import com.lawencon.jobportaladmin.constant.Role;
import com.lawencon.jobportaladmin.dao.CandidateProfileDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dao.EmployeeDao;
import com.lawencon.jobportaladmin.dao.JobDao;
import com.lawencon.jobportaladmin.dao.PersonTypeDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.employee.EmployeInsertReqAdminDto;
import com.lawencon.jobportaladmin.dto.employee.EmployeeResDto;
import com.lawencon.jobportaladmin.model.CandidateProfile;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.model.Employee;
import com.lawencon.jobportaladmin.model.Job;
import com.lawencon.jobportaladmin.model.PersonType;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class EmployeeService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private CandidateProfileDao candidateProfileDao;

	@Autowired
	private PersonTypeDao personTypeDao;

	@Autowired
	private JobDao jobDao;

	@Autowired
	private PrincipalService<String> principalService;

	public List<EmployeeResDto> getAll() {
		final List<Employee> getList = employeeDao.getAll(Employee.class);
		final List<EmployeeResDto> employeeRes = new ArrayList<>();
		for (int i = 0; i < getList.size(); i++) {
			final EmployeeResDto resDto = new EmployeeResDto();
			resDto.setId(getList.get(i).getId());
			resDto.setCandidateName(getList.get(i).getCandidate().getCandidateProfile().getFullname());
			resDto.setPhoneNumber(getList.get(i).getCandidate().getCandidateProfile().getPhoneNumber());
			resDto.setJobName(getList.get(i).getJob().getJobName());
			resDto.setCompanyUrl(getList.get(i).getJob().getCompany().getCompanyUrl());
			resDto.setEmploymentTypeName(getList.get(i).getJob().getEmploymentType().getEmploymentTypeName());
			resDto.setCandidateEmail(getList.get(i).getCandidate().getUserEmail());
			resDto.setCreatedBy(getList.get(i).getCandidate().getCreatedBy());

			employeeRes.add(resDto);
		}
		return employeeRes;
	}

	public EmployeeResDto getById(String id) {
		final EmployeeResDto employeeResDto = new EmployeeResDto();
		final Employee employee = employeeDao.getById(Employee.class, id);

		employeeResDto.setId(employee.getId());
		employeeResDto.setCandidateName(employee.getCandidate().getCandidateProfile().getFullname());
		employeeResDto.setPhoneNumber(employee.getCandidate().getCandidateProfile().getPhoneNumber());
		employeeResDto.setJobName(employee.getJob().getJobName());
		employeeResDto.setCompanyUrl(employee.getJob().getCompany().getCompanyUrl());
		employeeResDto.setEmploymentTypeName(employee.getJob().getEmploymentType().getEmploymentTypeName());
		employeeResDto.setCandidateEmail(employee.getCandidate().getUserEmail());
		employeeResDto.setCreatedBy(employee.getJob().getCreatedBy());

		return employeeResDto;
	}

	public InsertResDto insertEmployeeFromAdmin(EmployeInsertReqAdminDto data) {
		final InsertResDto response = new InsertResDto();

		try {
			em().getTransaction().begin();
			final Employee employee = new Employee();
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, data.getCandidateId());
			final Job job = jobDao.getById(Job.class, data.getJobId());
			employee.setCandidate(candidateUser);
			employee.setJob(job);
			employee.setEmployeeCode(GenerateCode.generateCode());
			employeeDao.save(employee);

			response.setId(employee.getId());
			response.setMessage("Candidate Assigned to a Job");

			CandidateProfile candidateProfile = candidateProfileDao.getById(CandidateProfile.class,
					candidateUser.getCandidateProfile().getId());
			final PersonType personType = personTypeDao.getByCode(PersonTypes.EMPLOYEE.typeCode);
			candidateProfile.setPersonType(personType);

			candidateProfile = candidateProfileDao.saveAndFlush(candidateProfile);

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Assign Candidate to a Job Failed");
		}

		return response;
	}
}
