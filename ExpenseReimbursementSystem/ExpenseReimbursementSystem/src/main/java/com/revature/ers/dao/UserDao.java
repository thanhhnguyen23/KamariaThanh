package com.revature.ers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.models.User;
import com.revature.ers.util.ConnectionFactory;

public class UserDao implements DAO<User>{
	
	private static Logger log = Logger.getLogger(UserDao.class);

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getInstance().getConnection()){ //TODO -- need to create ConnectionFactory util
			ResultSet rs = conn.createStatement().executeQuery(
					"SELECT * FROM ers_users JOIN ers_users_role USING (ers_user_role_id)"
					); 

			users = this.mapResultSet(rs);
		} catch (SQLException e){
			log.error(e.getMessage());

		}
		return users;
	}

	@Override
	public User getById(int userId) {
		User user = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			//TODO - PreparedStatement
			PreparedStatement pstate = conn.prepareStatement( "SELECT * FROM ers_users JOIN ers_users_role USING (ers_user_role_id) WHERE ers_user_id = ?");
			pstate.setInt(1, userId);
			
			ResultSet rs = pstate.executeQuery();
			List<User> users = this.mapResultSet(rs);
			
			if(!users.isEmpty()) {
				user = users.get(0);
				user.setPassword("************");
			}
		}
		catch(SQLException e) {
			log.error(e.getMessage());
		}
		return user;
	}

	public User getByUsername(String username) {
		User user = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			PreparedStatement pstate = conn.prepareStatement("SELECT * FROM ers_users JOIN ers_users_role USING (ers_user_role_id) WHERE ers_username = ?");
			pstate.setString(1, username);

			List<User> users = this.mapResultSet(pstate.executeQuery());
			
			if(users.isEmpty()) {
				user = null;
			}
			else {
				user = users.get(0);
			}
		}
		catch(SQLException e) {
			log.error(e.getMessage());
		}
		return user;
	}

	public User getByCredentials(String username, String password) {
		User user = null;

		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			PreparedStatement pstate = conn.prepareStatement("SELECT * FROM ers_users JOIN ers_users_role USING (ers_user_role_id) WHERE ers_username = ? AND ers_password = ?");
			pstate.setString(1, username);
			pstate.setString(2, password);

			List<User> users = this.mapResultSet(pstate.executeQuery());
			
			if(users.isEmpty()) {
				user = null;
			}
			else {
				user = users.get(0);
			}
		}
		catch(SQLException e) {
			log.error(e.getMessage());
		}
		return user;
	}

	private List<User> mapResultSet(ResultSet rs) throws SQLException{
		List<User> users = new ArrayList<>();
		while(rs.next()) {
			User user = new User();
			user.setUserId(rs.getInt("ers_user_id"));
			user.setUsername(rs.getString("ers_username"));
			user.setPassword(rs.getString("ers_password"));
			user.setFirstName(rs.getString("user_first_name"));
			user.setLastName(rs.getString("user_last_name"));
			user.setEmail(rs.getString("user_email"));
			user.setRoleId(rs.getInt("user_role_id"));
			
			users.add(user);
		}
		return users;
	}

	// unimplemented methods 
	@Override
	public User add(User obj) {
		return null;
	}

	@Override
	public User update(User updatedObj) {
		return null;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}
}