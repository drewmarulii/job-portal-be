package com.lawencon.jobportaladmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.jobportaladmin.dao.RoleDao;
import com.lawencon.jobportaladmin.dto.role.RoleResDto;
import com.lawencon.jobportaladmin.model.Role;

@Service
public class RoleService {

	@Autowired
	private RoleDao roleDao;

	public List<RoleResDto> getRoles() {
		final List<RoleResDto> rolesDto = new ArrayList<>();
		final List<Role> roles = roleDao.getAll(Role.class);
		
		
		for (int i = 0; i < roles.size(); i++) {
			if(!roles.get(i).getRoleCode().equals(com.lawencon.jobportaladmin.constant.Role.ADMIN.roleCode)) {
				final RoleResDto roleDto = new RoleResDto();
				roleDto.setId(roles.get(i).getId());
				roleDto.setRoleName(roles.get(i).getRoleName());
				roleDto.setRoleCode(roles.get(i).getRoleCode());
				rolesDto.add(roleDto);
			}
		}

		return rolesDto;
	}

}
