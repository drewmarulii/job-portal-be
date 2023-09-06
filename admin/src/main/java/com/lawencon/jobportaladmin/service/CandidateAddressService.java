package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.CandidateAddressDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dto.DeleteResDto;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidateaddress.CandidateAddressInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateaddress.CandidateAddressResDto;
import com.lawencon.jobportaladmin.dto.candidateaddress.CandidateAddressUpdateReqDto;
import com.lawencon.jobportaladmin.model.CandidateAddress;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateAddressService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private CandidateUserDao candidateUserDao;
	
	@Autowired
	private CandidateAddressDao candidateAddressDao;

	@Autowired
	private PrincipalService<String> principalService;
	
	public List<CandidateAddressResDto> getAllByCandidate(String id){
		final List<CandidateAddressResDto> candidateAddressResList = new ArrayList<>();
		final List<CandidateAddress> candidateAddress = candidateAddressDao.getByCandidateId(id);
		for(int i = 0 ; i < candidateAddress.size() ; i++) {
			final CandidateAddressResDto addressRes = new CandidateAddressResDto();
			addressRes.setAddress(candidateAddress.get(i).getAddress());
			addressRes.setCity(candidateAddress.get(i).getCity());
			addressRes.setCountry(candidateAddress.get(i).getCountry());
			addressRes.setPostalCode(candidateAddress.get(i).getPostalCode());
			addressRes.setProvince(candidateAddress.get(i).getProvince());
			addressRes.setResidenceType(candidateAddress.get(i).getResidenceType());
			addressRes.setId(candidateAddress.get(i).getId());
			candidateAddressResList.add(addressRes);
		}
		return candidateAddressResList;
	}
		
	public InsertResDto insertCandidateAddress(CandidateAddressInsertReqDto data) {
		final InsertResDto insertResDto = new InsertResDto();
		try {
			em().getTransaction().begin();
			final CandidateAddress candidateAddress = new CandidateAddress();
			if (data.getAddressCode() != null) {				
				candidateAddress.setAddressCode(data.getAddressCode());
			} else {
				candidateAddress.setAddressCode(GenerateCode.generateCode());
			}
			candidateAddress.setAddress(data.getAddress());
			candidateAddress.setCity(data.getCity());
			candidateAddress.setCountry(data.getCountry());
			candidateAddress.setProvince(data.getProvince());
			candidateAddress.setPostalCode(data.getPostalCode());
			candidateAddress.setResidenceType(data.getResidenceType());
			final CandidateUser candidateUser = candidateUserDao.getByEmail(data.getEmail());
			candidateAddress.setCandidateUser(candidateUser);
			candidateAddress.setCreatedBy(candidateUser.getId());
			
			candidateAddressDao.save(candidateAddress);
			
			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return insertResDto;
	}
	
	public UpdateResDto updateCandidateAdress(CandidateAddressUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		try {
			em().getTransaction().begin();
			final CandidateAddress candidateAddress = candidateAddressDao.getById(CandidateAddress.class, data.getId());
			candidateAddress.setAddress(data.getAddress());
			candidateAddress.setCity(data.getCity());
			candidateAddress.setCountry(data.getCountry());
			candidateAddress.setProvince(data.getProvince());
			candidateAddress.setPostalCode(data.getPostalCode());
			candidateAddress.setResidenceType(data.getResidenceType());
			final CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, data.getCandidateId());
			candidateAddress.setCandidateUser(candidateUser);
			candidateAddress.setCreatedBy(principalService.getAuthPrincipal());
			final CandidateAddress candidateAddressId = candidateAddressDao.save(candidateAddress);
			
			updateResDto.setMessage("Update Candidate Address Success");
			updateResDto.setVersion(candidateAddressId.getVersion());
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return updateResDto;
	}
	
	public DeleteResDto deleteCandidateAddress(String id) {
		final DeleteResDto deleteRes = new DeleteResDto();
		try {
			em().getTransaction().begin();
			candidateAddressDao.deleteById(CandidateAddress.class, id);
			deleteRes.setMessage("Delete Candidate Address Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return deleteRes;
	}
	
	public DeleteResDto deleteCandidateAddressFromCandidate(String code) {
		final DeleteResDto deleteRes = new DeleteResDto();
		try {
			em().getTransaction().begin();
			final CandidateAddress address = candidateAddressDao.getByCode(code);
			candidateAddressDao.deleteById(CandidateAddress.class, address.getId());
			deleteRes.setMessage("Delete Candidate Address Success");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		return deleteRes;
	}
}
