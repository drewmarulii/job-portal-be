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
import com.lawencon.jobportaladmin.dao.ApplicantDao;
import com.lawencon.jobportaladmin.dao.FileDao;
import com.lawencon.jobportaladmin.dao.HiringStatusDao;
import com.lawencon.jobportaladmin.dao.McuDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.mcu.McuInsertReqDto;
import com.lawencon.jobportaladmin.dto.mcu.McuResDto;
import com.lawencon.jobportaladmin.dto.mcu.McusInsertReqDto;
import com.lawencon.jobportaladmin.model.Applicant;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.HiringStatus;
import com.lawencon.jobportaladmin.model.Mcu;

@Service
public class McuService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private McuDao mcuDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private ApplicantDao applicantDao;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HiringStatusDao hiringStatusDao;

	public InsertResDto insertMcuFiles(McusInsertReqDto data) {
		final InsertResDto resDto = new InsertResDto();

		try {
			em().getTransaction().begin();
			Applicant applicant = applicantDao.getById(Applicant.class, data.getApplicantId());

			for (int i = 0; i < data.getMcuData().size(); i++) {
				final McuInsertReqDto mcuData = data.getMcuData().get(i);
				Mcu newMcu = new Mcu();

				File newMcuFile = new File();
				newMcuFile.setFileName(mcuData.getFileName());
				newMcuFile.setFileExtension(mcuData.getFileExtension());
				newMcuFile = fileDao.save(newMcuFile);

				newMcu.setApplicant(applicant);
				newMcu.setFile(newMcuFile);
				newMcu = mcuDao.save(newMcu);
			}

			final HiringStatus hiringStatus = hiringStatusDao
					.getByCode(com.lawencon.jobportaladmin.constant.HiringStatus.MCU.statusCode);
			applicant.setStatus(hiringStatus);
			data.setApplicantCode(applicant.getApplicantCode());
			data.setStatusCode(hiringStatus.getStatusCode());

			applicant = applicantDao.saveAndFlush(applicant);

			final String updateApplicantAPI = "http://localhost:8081/applicants";
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(JwtConfig.get());

			final RequestEntity<McusInsertReqDto> applicantUpdate = RequestEntity.patch(updateApplicantAPI)
					.headers(headers).body(data);
			final ResponseEntity<UpdateResDto> responseCandidate = restTemplate.exchange(applicantUpdate,
					UpdateResDto.class);

			if (responseCandidate.getStatusCode().equals(HttpStatus.OK)) {
				resDto.setMessage("Insert Mcu Files Success");
				em().getTransaction().commit();

			} else {

				em().getTransaction().rollback();
				throw new RuntimeException("Update Failed");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException("Insert Mcu Failed");
		}

		return resDto;
	}

	public List<McuResDto> getByApplicant(String id) {
		final List<Mcu> mcus = mcuDao.getByApplicant(id);
		final List<McuResDto> mcuDtos = new ArrayList<>();

		for (int i = 0; i < mcus.size(); i++) {
			final McuResDto mcuDto = new McuResDto();
			final File file = fileDao.getById(File.class, mcus.get(i).getFile().getId());
			mcuDto.setId(file.getId());
			mcuDto.setFilename(file.getFileName());
			mcuDto.setFileExtension(file.getFileExtension());
			mcuDtos.add(mcuDto);
		}

		return mcuDtos;
	}

}
