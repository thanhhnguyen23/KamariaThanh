package com.revature.ers.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.ers.models.User;
import com.revature.ers.util.ConnectionFactory;

public class UserDao implements DAO<User>{

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getInstance().getConnection()){ //TODO -- need to create ConnectionFactory util
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM ers_users JOIN ers_users_role USING (ers_user_role_id)"); 

			
			rs.
			
		}
		catch

	}

	@Override
	public User getById(int id) {
		// TODO Auto-generated method stub
		return null;
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

}
