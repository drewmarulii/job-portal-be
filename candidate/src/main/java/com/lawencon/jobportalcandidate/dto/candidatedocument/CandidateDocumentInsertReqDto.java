package com.lawencon.jobportalcandidate.dto.candidatedocument;

public class CandidateDocumentInsertReqDto {
	private String docCode;
	private String docName;
	private String candidateId;
	private String email;
	private String fileTypeCode;
	private String fileName;
	private String fileExtension;

	
	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getFileTypeCode() {
		return fileTypeCode;
	}

	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}



	

}
