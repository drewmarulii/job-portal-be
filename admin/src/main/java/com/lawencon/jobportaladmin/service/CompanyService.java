package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lawencon.base.ConnHandler;
import com.lawencon.config.JwtConfig;
import com.lawencon.jobportaladmin.dao.CompanyDao;
import com.lawencon.jobportaladmin.dao.FileDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.company.CompanyInsertReqDto;
import com.lawencon.jobportaladmin.dto.company.CompanyResDto;
import com.lawencon.jobportaladmin.dto.company.CompanyUpdateReqDto;
import com.lawencon.jobportaladmin.dto.question.QuestionUpdateReqDto;
import com.lawencon.jobportaladmin.model.Company;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CompanyService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private FileDao fileDao;
	@Autowired
	private PrincipalService<String> principalService;
	@Autowired
	private RestTemplate restTemplate;

	public List<CompanyResDto> getAllCompany() {
		final List<Company> company = companyDao.getAll(Company.class);
		final List<CompanyResDto> companyResList = new ArrayList<>();
		for (int i = 0; i < company.size(); i++) {
			final CompanyResDto companyRes = new CompanyResDto();
			companyRes.setId(company.get(i).getId());
			companyRes.setCompanyName(company.get(i).getCompanyName());
			companyRes.setCompanyCode(company.get(i).getCompanyCode());
			companyRes.setCompanyPhone(company.get(i).getCompanyPhone());
			companyRes.setAddress(company.get(i).getAddress());

			if (company.get(i).getCompanyUrl() != null) {
				companyRes.setCompanyUrl(company.get(i).getCompanyUrl());
			}
			companyRes.setPhotoId(company.get(i).getPhoto().getId());
			companyResList.add(companyRes);

		}
		return companyResList;
	}
	
	

	public InsertResDto insertCompany(CompanyInsertReqDto data) {
		final InsertResDto insertRes = new InsertResDto();
		try {
			em().getTransaction().begin();
			if(data.getFileName().isBlank()) {
				em().getTransaction().rollback();
				throw new RuntimeException("File is Empty");
			}
			Company company = new Company();

			company.setCompanyName(data.getCompanyName());
			company.setCompanyCode(GenerateCode.generateCode());
			
			data.setCompanyCode(company.getCompanyCode());
			
			company.setCompanyPhone(data.getCompanyPhone());
			company.setAddress(data.getAddress());

			if (data.getCompanyUrl() != null) {
				company.setCompanyUrl(data.getCompanyUrl());
			}

			File file = new File();
			file.setFileName(data.getFileName());
			file.setFileExtension(data.getFileExtension());

			file = fileDao.save(file);
			
			company.setPhoto(file);

			company = companyDao.save(company);

			final String companyInsertCandidateAPI = "http://localhost:8081/companies";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<CompanyInsertReqDto> companyInsert = RequestEntity.post(companyInsertCandidateAPI)
					.headers(headers).body(data);

			final ResponseEntity<InsertResDto> responseCandidate = restTemplate.exchange(companyInsert, InsertResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.CREATED)) {
				insertRes.setId(company.getId());
				insertRes.setMessage("Insert company Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Insert Failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
			throw new RuntimeException(e.getMessage());
		}

		return insertRes;
	}

	public UpdateResDto updateCompany(CompanyUpdateReqDto data) {
		final Company company = companyDao.getById(Company.class, data.getId());
		
		final UpdateResDto updateRes = new UpdateResDto();
		
		try {
			em().getTransaction().begin();
			data.setCompanyCode(company.getCompanyCode());
			if(data.getCompanyName()!=null && !data.getCompanyName().equals("")) {
				company.setCompanyName(data.getCompanyName());
			}
			if(data.getCompanyPhone()!=null && !data.getCompanyPhone().equals("")) {
				company.setCompanyPhone(data.getCompanyPhone());
			}
			
			if(data.getAddress()!=null && !data.getAddress().equals("")) {
				company.setAddress(data.getAddress());
			}

			if (data.getCompanyUrl() != null && !data.getCompanyUrl().equals("") ) {
				company.setCompanyUrl(data.getCompanyUrl());
			}

			if(data.getFileName()!=null && data.getFileExtension()!=null && !data.getFileName().equals("") && !data.getFileExtension().equals("")) {
				File file = new File();
				file.setFileName(data.getFileName());
				file.setFileExtension(data.getFileExtension());
				file = fileDao.save(file);	
				company.setPhoto(file);
			}
			
			final Company companyId = companyDao.saveAndFlush(company);

			final String companyUpdateAPI = "http://localhost:8081/companies";

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<CompanyUpdateReqDto> companyUpdate = RequestEntity.patch(companyUpdateAPI)
					.headers(headers).body(data);

			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(companyUpdate,
					UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				updateRes.setVersion(companyId.getVersion());
				updateRes.setMessage("Company Update Success");
				System.out.println("=========SUCCESS");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");
			}
			
			} catch (Exception e) {
			e.printStackTrace();
			em().getTransaction().rollback();
			throw new RuntimeException(e.getMessage());
		}

		return updateRes;
	}
	
	public CompanyResDto getById(String id) {
		final Company company = companyDao.getById(Company.class, id);
		final CompanyResDto companyDto= new CompanyResDto();

		companyDto.setId(company.getId());
		companyDto.setCompanyCode(company.getCompanyCode());
		companyDto.setCompanyName(company.getCompanyName());
		companyDto.setAddress(company.getAddress());
		companyDto.setCompanyUrl(company.getCompanyUrl());
		companyDto.setCompanyPhone(company.getCompanyPhone());
		companyDto.setPhotoId(company.getPhoto().getId());
		
		return companyDto;
	}
}
