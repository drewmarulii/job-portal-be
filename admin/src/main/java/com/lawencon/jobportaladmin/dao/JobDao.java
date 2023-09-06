package com.lawencon.jobportaladmin.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.Company;
import com.lawencon.jobportaladmin.model.EmploymentType;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.Job;
import com.lawencon.jobportaladmin.model.User;
import com.lawencon.jobportaladmin.util.BigDecimalUtil;

@Repository
public class JobDao extends AbstractJpaDao{

	private EntityManager em() {	
		return ConnHandler.getManager();
	}
	
	public List<Job> getByCompany(String code) {
		final List<Job> jobs = new ArrayList<>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ")
			.append(" tj.id AS job_id, ")
			.append(" job_name, ")
			.append(" company_name, ")
			.append(" address, ")
			.append(" start_date, ")
			.append(" end_date, ")
			.append(" hr_id, ")
			.append(" pic_id, ")
			.append(" expected_salary_min, ")
			.append(" expected_salary_max, ")
			.append(" employment_type_name, ")
			.append(" job_picture_id ")
			.append("FROM ")
			.append(" t_job tj ")
			.append("INNER JOIN ")
			.append(" t_company tc ON tc.id = tj.company_id ")
			.append("INNER JOIN ")
			.append(" t_employment_type tet ON tet.id = tj.employment_type_id ")
			.append("INNER JOIN ")
			.append(" t_user tu ON tu.id = tj.hr_id ")
			.append("INNER JOIN ")
			.append(" Wt_user tu2 ON tu2.id = tj.pic_id ")
			.append("WHERE ")
			.append(" company_code = :companycode");

			
		final String sql = "SELECT "
				+ "	tj.id AS job_id, "
				+ "	job_name, "
				+ "	company_name, "
				+ "	address, "
				+ "	start_date, "
				+ "	end_date, "
				+ " hr_id, "
				+ " pic_id, "
				+ "	expected_salary_min, "
				+ "	expected_salary_max, "
				+ "	employment_type_name, "
				+ "	job_picture_id "
				+ "FROM "
				+ "	t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN "
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id "
				+ "INNER JOIN "
				+ "	t_user tu ON tu.id = tj.hr_id "
				+ "INNER JOIN "
				+ "	t_user tu2 ON tu2.id = tj.pic_id "
				+ "WHERE "
				+ "	company_code = :companycode";
			
		final List<?> jobObjs = em().createNativeQuery(sql)
				.setParameter("companycode", code)
				.getResultList();
		
		if (jobObjs.size() > 0) {
			for (Object jobObj : jobObjs) {
				final Object[] jobArr = (Object[]) jobObj;
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobName(jobArr[1].toString());
				
				final Company company = new Company();
				company.setCompanyName(jobArr[2].toString());
				company.setAddress(jobArr[3].toString());
				job.setCompany(company);
				
				job.setStartDate(LocalDate.parse(jobArr[4].toString(), formatter));
				job.setEndDate(LocalDate.parse(jobArr[5].toString(), formatter));
				
				final User hr = new User();
				hr.setId(jobArr[6].toString());
				job.setHr(hr);
				
				final User pic = new User();
				pic.setId(jobArr[7].toString());
				job.setPic(pic);
				
				job.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(jobArr[8].toString()));
				job.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(jobArr[9].toString()));
				
				final EmploymentType type = new EmploymentType();
				type.setEmploymentTypeName(jobArr[10].toString());
				job.setEmploymentType(type);
				
				final File file = new File();
				file.setId(jobArr[11].toString());
				job.setJobPicture(file);
				
				jobs.add(job);
			}
		}
		
		return jobs;
	}
	
	public List<Job> getByPerson(String id) {
		final List<Job> jobs = new ArrayList<>();
		
		final StringBuilder sqlb = new StringBuilder();

			sqlb.append("SELECT ")
			.append(" tj.id AS job_id, ")
			.append(" job_name, ")
			.append(" company_name, ")
			.append(" address, ")
			.append(" start_date, ")
			.append(" end_date, ")
			.append(" hr_id, ")
			.append(" pic_id, ")
			.append(" expected_salary_min, ")
			.append(" expected_salary_max, ")
			.append(" employment_type_name, ")
			.append(" job_picture_id ")
			.append("FROM ")
			.append(" t_job tj ")
			.append("INNER JOIN ")
			.append(" t_company tc ON tc.id = tj.company_id ")
			.append("INNER JOIN ")
			.append(" t_employment_type tet ON tet.id = tj.employment_type_id ")
			.append("WHERE ")
			.append(" tj.hr_id = :id  ;");
		
		final String sql = "SELECT "
				+ "	tj.id AS job_id,"
				+ "	job_name,"
				+ "	tc.company_name,"
				+ "	address,"
				+ "	start_date,"
				+ "	end_date,"
				+ "	expected_salary_min,"
				+ "	expected_salary_max,"
				+ "	employment_type_name,"
				+ "	job_picture_id "
				+ "FROM "
				+ "	t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN"
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id "
				+ "WHERE "
				+ "	tj.hr_id = :id OR tj.created_by =:id";

		final List<?> jobObjs = em().createNativeQuery(sql)
				.setParameter("id", id)
				.getResultList();
		
		if (jobObjs.size() > 0) {
			for (Object jobObj : jobObjs) {
				final Object[] jobArr = (Object[]) jobObj;
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobName(jobArr[1].toString());
				
				final Company company = new Company();
				company.setCompanyName(jobArr[2].toString());
				company.setAddress(jobArr[3].toString());
				job.setCompany(company);
				
				job.setStartDate(LocalDate.parse(jobArr[4].toString()));
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				job.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(jobArr[6].toString()));
				job.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(jobArr[7].toString()));
				
				final EmploymentType type = new EmploymentType();
				type.setEmploymentTypeName(jobArr[8].toString());
				job.setEmploymentType(type);
				
				final File file = new File();
				file.setId(jobArr[9].toString());
				job.setJobPicture(file);
				jobs.add(job);
			}
		}
		
		return jobs;
	}

	public List<Job> getByAssignedHR(String id) {
		final List<Job> jobs = new ArrayList<>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" tj.id AS job_id, ");
			sqlb.append(" job_name, ");
			sqlb.append(" company_name, ");
			sqlb.append(" address, ");
			sqlb.append(" start_date, ");
			sqlb.append(" end_date, ");
			sqlb.append(" hr_id, ");
			sqlb.append(" pic_id, ");
			sqlb.append(" expected_salary_min, ");
			sqlb.append(" expected_salary_max, ");
			sqlb.append(" employment_type_name, ");
			sqlb.append(" job_picture_id ");
			sqlb.append("FROM ");
			sqlb.append(" t_job tj ");
			sqlb.append("INNER JOIN ");
			sqlb.append(" t_company tc ON tc.id = tj.company_id ");
			sqlb.append("INNER JOIN ");
			sqlb.append(" t_employment_type tet ON tet.id = tj.employment_type_id ");
			sqlb.append("INNER JOIN ");
			sqlb.append(" t_user tu ON tu.id = tj.hr_id ");
			sqlb.append("INNER JOIN ");
			sqlb.append(" t_user tu2 ON tu2.id = tj.pic_id ");
			sqlb.append("WHERE ");
			sqlb.append(" tj.hr_id = :hrid");
		
		final String sql = "SELECT "
				+ "	tj.id AS job_id,"
				+ "	job_name,"
				+ "	company_name,"
				+ "	address,"
				+ "	start_date,"
				+ "	end_date,"
				+ " hr_id,"
				+ " pic_id,"
				+ "	expected_salary_min,"
				+ "	expected_salary_max,"
				+ "	employment_type_name,"
				+ "	job_picture_id "
				+ "FROM "
				+ "	t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN"
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id "
				+ "INNER JOIN"
				+ "	t_user tu ON tu.id = tj.hr_id "
				+ "INNER JOIN"
				+ "	t_user tu2 ON tu2.id = tj.pic_id "
				+ "WHERE "
				+ "	hr_id = :hrid";
			
		final List<?> jobObjs = em().createNativeQuery(sqlb.toString())
				.setParameter("hrid", id)
				.getResultList();
		
		if (jobObjs.size() > 0) {
			for (Object jobObj : jobObjs) {
				final Object[] jobArr = (Object[]) jobObj;
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobName(jobArr[1].toString());
				
				final Company company = new Company();
				company.setCompanyName(jobArr[2].toString());
				company.setAddress(jobArr[3].toString());
				
				job.setStartDate(LocalDate.parse(jobArr[4].toString(), formatter));
				job.setEndDate(LocalDate.parse(jobArr[5].toString(), formatter));
				
				final User hr = new User();
				hr.setId(jobArr[6].toString());
				job.setHr(hr);
				
				final User pic = new User();
				pic.setId(jobArr[7].toString());
				job.setPic(pic);
				
				job.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(jobArr[8].toString()));
				job.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(jobArr[9].toString()));
				
				final EmploymentType type = new EmploymentType();
				type.setEmploymentTypeName(jobArr[10].toString());
				job.setEmploymentType(type);
				
				final File file = new File();
				file.setId(jobArr[11].toString());
				job.setJobPicture(file);
				
				jobs.add(job);
			}
		}
		
		return jobs;
	}
	
	public List<Job> getByAssignedPIC(String id) {
		final List<Job> jobs = new ArrayList<>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ")
			.append( "	tj.id AS job_id,")
			.append( "	job_name,")
			.append( "	tc.company_name,")
			.append( "	address,")
			.append( "	start_date,")
			.append( "	end_date,")
			.append( "	expected_salary_min,")
			.append( "	expected_salary_max,")
			.append( "	employment_type_name,")
			.append( "	job_picture_id ")
			.append( "FROM ")
			.append( "	t_job tj ")
			.append( "INNER JOIN ")
			.append( "	t_company tc ON tc.id = tj.company_id ")
			.append( "INNER JOIN")
			.append( "	t_employment_type tet ON tet.id = tj.employment_type_id ")
			.append( "WHERE ")
			.append(" pic_id = :picid");
		
		final String sql =  "SELECT "
				+ "	tj.id AS job_id,"
				+ "	job_name,"
				+ "	tc.company_name,"
				+ "	address,"
				+ "	start_date,"
				+ "	end_date,"
				+ "	expected_salary_min,"
				+ "	expected_salary_max,"
				+ "	employment_type_name,"
				+ "	job_picture_id "
				+ "FROM "
				+ "	t_job tj "
				+ "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id "
				+ "INNER JOIN"
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id "
				+ "WHERE "
				+ "	pic_id = :picid";
			
		final List<?> jobObjs = em().createNativeQuery(sqlb.toString())
				.setParameter("picid", id)
				.getResultList();
		
		if (jobObjs.size() > 0) {
			for (Object jobObj : jobObjs) {
				final Object[] jobArr = (Object[]) jobObj;
				final Job job = new Job();
				job.setId(jobArr[0].toString());
				job.setJobName(jobArr[1].toString());
				
				final Company company = new Company();
				company.setCompanyName(jobArr[2].toString());
				company.setAddress(jobArr[3].toString());
				job.setCompany(company);
				
				job.setStartDate(LocalDate.parse(jobArr[4].toString()));
				job.setEndDate(LocalDate.parse(jobArr[5].toString()));
				job.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(jobArr[6].toString()));
				job.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(jobArr[7].toString()));
				
				final EmploymentType type = new EmploymentType();
				type.setEmploymentTypeName(jobArr[8].toString());
				job.setEmploymentType(type);
				
				final File file = new File();
				file.setId(jobArr[9].toString());
				job.setJobPicture(file);
				jobs.add(job);
			}
		}
		
		return jobs;
	}
	
	public Job getByCode (String jobCode) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" j ");
			sqlb.append("FROM ");
			sqlb.append(" Job j ");
			sqlb.append("WHERE ");
			sqlb.append(" j.jobCode = :jobCode");
		
		final String sql = "SELECT "
				+ " j.id, "
				+ " j.jobName, "
				+ " j.jobCode, "
				+ " j.version "
				+ " FROM Job j"
				+ " WHERE j.jobCode = :jobCode";
		
		final Job job = this.em().createQuery(sqlb.toString(),Job.class).setParameter("jobCode", jobCode).getSingleResult();
	
		return job;
	}
}
