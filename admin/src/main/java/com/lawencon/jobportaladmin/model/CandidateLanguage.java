package com.lawencon.jobportaladmin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "t_candidate_language")
public class CandidateLanguage extends BaseEntity {

	@Column(name = "language_code",length = 5, nullable = false, unique = true)
	private String languageCode;
	
	@Column(name = "language_name", length = 30, nullable = false)
	private String languageName;

	@Column(name = "writing_rate", length = 2, nullable = false)
	private String writingRate;

	@Column(name = "speaking_rate", length = 2, nullable = false)
	private String speakingRate;

	@Column(name = "listening_rate", length = 2, nullable = false)
	private String listeningRate;

	@OneToOne
	@JoinColumn(name = "user_id")
	private CandidateUser candidateUser;
	
	

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
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

	public CandidateUser getCandidateUser() {
		return candidateUser;
	}

	public void setCandidateUser(CandidateUser candidateUser) {
		this.candidateUser = candidateUser;
	}

}
