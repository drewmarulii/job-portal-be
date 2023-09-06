package com.lawencon.jobportaladmin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.File;
import com.lawencon.jobportaladmin.model.Profile;
import com.lawencon.jobportaladmin.model.Role;
import com.lawencon.jobportaladmin.model.User;

@Repository
public class UserDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	public User getByUsername(String email) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" tu.id, ");
			sqlb.append(" tu.user_password, ");
			sqlb.append(" tu.is_active, "); 
			sqlb.append(" tu.profile_id, ");
			sqlb.append(" tp.full_name, ");
			sqlb.append(" tr.role_code, ");
			sqlb.append(" tp.photo_id ");
			sqlb.append("FROM ");
			sqlb.append(" t_user tu ");
			sqlb.append("INNER JOIN ");
			sqlb.append(" t_role tr ON tr.id = tu.role_id ");
			sqlb.append("INNER JOIN " );
			sqlb.append(" t_profile tp ON tp.id = tu.profile_id ");
			sqlb.append("WHERE ");
			sqlb.append(" tu.user_email = :email");
			
		final String sql = "SELECT tu.id ,"
				+ " tu.user_password, "
				+ " tu.is_active, " 
				+ "	tu.profile_id, "
				+ " tp.full_name, "
				+ " tr.role_code,"
				+ " tp.photo_id "
				+ " FROM t_user tu "
				+ " INNER JOIN t_role tr ON tr.id = tu.role_id "
				+ " INNER JOIN t_profile tp ON tp.id = tu.profile_id " 
				+ " WHERE tu.user_email = :email ";

			final Object user = em().createNativeQuery(sqlb.toString()).setParameter("email", email).getSingleResult();

			final Object[] userArr = (Object[]) user;
			User userGet = null;

			if (userArr.length > 0) {
				userGet = new User();
				userGet.setId(userArr[0].toString());
				userGet.setUserPassword(userArr[1].toString());
				userGet.setIsActive(Boolean.valueOf(userArr[2].toString()));
				
				final Profile profile = new Profile();
				profile.setId(userArr[3].toString());
				profile.setFullName(userArr[4].toString());
				
				final File photo = new File();
				photo.setId(userArr[6].toString());
				
				profile.setPhoto(photo);
				userGet.setProfile(profile);
				
				final Role role = new Role();
				role.setRoleCode(userArr[5].toString());
				userGet.setRole(role);
	
			}
			
			return userGet;
	}

	public List<User> getByRoleCode(String roleCode) {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" tu.id, ");
			sqlb.append(" tu.user_email, ");
			sqlb.append(" tu.is_active, ");
			sqlb.append(" tu.profile_id, ");
			sqlb.append(" tp.full_name, ");
			sqlb.append(" tr.role_name, ");
			sqlb.append(" tr.role_code, ");
			sqlb.append(" tp.photo_id ");
			sqlb.append("FROM ");
			sqlb.append(" t_user tu ");
			sqlb.append("INNER JOIN ");
			sqlb.append(" t_role tr ON tr.id = tu.role_id ");
			sqlb.append("INNER JOIN ");
			sqlb.append(" t_profile tp ON tp.id = tu.profile_id ");
			sqlb.append("WHERE ");
			sqlb.append(" tr.role_code = :roleCode");
			
		final String sql = "SELECT tu.id," + " tu.user_email," + " tu.is_active, " + "	tu.profile_id, "
				+ " tp.full_name, " + "	tr.role_name," + " tr.role_code," + " tp.photo_id " + " FROM t_user tu"
				+ " INNER JOIN t_role tr ON tr.id = tu.role_id " + " INNER JOIN t_profile tp ON tp.id = tu.profile_id "
				+ " WHERE tr.role_code = :roleCode";

		final List<?> userObjs = em().createNativeQuery(sqlb.toString()).setParameter("roleCode", roleCode).getResultList();

		final List<User> users = new ArrayList<>();
		if (userObjs.size() > 0) {
			for (Object userObj : userObjs) {
				final Object[] userObjArr = (Object[]) userObj;
				final User userGet = new User();
				userGet.setId(userObjArr[0].toString());
				userGet.setUserEmail(userObjArr[1].toString());
				userGet.setIsActive(Boolean.valueOf(userObjArr[2].toString()));

				final Profile profile = new Profile();
				profile.setId(userObjArr[3].toString());
				profile.setFullName(userObjArr[4].toString());

				final Role role = new Role();
				role.setRoleName(userObjArr[5].toString());
				role.setRoleCode(userObjArr[6].toString());
				userGet.setRole(role);

				final File photo = new File();
				photo.setId(userObjArr[7].toString());

				profile.setPhoto(photo);
				userGet.setProfile(profile);

				users.add(userGet);
			}
		}

		return users;
	}

}
