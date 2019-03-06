package com.revature.ers.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.RoleDao;
import com.revature.ers.models.Role;

public class RoleService {

	private RoleDao roleDao = new RoleDao();
	
	private Logger log = Logger.getLogger(RoleService.class);
	
	
	public List<Role> getAllRoles(){
		log.info("getting all roles");
		return roleDao.getAll();
	}
	
	public Role getRoleById(int roleId) {
		log.info("getting role by Id");
		return roleDao.getById(roleId);
	}
	
}
