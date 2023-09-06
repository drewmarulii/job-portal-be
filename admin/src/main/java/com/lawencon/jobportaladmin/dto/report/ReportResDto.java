package com.lawencon.jobportaladmin.dto.report;

import java.time.LocalDateTime;

public class ReportResDto {

	private String fullName;
	private String jobName;
	private LocalDateTime applyAt;
	private LocalDateTime hiredAt;
	private Long timeDifference;
	private String employmentTypeName;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Long getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(Long timeDifference) {
		this.timeDifference = timeDifference;
	}

	public String getEmploymentTypeName() {
		return employmentTypeName;
	}

	public void setEmploymentTypeName(String employmentTypeName) {
		this.employmentTypeName = employmentTypeName;
	}

	public LocalDateTime getApplyAt() {
		return applyAt;
	}

	public void setApplyAt(LocalDateTime applyAt) {
		this.applyAt = applyAt;
	}

	public LocalDateTime getHiredAt() {
		return hiredAt;
	}

	public void setHiredAt(LocalDateTime hiredAt) {
		this.hiredAt = hiredAt;
	}

	
}
