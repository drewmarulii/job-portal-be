package com.lawencon.jobportaladmin.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.constant.PersonTypes;
import com.lawencon.jobportaladmin.dao.CandidateAddressDao;
import com.lawencon.jobportaladmin.dao.CandidateDocumentsDao;
import com.lawencon.jobportaladmin.dao.CandidateEducationDao;
import com.lawencon.jobportaladmin.dao.CandidateFamilyDao;
import com.lawencon.jobportaladmin.dao.CandidateLanguageDao;
import com.lawencon.jobportaladmin.dao.CandidateProfileDao;
import com.lawencon.jobportaladmin.dao.CandidateProjectExpDao;
import com.lawencon.jobportaladmin.dao.CandidateReferencesDao;
import com.lawencon.jobportaladmin.dao.CandidateSkillDao;
import com.lawencon.jobportaladmin.dao.CandidateStatusDao;
import com.lawencon.jobportaladmin.dao.CandidateTrainingExpDao;
import com.lawencon.jobportaladmin.dao.CandidateUserDao;
import com.lawencon.jobportaladmin.dao.CandidateWorkExpDao;
import com.lawencon.jobportaladmin.dao.FileDao;
import com.lawencon.jobportaladmin.dao.FileTypeDao;
import com.lawencon.jobportaladmin.dao.MaritalStatusDao;
import com.lawencon.jobportaladmin.dao.PersonTypeDao;
import com.lawencon.jobportaladmin.dao.ReligionDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.candidate.CandidateMasterInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateuser.CandidateUserInsertReqDto;
import com.lawencon.jobportaladmin.dto.candidateuser.CandidateUserResDto;
import com.lawencon.jobportaladmin.dto.candidateuser.CandidateUserUpdateReqDto;
import com.lawencon.jobportaladmin.model.CandidateAddress;
import com.lawencon.jobportaladmin.model.CandidateDocuments;
import com.lawencon.jobportaladmin.model.CandidateEducation;
import com.lawencon.jobportaladmin.model.CandidateFamily;
import com.lawencon.jobportaladmin.model.CandidateLanguage;
import com.lawencon.jobportaladmin.model.CandidateProfile;
import com.lawencon.jobportaladmin.model.CandidateProjectExp;
import com.lawencon.jobportaladmin.model.CandidateReferences;
import com.lawencon.jobportaladmin.model.CandidateSkill;
import com.lawencon.jobportaladmin.model.CandidateStatus;
import com.lawencon.jobportaladmin.model.CandidateTrainingExp;
import com.lawencon.jobportaladmin.model.CandidateUser;
import com.lawencon.jobportaladmin.model.CandidateWorkExp;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.FileType;
import com.lawencon.jobportaladmin.model.MaritalStatus;
import com.lawencon.jobportaladmin.model.PersonType;
import com.lawencon.jobportaladmin.model.Religion;
import com.lawencon.jobportaladmin.util.BigDecimalUtil;
import com.lawencon.jobportaladmin.util.DateUtil;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.jobportaladmin.util.MoneyUtil;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CandidateUserService {

	@Autowired
	private CandidateUserDao candidateUserDao;

	@Autowired
	private CandidateProfileDao candidateProfileDao;

	@Autowired
	private CandidateAddressDao candidateAddressDao;

	@Autowired
	private CandidateDocumentsDao candidateDocumentsDao;

	@Autowired
	private CandidateEducationDao candidateEducationDao;

	@Autowired
	private CandidateFamilyDao candidateFamilyDao;

	@Autowired
	private CandidateLanguageDao candidateLanguageDao;

	@Autowired
	private CandidateProjectExpDao candidateProjectExpDao;

	@Autowired
	private CandidateReferencesDao candidateRefDao;

	@Autowired
	private CandidateSkillDao candidateSkillDao;

	@Autowired
	private CandidateTrainingExpDao candidateTrainingDao;

	@Autowired
	private CandidateWorkExpDao candidateWorkExpDao;

	@Autowired
	private MaritalStatusDao maritalStatusDao;

	@Autowired
	private PersonTypeDao personTypeDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private FileTypeDao fileTypeDao;

	@Autowired
	private ReligionDao religionDao;

	@Autowired
	private CandidateStatusDao candidateStatusDao;

	@Autowired
	private PrincipalService<String> principalService;
	
	@Autowired
	private EmailService emailService;

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public InsertResDto insertCandidateFromAdmin(CandidateMasterInsertReqDto candidateData) {
		final InsertResDto insertResDto = new InsertResDto();
		try {
			em().getTransaction().begin();
			CandidateUser candidateUser = new CandidateUser();
			candidateUser.setUserEmail(candidateData.getUserEmail());

			CandidateProfile candidateProfile = new CandidateProfile();
			candidateProfile.setSalutation(candidateData.getSalutation());
			candidateProfile.setFullname(candidateData.getFullname());
			candidateProfile.setGender(candidateData.getGender());
			candidateProfile.setExperience(candidateData.getExperience());
			candidateProfile.setExpectedSalary(BigDecimal.valueOf(Long.valueOf(candidateData.getExpectedSalary())));
			candidateProfile.setPhoneNumber(candidateData.getPhoneNumber());
			candidateProfile.setMobileNumber(candidateData.getMobileNumber());
			candidateProfile.setNik(candidateData.getNik());
			candidateProfile.setBirthPlace(candidateData.getBirthPlace());
			candidateProfile.setBirthDate(LocalDate.parse(candidateData.getBirthDate()));

			final MaritalStatus status = maritalStatusDao.getById(MaritalStatus.class,
					candidateData.getMaritalStatusId());
			candidateProfile.setMaritalStatus(status);

			final Religion religion = religionDao.getById(Religion.class, candidateData.getReligionId());
			candidateProfile.setReligion(religion);

			final PersonType type = personTypeDao.getByCode(PersonTypes.CANDIDATE.typeCode);
			candidateProfile.setPersonType(type);

			final CandidateStatus candidateStatus = candidateStatusDao
					.getByCode(com.lawencon.jobportaladmin.constant.CandidateStatus.ACTIVE.typeCode);
			candidateProfile.setCandidateStatus(candidateStatus);

			if (candidateData.getFile() != null) {
				final File file = new File();
				file.setFileName(candidateData.getFile());
				file.setFileExtension(candidateData.getFileExtension());
				file.setCreatedBy(principalService.getAuthPrincipal());
				fileDao.save(file);
				candidateProfile.setFile(file);
			}

			candidateProfile.setUpdatedBy(principalService.getAuthPrincipal());
			candidateProfileDao.save(candidateProfile);
			candidateUser.setCandidateProfile(candidateProfile);
			candidateUserDao.save(candidateUser);

			// Candidate Addresses
			if (candidateData.getCandidateAddress() != null) {
				for (int i = 0; i < candidateData.getCandidateAddress().size(); i++) {
					final CandidateAddress candidateAddress = new CandidateAddress();
					candidateAddress.setAddress(candidateData.getCandidateAddress().get(i).getAddress());
					candidateAddress.setCity(candidateData.getCandidateAddress().get(i).getCity());
					candidateAddress.setCountry(candidateData.getCandidateAddress().get(i).getCountry());
					candidateAddress.setProvince(candidateData.getCandidateAddress().get(i).getProvince());
					candidateAddress.setPostalCode(candidateData.getCandidateAddress().get(i).getPostalCode());
					candidateAddress.setResidenceType(candidateData.getCandidateAddress().get(i).getResidenceType());

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					candidateAddress.setCandidateUser(candidate);
					candidateAddress.setCreatedBy(principalService.getAuthPrincipal());

					candidateAddressDao.save(candidateAddress);
				}
			}

			// Candidate Documents
			if (candidateData.getCandidateDocuments() != null) {
				for (int i = 0; i < candidateData.getCandidateDocuments().size(); i++) {
					final CandidateDocuments candidateDocument = new CandidateDocuments();
					candidateDocument.setDocName(candidateData.getCandidateDocuments().get(i).getDocName());
					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					candidateDocument.setCandidateUser(candidate);

					final FileType fileType = fileTypeDao
							.getByCode(candidateData.getCandidateDocuments().get(i).getFileTypeCode());
					candidateDocument.setFileType(fileType);

					final File file = new File();
					file.setFileName(candidateData.getCandidateDocuments().get(i).getFileName());
					file.setFileExtension(candidateData.getCandidateDocuments().get(i).getFileExtension());
					file.setCreatedBy(principalService.getAuthPrincipal());
					fileDao.save(file);

					candidateDocument.setFile(file);
					candidateDocument.setCreatedBy(principalService.getAuthPrincipal());

					candidateDocumentsDao.save(candidateDocument);
				}
			}

			// Candidate Educations
			if (candidateData.getCandidateEducations() != null) {
				for (int i = 0; i < candidateData.getCandidateEducations().size(); i++) {
					final CandidateEducation education = new CandidateEducation();
					education.setDegreeName(candidateData.getCandidateEducations().get(i).getDegreeName());
					education.setInstitutionName(candidateData.getCandidateEducations().get(i).getInstituitionName());
					education.setMajors(candidateData.getCandidateEducations().get(i).getMajors());
					education.setCgpa(candidateData.getCandidateEducations().get(i).getCgpa());
					education.setStartYear(
							LocalDate.parse(candidateData.getCandidateEducations().get(i).getStartYear().toString()));
					education.setEndYear(
							LocalDate.parse(candidateData.getCandidateEducations().get(i).getEndYear().toString()));

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					education.setCandidateUser(candidate);
					education.setCreatedBy(principalService.getAuthPrincipal());

					candidateEducationDao.save(education);

				}
			}

			// Candidate Families
			if (candidateData.getCandidateFamily() != null) {
				for (int i = 0; i < candidateData.getCandidateFamily().size(); i++) {
					final CandidateFamily family = new CandidateFamily();
					family.setFullname(candidateData.getCandidateFamily().get(i).getFullname());
					family.setRelationship(candidateData.getCandidateFamily().get(i).getRelationship());
					family.setDegreeName(candidateData.getCandidateFamily().get(i).getDegreeName());
					family.setOccupation(candidateData.getCandidateFamily().get(i).getOccupation());
					family.setBirthDate(
							LocalDate.parse(candidateData.getCandidateFamily().get(i).getBirthDate().toString()));
					family.setBirthPlace(candidateData.getCandidateFamily().get(i).getBirthPlace());

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					family.setCandidateUser(candidate);
					family.setCreatedBy(principalService.getAuthPrincipal());

					candidateFamilyDao.save(family);
				}
			}

			// Candidate Languages
			if (candidateData.getCandidateLanguage() != null) {
				for (int i = 0; i < candidateData.getCandidateLanguage().size(); i++) {
					final CandidateLanguage candidateLanguage = new CandidateLanguage();
					candidateLanguage.setLanguageName(candidateData.getCandidateLanguage().get(i).getLanguageName());
					candidateLanguage.setListeningRate(candidateData.getCandidateLanguage().get(i).getListeningRate());
					candidateLanguage.setSpeakingRate(candidateData.getCandidateLanguage().get(i).getSpeakingRate());
					candidateLanguage.setWritingRate(candidateData.getCandidateLanguage().get(i).getWritingRate());

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					candidateLanguage.setCandidateUser(candidate);
					candidateLanguage.setCreatedBy(principalService.getAuthPrincipal());

					candidateLanguageDao.save(candidateLanguage);
				}
			}

			// Candidate Projects
			if (candidateData.getCandidateProjectExp() != null) {
				for (int i = 0; i < candidateData.getCandidateProjectExp().size(); i++) {
					final CandidateProjectExp projectExp = new CandidateProjectExp();
					projectExp.setProjectName(candidateData.getCandidateProjectExp().get(i).getProjectName());
					projectExp.setDescription(candidateData.getCandidateProjectExp().get(i).getDescription());
					projectExp.setProjectUrl(candidateData.getCandidateProjectExp().get(i).getProjectUrl());
					projectExp.setStartDate(
							LocalDate.parse(candidateData.getCandidateProjectExp().get(i).getStartDate().toString()));
					projectExp.setEndDate(
							LocalDate.parse(candidateData.getCandidateProjectExp().get(i).getEndDate().toString()));

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					projectExp.setCandidateUser(candidate);
					projectExp.setCreatedBy(principalService.getAuthPrincipal());

					candidateProjectExpDao.save(projectExp);
				}
			}

			// Candidate References
			if (candidateData.getCandidateReferences() != null) {
				for (int i = 0; i < candidateData.getCandidateReferences().size(); i++) {
					final CandidateReferences reference = new CandidateReferences();
					reference.setFullName(candidateData.getCandidateReferences().get(i).getFullname());
					reference.setRelationship(candidateData.getCandidateReferences().get(i).getRelationship());
					reference.setOccupation(candidateData.getCandidateReferences().get(i).getOccupation());
					reference.setPhoneNumber(candidateData.getCandidateReferences().get(i).getPhoneNumber());
					reference.setEmail(candidateData.getCandidateReferences().get(i).getEmail());
					reference.setCompany(candidateData.getCandidateReferences().get(i).getCompany());
					reference.setDescription(candidateData.getCandidateReferences().get(i).getDescription());

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					reference.setCandidateUser(candidate);
					reference.setCreatedBy(principalService.getAuthPrincipal());

					candidateRefDao.save(reference);
				}
			}

			// Candidate Skills
			if (candidateData.getCandidateSkill() != null) {
				for (int i = 0; i < candidateData.getCandidateSkill().size(); i++) {
					final CandidateSkill skill = new CandidateSkill();
					skill.setSkillName(candidateData.getCandidateSkill().get(i).getSkillName());

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					skill.setCandidateUser(candidate);
					skill.setCreatedBy(principalService.getAuthPrincipal());

					candidateSkillDao.save(skill);
				}
			}

			// Candidate Training
			if (candidateData.getCandidateTrainingExp() != null) {
				for (int i = 0; i < candidateData.getCandidateTrainingExp().size(); i++) {
					final CandidateTrainingExp trainingExp = new CandidateTrainingExp();
					trainingExp.setCreatedBy(principalService.getAuthPrincipal());
					trainingExp.setTrainingName(candidateData.getCandidateTrainingExp().get(i).getTrainingName());
					trainingExp
							.setOrganizationName(candidateData.getCandidateTrainingExp().get(i).getOrganizationName());
					trainingExp.setStartDate(
							LocalDate.parse(candidateData.getCandidateTrainingExp().get(i).getStartDate().toString()));
					trainingExp.setEndDate(
							LocalDate.parse(candidateData.getCandidateTrainingExp().get(i).getEndDate().toString()));
					trainingExp.setDescription(candidateData.getCandidateTrainingExp().get(i).getDescription());

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					trainingExp.setCandidateUser(candidate);
					trainingExp.setCreatedBy(principalService.getAuthPrincipal());

					candidateTrainingDao.save(trainingExp);

				}
			}

			// Candidate Works
			if (candidateData.getCandidateWorkExp() != null) {
				for (int i = 0; i < candidateData.getCandidateWorkExp().size(); i++) {
					final CandidateWorkExp work = new CandidateWorkExp();
					work.setPositionName(candidateData.getCandidateWorkExp().get(i).getPositionName());
					work.setCompanyName(candidateData.getCandidateWorkExp().get(i).getCompanyName());
					work.setAddress(candidateData.getCandidateWorkExp().get(i).getAddress());
					work.setResponsibility(candidateData.getCandidateWorkExp().get(i).getResponsibility());
					work.setReasonLeave(candidateData.getCandidateWorkExp().get(i).getReasonLeave());
					work.setLastSalary(BigDecimalUtil
							.parseToBigDecimal(candidateData.getCandidateWorkExp().get(i).getLastSalary().toString()));
					work.setStartDate(
							LocalDate.parse(candidateData.getCandidateWorkExp().get(i).getStartDate().toString()));
					work.setEndDate(
							LocalDate.parse(candidateData.getCandidateWorkExp().get(i).getEndDate().toString()));

					final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class,
							candidateUser.getId());
					work.setCandidateUser(candidate);
					work.setCreatedBy(principalService.getAuthPrincipal());

					candidateWorkExpDao.save(work);
				}
			}

			insertResDto.setId(candidateUser.getId());
			insertResDto.setMessage("Candidate has been added!");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertResDto;
	}

	public UpdateResDto updateCandidateFromAdmin(CandidateUserUpdateReqDto candidateData) {
		final UpdateResDto updateResDto = new UpdateResDto();
		
		try {
			em().getTransaction().begin();
			CandidateUser candidateUser = candidateUserDao.getById(CandidateUser.class, candidateData.getId());
			candidateUser.setUserEmail(candidateData.getUserEmail());

			CandidateProfile candidateProfile = candidateProfileDao.getById(CandidateProfile.class, candidateUser.getCandidateProfile().getId());
			candidateProfile.setSalutation(candidateData.getProfile().getSalutation());
			candidateProfile.setFullname(candidateData.getProfile().getFullname());
			candidateProfile.setGender(candidateData.getProfile().getGender());
			candidateProfile.setExperience(candidateData.getProfile().getExperience());
			candidateProfile.setExpectedSalary(BigDecimal.valueOf(Long.valueOf(candidateData.getProfile().getExpectedSalary())));
			candidateProfile.setPhoneNumber(candidateData.getProfile().getPhoneNumber());
			candidateProfile.setMobileNumber(candidateData.getProfile().getMobileNumber());
			candidateProfile.setNik(candidateData.getProfile().getNik());
			candidateProfile.setBirthPlace(candidateData.getProfile().getBirthPlace());
			candidateProfile.setBirthDate(LocalDate.parse(candidateData.getProfile().getBirthDate()));

			final MaritalStatus status = maritalStatusDao.getById(MaritalStatus.class,
					candidateData.getProfile().getMaritalStatusId());
			candidateProfile.setMaritalStatus(status);

			final Religion religion = religionDao.getById(Religion.class, candidateData.getProfile().getReligionId());
			candidateProfile.setReligion(religion);

			final PersonType type = personTypeDao.getById(PersonType.class, candidateData.getProfile().getPersonTypeId());
			candidateProfile.setPersonType(type);

			final CandidateStatus candidateStatus = candidateStatusDao
					.getByCode(com.lawencon.jobportaladmin.constant.CandidateStatus.ACTIVE.typeCode);
			candidateProfile.setCandidateStatus(candidateStatus);

			if (!candidateData.getProfile().getFile().isBlank()) {
				final String fileId = candidateData.getProfile().getFileId();
				final File file = new File();
				file.setFileName(candidateData.getProfile().getFile());
				file.setFileExtension(candidateData.getProfile().getFileExtension());
				file.setCreatedBy(principalService.getAuthPrincipal());
				fileDao.save(file);
				fileDao.deleteById(File.class, fileId);
				candidateProfile.setFile(file);
			}

			final CandidateStatus candidatestatus = candidateStatusDao.getById(CandidateStatus.class,
					candidateData.getProfile().getCandidateStatusId());
			candidateProfile.setCandidateStatus(candidatestatus);
			candidateProfile.setUpdatedBy(principalService.getAuthPrincipal());
			candidateProfileDao.saveAndFlush(candidateProfile);
			candidateUser.setCandidateProfile(candidateProfile);
			candidateUserDao.saveAndFlush(candidateUser);
			
			updateResDto.setVersion(candidateUser.getVersion());
			updateResDto.setMessage("Candidate has been updated!");
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return updateResDto;
	}
	
	public InsertResDto insertCandidateuser(CandidateUserInsertReqDto candidateData) {
		final InsertResDto insertResDto = new InsertResDto();

		try {
			em().getTransaction().begin();

			CandidateUser candidateUser = new CandidateUser();
			candidateUser.setUserEmail(candidateData.getUserEmail());

			CandidateProfile candidateProfile = new CandidateProfile();
			candidateProfile.setFullname(candidateData.getProfile().getFullname());
			final PersonType personType = personTypeDao.getByCode(PersonTypes.CANDIDATE.typeCode);
			candidateProfile.setPersonType(personType);
			final CandidateStatus candidateStatus = candidateStatusDao
					.getByCode(com.lawencon.jobportaladmin.constant.CandidateStatus.ACTIVE.typeCode);
			candidateProfile.setCandidateStatus(candidateStatus);

			candidateProfile = candidateProfileDao.saveNoLogin(candidateProfile, () -> GenerateCode.generateCode());
			candidateUser.setCandidateProfile(candidateProfile);
			candidateUser = candidateUserDao.saveNoLogin(candidateUser, () -> GenerateCode.generateCode());

			final String emailSubject = "Welcome to JobRoad";
			emailService.sendEmailNewCandidate(candidateUser, emailSubject);
			
			insertResDto.setId(candidateUser.getId());
			insertResDto.setMessage("Candidate has been added!");

			em().getTransaction().commit();

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertResDto;
	}

	public UpdateResDto updateCandidateUser(CandidateUserUpdateReqDto data) {
		final UpdateResDto updateResDto = new UpdateResDto();
		
		try {
			em().getTransaction().begin();
			CandidateUser candidateUser = candidateUserDao.getByEmail(data.getUserEmail());
			candidateUser.setUserEmail(data.getUserEmail());
			
			CandidateProfile profile = candidateProfileDao.getById(CandidateProfile.class, candidateUser.getCandidateProfile().getId());
			profile.setSalutation(data.getProfile().getSalutation());
			profile.setFullname(data.getProfile().getFullname());
			profile.setGender(data.getProfile().getGender());
			profile.setExperience(data.getProfile().getExperience());
			profile.setExpectedSalary(BigDecimal.valueOf(Long.valueOf(data.getProfile().getExpectedSalary())));
			profile.setPhoneNumber(data.getProfile().getPhoneNumber());
			profile.setMobileNumber(data.getProfile().getMobileNumber());
			profile.setNik(data.getProfile().getNik());
			profile.setBirthDate(LocalDate.parse(data.getProfile().getBirthDate().toString()));
			profile.setBirthPlace(data.getProfile().getBirthPlace());
			
			final MaritalStatus status = maritalStatusDao.getByCode(data.getProfile().getMaritalStatusCode());
			profile.setMaritalStatus(status);

			final Religion religion = religionDao.getByCode(data.getProfile().getReligionCode());
			profile.setReligion(religion);

			final PersonType type = personTypeDao.getByCode(data.getProfile().getPersonTypeCode());
			profile.setPersonType(type);
			
			if (!data.getProfile().getFile().isBlank()) {
				final String fileId = data.getProfile().getFileId();

				File file = new File();
				file.setFileName(data.getProfile().getFile());
				file.setFileExtension(data.getProfile().getFileExtension());
				file.setCreatedBy(data.getId());
				file = fileDao.save(file);
				profile.setFile(file);
				
				if (fileId != null) {										
					fileDao.deleteById(File.class, fileId);
				}
			}

			final CandidateStatus candidatestatus = candidateStatusDao.getByCode(data.getProfile().getCandidateStatusCode());
			profile.setCandidateStatus(candidatestatus);
			profile.setUpdatedBy(data.getId());
			candidateProfileDao.saveAndFlush(profile);
			candidateUser.setCandidateProfile(profile);
			candidateUserDao.saveAndFlush(candidateUser);
			
			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}
		
		return updateResDto;
	}
	
	public List<CandidateUserResDto> getAll() {
		final List<CandidateUserResDto> candidatesDto = new ArrayList<>();
		final List<CandidateUser> candidate = candidateUserDao.getAll(CandidateUser.class);

		for (int i = 0; i < candidate.size(); i++) {
			final CandidateUserResDto candidateDto = new CandidateUserResDto();
			candidateDto.setId(candidate.get(i).getId());
			candidateDto.setFullname(candidate.get(i).getCandidateProfile().getFullname());
			candidateDto.setUserEmail(candidate.get(i).getUserEmail());
			candidateDto.setProfileId(candidate.get(i).getCandidateProfile().getId());
			candidateDto.setSalutation(candidate.get(i).getCandidateProfile().getSalutation());
			candidateDto.setFullname(candidate.get(i).getCandidateProfile().getFullname());
			candidateDto.setGender(candidate.get(i).getCandidateProfile().getGender());
			candidateDto.setExperience(candidate.get(i).getCandidateProfile().getExperience());
			if(candidate.get(i).getCandidateProfile().getExpectedSalary()!=null) {
				candidateDto.setExpectedSalary(MoneyUtil.parseToRupiah(candidate.get(i).getCandidateProfile().getExpectedSalary()));
			}
			
			candidateDto.setPhoneNumber(candidate.get(i).getCandidateProfile().getPhoneNumber());
			candidateDto.setMobileNumber(candidate.get(i).getCandidateProfile().getMobileNumber());
			candidateDto.setNik(candidate.get(i).getCandidateProfile().getNik());
			if (candidate.get(i).getCandidateProfile().getBirthDate() != null) {
				candidateDto.setBirthDate(DateUtil.localDateToString(candidate.get(i).getCandidateProfile().getBirthDate()));
			}
			candidateDto.setBirthPlace(candidate.get(i).getCandidateProfile().getBirthPlace());
			if (candidate.get(i).getCandidateProfile().getMaritalStatus() != null) {
				candidateDto.setMaritalStatusId(candidate.get(i).getCandidateProfile().getMaritalStatus().getId());
				final MaritalStatus marital = maritalStatusDao.getById(MaritalStatus.class,
						candidate.get(i).getCandidateProfile().getMaritalStatus().getId());
				candidateDto.setMaritalStatus(marital.getMaritalName());
				candidateDto.setReligionId(candidate.get(i).getCandidateProfile().getReligion().getId());
			}
			if (candidate.get(i).getCandidateProfile().getReligion() != null) {
				final Religion religion = religionDao.getById(Religion.class,
						candidate.get(i).getCandidateProfile().getReligion().getId());
				candidateDto.setReligion(religion.getReligionName());
				candidateDto.setPersonTypeId(candidate.get(i).getCandidateProfile().getPersonType().getId());
			}
			if (candidate.get(i).getCandidateProfile().getPersonType() != null) {
				final PersonType type = personTypeDao.getById(PersonType.class,
						candidate.get(i).getCandidateProfile().getPersonType().getId());
				candidateDto.setPersonType(type.getTypeName());
				
				candidateDto.setCandidateStatusId(candidate.get(i).getCandidateProfile().getCandidateStatus().getId());
			}
			if (candidate.get(i).getCandidateProfile().getCandidateStatus() != null) {
				final CandidateStatus status = candidateStatusDao.getById(CandidateStatus.class,
						candidate.get(i).getCandidateProfile().getCandidateStatus().getId());
				candidateDto.setCandidateStatus(status.getStatusName());
				candidateDto.setProfileId(candidate.get(i).getCandidateProfile().getId());
			}
			candidatesDto.add(candidateDto);
		}
		return candidatesDto;

	}

	public CandidateUserResDto getById(String id) {
		final CandidateUser candidate = candidateUserDao.getById(CandidateUser.class, id);

		final CandidateUserResDto candidateDto = new CandidateUserResDto();
		candidateDto.setId(candidate.getId());
		candidateDto.setFullname(candidate.getCandidateProfile().getFullname());
		candidateDto.setUserEmail(candidate.getUserEmail());
		candidateDto.setProfileId(candidate.getCandidateProfile().getId());
		candidateDto.setSalutation(candidate.getCandidateProfile().getSalutation());
		candidateDto.setFullname(candidate.getCandidateProfile().getFullname());
		candidateDto.setGender(candidate.getCandidateProfile().getGender());
		candidateDto.setExperience(candidate.getCandidateProfile().getExperience());
		if(candidate.getCandidateProfile().getExpectedSalary()!=null) {
			candidateDto.setExpectedSalary(MoneyUtil.parseToRupiah(candidate.getCandidateProfile().getExpectedSalary()));
			candidateDto.setExpectedSalaryNum(candidate.getCandidateProfile().getExpectedSalary());
		}
		candidateDto.setPhoneNumber(candidate.getCandidateProfile().getPhoneNumber());
		candidateDto.setMobileNumber(candidate.getCandidateProfile().getMobileNumber());
		candidateDto.setNik(candidate.getCandidateProfile().getNik());
		if (candidate.getCandidateProfile().getBirthDate() != null) {
			candidateDto.setBirthDate(DateUtil.localDateToString(candidate.getCandidateProfile().getBirthDate()));
		}
		candidateDto.setBirthPlace(candidate.getCandidateProfile().getBirthPlace());
		if (candidate.getCandidateProfile().getMaritalStatus() != null) {
			final MaritalStatus marital = maritalStatusDao.getById(MaritalStatus.class,
					candidate.getCandidateProfile().getMaritalStatus().getId());
			candidateDto.setMaritalStatusId(candidate.getCandidateProfile().getMaritalStatus().getId());
			candidateDto.setMaritalStatus(marital.getMaritalName());
		}
		if (candidate.getCandidateProfile().getReligion() != null) {
			final Religion religion = religionDao.getById(Religion.class,
					candidate.getCandidateProfile().getReligion().getId());
			candidateDto.setReligionId(candidate.getCandidateProfile().getReligion().getId());
			candidateDto.setReligion(religion.getReligionName());
		}
		if (candidate.getCandidateProfile().getPersonType() != null) {
			final PersonType type = personTypeDao.getById(PersonType.class,
					candidate.getCandidateProfile().getPersonType().getId());
			candidateDto.setPersonType(type.getTypeName());
			candidateDto.setPersonTypeId(candidate.getCandidateProfile().getPersonType().getId());
		}
		if (candidate.getCandidateProfile().getFile() != null) {			
			candidateDto.setFileId(candidate.getCandidateProfile().getFile().getId());
		}
		if (candidate.getCandidateProfile().getCandidateStatus() != null) {
			final CandidateStatus status = candidateStatusDao.getById(CandidateStatus.class,
					candidate.getCandidateProfile().getCandidateStatus().getId());
			candidateDto.setCandidateStatusId(candidate.getCandidateProfile().getCandidateStatus().getId());
			candidateDto.setCandidateStatus(status.getStatusName());
			candidateDto.setProfileId(candidate.getCandidateProfile().getId());
		}
		
		candidateDto.setCreatedBy(candidate.getCreatedBy());

		return candidateDto;
	}

}
