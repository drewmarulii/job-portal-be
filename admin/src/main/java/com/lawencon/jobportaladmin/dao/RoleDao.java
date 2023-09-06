package com.lawencon.jobportaladmin.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.base.ConnHandler;
import com.lawencon.jobportaladmin.model.Role;

@Repository
public class RoleDao extends AbstractJpaDao {

	private EntityManager em() {
		return ConnHandler.getManager();
	}

	@SuppressWarnings("unchecked")
	public List<Role> getByCode() {
		final StringBuilder sqlb = new StringBuilder();
			sqlb.append("SELECT ");
			sqlb.append(" tr.id, ");
			sqlb.append(" tr.role_name, ");
			sqlb.append("FROM ");
			sqlb.append(" t_role tr ");
			sqlb.append("WHERE ");
			sqlb.append(" tr.role_code != 'ADM'");
			
		final String sql = "SELECT tr.id, tr.role_name FROM t_role tr WHERE tr.role_code != 'ADM' ";

		final List<Role> roles = em().createNativeQuery(sqlb.toString(), Role.class).getResultList();
		return roles;

	}

}
