package com.lawencon.jobportaladmin.dto.candidatelanguage;

public class CandidateLanguageUpdateReqDto {
	private String id;
	private String languageName;
	private String writingRate;
	private String speakingRate;
	private String listeningRate;
	private String candidateId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getWritingRate() {
		return writingRate;
	}

	public void setWritingRate(String writingRate) {
		this.writingRate = writingRate;
	}

	public String getSpeakingRate() {
		return speakingRate;
	}

	public void setSpeakingRate(String speakingRate) {
		this.speakingRate = speakingRate;
	}

	public String getListeningRate() {
		return listeningRate;
	}

	public void setListeningRate(String listeningRate) {
		this.listeningRate = listeningRate;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
}
