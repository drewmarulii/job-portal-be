package com.lawencon.jobportaladmin.dao;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.Company;
import com.lawencon.jobportaladmin.model.File;

@Repository
public class CompanyDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}
	public Company getByCode(String code) {
		final StringBuilder sql = new StringBuilder();
				sql.append("SELECT c.id ,c.companyCode,c.companyName, ");
				sql.append( " c.address,c.companyUrl,c.companyPhone,c.photo.id, ");
				sql.append("c.photo.fileName,c.photo.fileExtension, ");
				sql.append( "c.createdBy,c.createdAt , c.isActive, c.version ");
				sql.append( "FROM Company c ");
				sql.append( "WHERE c.companyCode = : code ");
		final Object companyObj = em().createQuery(sql.toString(), Company.class)
				.setParameter("code", code)
				.getSingleResult();
		
		final Object[] companyArr = (Object[]) companyObj;
		final Company company = new Company();
		company.setId(companyArr[0].toString());
		company.setCompanyCode(companyArr[1].toString());
		company.setCompanyName(companyArr[2].toString());
		company.setAddress(companyArr[3].toString());
		company.setCompanyUrl(companyArr[4].toString());
		company.setCompanyPhone(companyArr[5].toString());
		
		final File file = new File();
		file.setId(companyArr[6].toString());
		file.setFileName(companyArr[7].toString());
		file.setFileExtension(companyArr[8].toString());
		company.setPhoto(file);
		
		company.setCreatedBy(companyArr[9].toString());
		company.setCreatedAt(LocalDateTime.parse(companyArr[10].toString()));
		company.setIsActive(Boolean.valueOf(companyArr[11].toString()));
		company.setVersion(Integer.valueOf(companyArr[12].toString()));
		
		return company;
	}
}
