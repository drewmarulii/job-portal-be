package com.lawencon.jobportalcandidate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportalcandidate.dao.CandidateStatusDao;
import com.lawencon.jobportalcandidate.dto.candidatestatus.CandidateStatusResDto;
import com.lawencon.jobportalcandidate.model.CandidateStatus;


@Service
public class CandidateStatusService {
	
	@Autowired
	private CandidateStatusDao statusDao;
	public List<CandidateStatusResDto> getAll(){
		final List<CandidateStatus> status = statusDao.getAll(CandidateStatus.class);
		final List<CandidateStatusResDto> candidateStatusResList = new ArrayList<>();
		for(int i = 0 ; i < status.size() ; i++) {
			final CandidateStatusResDto statusRes = new CandidateStatusResDto();
			statusRes.setId(status.get(i).getId());
			statusRes.setStatusCode(status.get(i).getStatusCode());
			statusRes.setStatusName(status.get(i).getStatusName());
			candidateStatusResList.add(statusRes);
		}
		return candidateStatusResList;
	}
}
