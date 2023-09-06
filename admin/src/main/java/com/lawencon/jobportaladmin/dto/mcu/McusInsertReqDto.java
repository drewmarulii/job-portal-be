package com.lawencon.jobportaladmin.dto.mcu;

import java.util.List;

public class McusInsertReqDto {

	private String applicantId;
	private String applicantCode;
	private String statusCode;
	private List<McuInsertReqDto> mcuData;

	public List<McuInsertReqDto> getMcuData() {
		return mcuData;
	}

	public void setMcuData(List<McuInsertReqDto> mcuData) {
		this.mcuData = mcuData;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantCode() {
		return applicantCode;
	}

	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
