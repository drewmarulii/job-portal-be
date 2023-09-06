package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.constant.PersonTypes;
import com.lawencon.jobportaladmin.dao.FileDao;
import com.lawencon.jobportaladmin.dao.PersonTypeDao;
import com.lawencon.jobportaladmin.dao.ProfileDao;
import com.lawencon.jobportaladmin.dao.RoleDao;
import com.lawencon.jobportaladmin.dao.UserDao;
import com.lawencon.jobportaladmin.dto.InsertResDto;
import com.lawencon.jobportaladmin.dto.UpdateResDto;
import com.lawencon.jobportaladmin.dto.changepassword.ChangePasswordReqDto;
import com.lawencon.jobportaladmin.dto.login.LoginReqDto;
import com.lawencon.jobportaladmin.dto.login.LoginResDto;
import com.lawencon.jobportaladmin.dto.profile.ProfileResDto;
import com.lawencon.jobportaladmin.dto.profile.ProfileUpdateReqDto;
import com.lawencon.jobportaladmin.dto.user.UserInsertReqDto;
import com.lawencon.jobportaladmin.dto.user.UsersResDto;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.PersonType;
import com.lawencon.jobportaladmin.model.Profile;
import com.lawencon.jobportaladmin.model.Role;
import com.lawencon.jobportaladmin.model.User;
import com.lawencon.jobportaladmin.util.GenerateCode;
import com.lawencon.security.principal.PrincipalService;

@Service
public class UserService implements UserDetailsService {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PersonTypeDao personTypeDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PrincipalService<String> principalService;

	public LoginResDto login(LoginReqDto loginData) {
		final User user = userDao.getByUsername(loginData.getUserEmail());
		final LoginResDto loginRes = new LoginResDto();

		if (!user.getIsActive()) {
			loginRes.setMessage("Akun anda nonaktif");
			return loginRes;
		} else {
			loginRes.setUserId(user.getId());
			loginRes.setFullName(user.getProfile().getFullName());
			loginRes.setProfileId(user.getProfile().getId());
			loginRes.setRoleCode(user.getRole().getRoleCode());
			loginRes.setPhotoId(user.getProfile().getPhoto().getId());
		}

		return loginRes;
	}

	public UpdateResDto changePassword(ChangePasswordReqDto data) {
		final UpdateResDto resDto = new UpdateResDto();
		User user = userDao.getById(User.class, principalService.getAuthPrincipal());
		try {
			em().getTransaction().begin();
			if (passwordEncoder.matches(data.getOldPassword(), user.getUserPassword())) {
				final String encodedPassword = passwordEncoder.encode(data.getNewPassword());
				user.setUserPassword(encodedPassword);
				user = userDao.save(user);
				resDto.setVersion(user.getVersion());
				resDto.setMessage("Update Password Success");
				em().getTransaction().commit();
			} else {
				em().getTransaction().rollback();
				resDto.setMessage("Update Password Failed");
				throw new RuntimeException("Password lama tidak sama");
			}

		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return resDto;
	}

	public InsertResDto registerUser(UserInsertReqDto userData) {
		final InsertResDto insertResDto = new InsertResDto();

		try {
			em().getTransaction().begin();
			if(userData.getPhotoName().isBlank()) {
				throw new NullPointerException("File is Empty");
			}
			User newUser = new User();

			newUser.setUserEmail(userData.getUserEmail());

			final Role role = roleDao.getById(Role.class, userData.getRoleId());
			newUser.setRole(role);

			final String generatePassword = GenerateCode.generateCode();
			final String encodedPassword = passwordEncoder.encode(generatePassword);

			newUser.setUserPassword(encodedPassword);

			final PersonType personType = personTypeDao.getByCode(PersonTypes.EMPLOYEE.typeCode);

			Profile profile = new Profile();
			profile.setFullName(userData.getFullName());
			profile.setAddress(userData.getAddress());
			profile.setPhoneNumber(userData.getPhoneNumber());
			profile.setPersonType(personType);

			if(userData.getPhotoName() !=null && userData.getExtensionName()!=null) {
				File photo = new File();
				photo.setFileName(userData.getPhotoName());
				photo.setFileExtension(userData.getExtensionName());
				photo = fileDao.save(photo);
				profile.setPhoto(photo);
			}
			
			profile = profileDao.save(profile);
			newUser.setProfile(profile);
			newUser = userDao.save(newUser);

			final String emailSubject = "JobRoad Account Registration";
			
			emailService.sendEmailNewUser(emailSubject, newUser, generatePassword);

			insertResDto.setId(newUser.getId());
			insertResDto.setMessage("Insert User Success");

			em().getTransaction().commit();
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
		}

		return insertResDto;
	}

	public List<UsersResDto> getAll() {
		final List<UsersResDto> usersDto = new ArrayList<>();
		final List<User> usersDb = userDao.getAll(User.class);

		for (int i = 0; i < usersDb.size(); i++) {
			final UsersResDto userDto = new UsersResDto();
			userDto.setId(usersDb.get(i).getId());
			userDto.setFullName(usersDb.get(i).getProfile().getFullName());
			userDto.setRole(usersDb.get(i).getRole().getRoleName());
			userDto.setEmail(usersDb.get(i).getUserEmail());
			userDto.setRole(usersDb.get(i).getRole().getRoleName());
			usersDto.add(userDto);
		}
		return usersDto;
	}

	public List<UsersResDto> getUsersByRoleCode(String roleCode) {
		final List<UsersResDto> usersDto = new ArrayList<>();
		final List<User> usersDb = userDao.getByRoleCode(roleCode);

		for (int i = 0; i < usersDb.size(); i++) {
			final UsersResDto userDto = new UsersResDto();
			userDto.setId(usersDb.get(i).getId());
			userDto.setFullName(usersDb.get(i).getProfile().getFullName());
			userDto.setRole(usersDb.get(i).getRole().getRoleName());
			userDto.setEmail(usersDb.get(i).getUserEmail());
			userDto.setRole(usersDb.get(i).getRole().getRoleName());
			usersDto.add(userDto);
		}
		return usersDto;
	}

	public ProfileResDto getProfile(String id) {
		final Profile profile = profileDao.getById(Profile.class, id);
		final ProfileResDto res = new ProfileResDto();
		res.setAddress(profile.getAddress());
		res.setFullName(profile.getFullName());
		res.setPhoneNumber(profile.getPhoneNumber());
		res.setPhoto(profile.getPhoto().getId());
		return res;
	}

	public UpdateResDto updateProfile(ProfileUpdateReqDto data) {
		UpdateResDto updateResDto = null;
		try {
			em().getTransaction().begin();
			final Profile profile = profileDao.getById(Profile.class, data.getId());
			profile.setFullName(data.getFullName());
			profile.setAddress(data.getAddress());
			profile.setPhoneNumber(data.getPhoneNumber());

			final File oldFile = fileDao.getById(File.class, profile.getPhoto().getId());
			final File file = new File();
			file.setFileName(data.getFileName());
			file.setFileExtension(data.getFileExtension());
			file.setCreatedBy(principalService.getAuthPrincipal());
			profile.setPhoto(file);
			fileDao.save(file);
			profileDao.saveAndFlush(profile);
			fileDao.deleteById(File.class, oldFile.getId());
			updateResDto = new UpdateResDto();
			updateResDto.setMessage("Update Berhasil");
			updateResDto.setVersion(profile.getVersion());
			em().getTransaction().commit();
			return updateResDto;
		} catch (Exception e) {
			em().getTransaction().rollback();
			e.printStackTrace();
			return null;
		}

	}

	public UsersResDto getById(String id) {
		final User user = userDao.getById(User.class, id);
		final UsersResDto userDto = new UsersResDto();
		userDto.setId(user.getId());
		userDto.setProfileId(user.getProfile().getId());
		userDto.setFullName(user.getProfile().getFullName());
		userDto.setRole(user.getRole().getRoleName());
		userDto.setEmail(user.getUserEmail());
		userDto.setRole(user.getRole().getRoleName());
		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userDao.getByUsername(username);

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(username, user.getUserPassword(),
					new ArrayList<>());
		}

		throw new UsernameNotFoundException("Email / Password salah");
	}

}