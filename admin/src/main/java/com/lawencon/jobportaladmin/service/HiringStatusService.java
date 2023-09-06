package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportaladmin.dao.HiringStatusDao;
import com.lawencon.jobportaladmin.dto.hiringstatus.HiringStatusGetResDto;
import com.lawencon.jobportaladmin.model.HiringStatus;

@Service
public class HiringStatusService {

	@Autowired
	private HiringStatusDao hiringStatusDao;

	public List<HiringStatusGetResDto> getHiringStatus() {
		final List<HiringStatusGetResDto> hiringStatusDto = new ArrayList<>();
		final List<HiringStatus> hiringStatus = hiringStatusDao.getAll(HiringStatus.class);

		for (int i = 0; i < hiringStatus.size(); i++) {
			final HiringStatusGetResDto hiringStats = new HiringStatusGetResDto();
			hiringStats.setId(hiringStatus.get(i).getId());
			hiringStats.setStatusName(hiringStatus.get(i).getStatusName());
			hiringStatusDto.add(hiringStats);
		}

		return hiringStatusDto;

	}

}
