package com.revature.ers.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.models.Role;
import com.revature.ers.util.ConnectionFactory;

import oracle.jdbc.OracleTypes;

public class RoleDao implements DAO<Role>{
	
	private static Logger log = Logger.getLogger(RoleDao.class);

	@Override
	public List<Role> getAll() {
		List<Role> roles = new ArrayList<>();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "{ CALL get_all_roles(?) }";
			
			CallableStatement cstate = conn.prepareCall(sql);
			cstate.registerOutParameter(1, OracleTypes.CURSOR);
			
			cstate.execute();
			
			ResultSet rs = (ResultSet)cstate.getObject(1);
			roles = this.mapResultSet(rs);
			
		}
		catch(SQLException e) {
			log.error(e.getMessage());
			
		}
		return roles;
	}
	@Override
	public Role getById(int roleId) {
		Role role = new Role();
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			
			PreparedStatement pstate = conn.prepareStatement("SELECT * FROM ers_user_roles WHERE ers_user_role_id = ?");
			pstate.setInt(1, roleId);


			ResultSet rs = pstate.executeQuery();
			
			rs.next(); 

			List<Role> roles = this.mapResultSet(rs);
			if (roles.isEmpty()) 
				role = null;
			else 
				role = roles.get(0);

		}
		catch(SQLException e) {
			log.error(e.getMessage());
		}
		if(role.getRoleId() == 0) {
			return null;
		}
		return role;
	}

	private List<Role> mapResultSet(ResultSet rs) throws SQLException{
		List<Role> roles = new ArrayList<>();
		while(rs.next()) {
			Role role = new Role();
			
			role.setRoleId(rs.getInt("ers_user_role_id")); 
			role.setRoleName(rs.getString("user_role"));
			
			roles.add(role);
		}
		return roles;
	}

	// unimplemented methods
	@Override
	public Role add(Role obj) {
		return null;
	}
	@Override
	public Role update(Role updatedObj) {
		return null;
	}
	@Override
	public boolean delete(int id) {
		return false;
	}
}
