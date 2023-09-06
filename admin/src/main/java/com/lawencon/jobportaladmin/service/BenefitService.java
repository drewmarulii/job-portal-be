package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.dao.BenefitDao;
import com.lawencon.jobportaladmin.dao.OwnedBenefitDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.benefit.BenefitInsertReqDto;
import com.lawencon.jobportaladmin.dto.benefit.BenefitResDto;
import com.lawencon.jobportaladmin.model.Benefit;
import com.lawencon.jobportaladmin.model.OwnedBenefit;
import com.lawencon.jobportaladmin.util.GenerateCode;

@Service
public class BenefitService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private BenefitDao benefitDao;
	
	@Autowired
	private OwnedBenefitDao ownedBenefitDao;

	public InsertResDto insertBenefit(BenefitInsertReqDto benefitData) {
		final InsertResDto resDto = new InsertResDto();

		try {
			em().getTransaction().begin();

			Benefit benefit = new Benefit();
			if("".equals(benefitData.getBenefitName())) {
				em().getTransaction().rollback();
				throw new RuntimeException("Benefit name is null");
			}
			
			benefit.setBenefitName(benefitData.getBenefitName());
			benefit.setBenefitCode(GenerateCode.generateCode());

			benefit = benefitDao.save(benefit);

			resDto.setId(benefit.getId());
			resDto.setMessage("Insert Benefit Success");
			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return resDto;
	}	

	public List<BenefitResDto> getAll() {
		final List<BenefitResDto> benefitsDto = new ArrayList<>();
		final List<Benefit> benefits = benefitDao.getAll(Benefit.class);

		for (int i = 0; i < benefits.size(); i++) {
			final BenefitResDto benefitDto = new BenefitResDto();
			benefitDto.setId(benefits.get(i).getId());
			benefitDto.setBenefitName(benefits.get(i).getBenefitName());
			benefitsDto.add(benefitDto);
		}

		return benefitsDto;
	}
	
	public List<BenefitResDto> getByJob(String id){
		final List<OwnedBenefit>ownedBenefitList = ownedBenefitDao.getByJob(id);
		final List<BenefitResDto>benefitList = new ArrayList<>();
		for(int i = 0 ; i < ownedBenefitList.size() ; i++) {
			final BenefitResDto benefit = new BenefitResDto();
			benefit.setBenefitName(ownedBenefitList.get(i).getBenefit().getBenefitName());
			benefit.setId(ownedBenefitList.get(i).getBenefit().getId());
			benefitList.add(benefit);
		}
		return benefitList;
	}

}
