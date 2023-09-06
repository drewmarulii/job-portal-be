package com.lawencon.jobportalcandidate.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportalcandidate.model.Company;
import com.lawencon.jobportalcandidate.model.EmploymentType;
import com.lawencon.jobportalcandidate.model.File;
import com.lawencon.jobportalcandidate.model.Job;
import com.lawencon.jobportalcandidate.util.BigDecimalUtil;

@Repository
public class JobDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public List<Job> getByCompany(String code) {
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
		sqlb.append("WHERE ");
		sqlb.append(" company_code = :companycode");

		final String sql = "SELECT " + "	tj.id AS job_id," + "	job_name," + "	company_name," + "	address,"
				+ "	start_date," + "	end_date," + "	expected_salary_min," + "	expected_salary_max,"
				+ "	employment_type_name," + "	job_picture_id " + "FROM " + "	t_job tj" + "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id" + "INNER JOIN"
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id" + "WHERE "
				+ "	company_code = :companycode";

		final List<?> jobObjs = em().createNativeQuery(sqlb.toString()).setParameter("companycode", code)
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

	public List<Job> getBySalary(Float salary) {
		final List<Job> jobs = new ArrayList<>();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		final String sql = "SELECT " + "	tj.id AS job_id," + "	job_name," + "	company_name," + "	address,"
				+ "	start_date," + "	end_date," + "	expected_salary_min," + "	expected_salary_max,"
				+ "	employment_type_name," + "	job_picture_id " + "FROM " + "	t_job tj" + "INNER JOIN "
				+ "	t_company tc ON tc.id = tj.company_id" + "INNER JOIN"
				+ "	t_employment_type tet ON tet.id = tj.employment_type_id" + "WHERE "
				+ "	expected_salary_min >= :salary";

		final List<?> jobObjs = em().createNativeQuery(sql).setParameter("salary", salary).getResultList();

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

	public Job getByCode(String code) {
		final String sql = "SELECT j.id,j.jobCode,j.jobName, "
				+ "j.Company.companyName, j.Company.companyCode, j.startDate,j.endDate, "
				+ "j.description , j.expectedSalaryMin,j.expectedSalaryMax, "
				+ "j.employmentType.employmentTypeCode, j.employmentType.employmentTypeName, "
				+ "j.jobPicture.fileName , j.jobPicture.id , j.jobPicture.fileExtension, "
				+ "j.createdBy , j.createdAt , j.isActive, j.version  " + " FROM Job j " + " WHERE j.jobCode = :code";

		final Object jobObj = em().createQuery(sql).setParameter("code", code).getSingleResult();

		final Object[] jobArr = (Object[]) jobObj;
		Job job = null;
		if (jobArr.length > 0) {
			job = new Job();
			job.setId(jobArr[0].toString());
			job.setJobCode(jobArr[1].toString());
			job.setJobName(jobArr[2].toString());

			final Company company = new Company();
			company.setCompanyName(jobArr[3].toString());
			company.setCompanyCode(jobArr[4].toString());
			job.setCompany(company);

			job.setStartDate(LocalDate.parse(jobArr[5].toString()));
			job.setEndDate(LocalDate.parse(jobArr[6].toString()));
			job.setDescription(jobArr[7].toString());
			job.setExpectedSalaryMin(BigDecimalUtil.parseToBigDecimal(jobArr[8].toString()));
			job.setExpectedSalaryMax(BigDecimalUtil.parseToBigDecimal(jobArr[9].toString()));

			final EmploymentType type = new EmploymentType();
			type.setEmploymentTypeCode(jobArr[10].toString());
			type.setEmploymentTypeName(jobArr[11].toString());
			job.setEmploymentType(type);

			final File file = new File();
			file.setFileName(jobArr[12].toString());
			file.setId(jobArr[13].toString());
			file.setFileExtension(jobArr[14].toString());
			job.setJobPicture(file);

			job.setCreatedBy(jobArr[15].toString());
			job.setCreatedAt(LocalDateTime.parse(jobArr[16].toString()));
			job.setIsActive(Boolean.valueOf(jobArr[17].toString()));
			job.setVersion(Integer.valueOf(jobArr[18].toString()));
		}
		return job;
	}

	
	public List<Job>filterJob(String title,String location,BigDecimal salary){
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		final List<Job> jobs = new ArrayList<>();

		final StringBuilder sqlb = new StringBuilder();
		sqlb.append("SELECT ")
		.append(" tj.id AS job_id, ")
		.append(" job_name, ")
		.append(" company_name, ")
		.append(" tc.address, ")
		.append(" start_date, ")
		.append(" end_date, ")
		.append(" expected_salary_min, ")
		.append(" expected_salary_max, ")
		.append(" employment_type_name, ")
		.append(" job_picture_id, ")
		.append(" tc.photo_id ")
		.append("FROM ")
		.append(" t_job tj ")
		.append("INNER JOIN ")
		.append(" t_company tc ON tc.id = tj.company_id ")
		.append("INNER JOIN ")
		.append(" t_employment_type tet ON tet.id = tj.employment_type_id ")
		.append("WHERE ")
		.append(" 1 = 1 ");
		
		if(title != null && !title.equalsIgnoreCase("")) {
			sqlb.append(" AND tj.job_name ILIKE :name || '%' ");
		}
		if(location != null && !location.equalsIgnoreCase("")) {
			sqlb.append(" AND tc.address ILIKE :location || '%' ");
		}
		if(salary != null) {
			sqlb.append(" AND tj.expected_salary_min >= :salary ");
		}
		
		final Query jobQuery = em().createNativeQuery(sqlb.toString());
		
		if(title != null && !title.equalsIgnoreCase("")) {
			jobQuery.setParameter("name", title);
		}
		
		if(location != null && !location.equalsIgnoreCase("")) {
			jobQuery.setParameter("location", location);
		}
		if(salary != null) {
			jobQuery.setParameter("salary", salary);
		}
		
		final List<?> jobObjs =jobQuery.getResultList();

		if (jobObjs.size() > 0) {
			for (Object jobObj : jobObjs) {
				
					final Object[] jobArr = (Object[]) jobObj;
					final Job job = new Job();
					job.setId(jobArr[0].toString());
					job.setJobName(jobArr[1].toString());
					
					final Company company = new Company();
					company.setCompanyName(jobArr[2].toString());
					company.setAddress(jobArr[3].toString());
					final File companyPicture = new File();
					companyPicture.setId(jobArr[10].toString());
					company.setPhoto(companyPicture);
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


	public List<Job> getByTopThreeSalary() {
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
			.append("ORDER BY ")
			.append(" expected_salary_min ")
			.append("DESC ")
			.append("LIMIT ")
			.append(" 3");
		
		final List<?> jobObjs = em().createNativeQuery(sqlb.toString()).getResultList();
		
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
}
