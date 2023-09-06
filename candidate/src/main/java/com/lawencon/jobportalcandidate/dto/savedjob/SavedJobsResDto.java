package com.lawencon.jobportalcandidate.dto.savedjob;

import java.util.List;

public class SavedJobsResDto {

	private List<SavedJobResDto> savedJobs;

	public List<SavedJobResDto> getSavedJobs() {
		return savedJobs;
	}

	public void setSavedJobs(List<SavedJobResDto> savedJobs) {
		this.savedJobs = savedJobs;
	}
	
}
