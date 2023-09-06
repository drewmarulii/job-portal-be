package com.lawencon.jobportaladmin.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportaladmin.dao.ReportDao;
import com.lawencon.jobportaladmin.dto.report.ReportFilterDto;
import com.lawencon.jobportaladmin.dto.report.ReportResDto;
import com.lawencon.jobportaladmin.util.DateUtil;
import com.lawencon.util.JasperUtil;

@Service
public class ReportService {

	@Autowired
	private ReportDao reportDao;

	@Autowired
	private JasperUtil jasperUtil;

	public List<ReportResDto> getReport(String startDate, String endDate) {
		LocalDate newStartDate = null;
		LocalDate newEndDate = null;

		if (startDate != null) {
			newStartDate = Date.valueOf(startDate).toLocalDate();
		}
		
		if (endDate != null || "".equalsIgnoreCase(endDate)) {
			newEndDate = Date.valueOf(endDate).toLocalDate();
		}
		
		final List<ReportResDto> reports = reportDao.getReport(newStartDate, newEndDate);
		return reports;
	}

	public byte[] downloadReport(String startDate, String endDate) throws Exception {

		LocalDate newStartDate = null;
		LocalDate newEndDate = null;

		if (startDate != null) {
			newStartDate = Date.valueOf(startDate).toLocalDate();

		}
		if (endDate != null || "".equalsIgnoreCase(endDate)) {
			newEndDate = Date.valueOf(endDate).toLocalDate();

		}

		final ReportFilterDto filterData = new ReportFilterDto();
		filterData.setStartDate(DateUtil.localDateToString(newStartDate));
		filterData.setEndDate(DateUtil.localDateToString(newEndDate));
		
		
		final Map<String, Object> filterDataMap = new HashMap<>();
		filterDataMap.put("startDate", filterData.getStartDate());
		filterDataMap.put("endDate", filterData.getEndDate());
		
		final List<ReportResDto> reports = reportDao.getReport(newStartDate, newEndDate);
		
		final byte[] data = jasperUtil.responseToByteArray(reports, filterDataMap, "Report");
		
		return data;
	}

}
