package com.revature.ers.services;

import java.util.List;

import com.revature.ers.dao.UserDao;
import com.revature.ers.models.User;

public class UserService {
	
	private UserDao userDao = new UserDao();
	
	
	public List<User> getAllUsers() {
		return userDao.getAll();
	}
	
	
	public User getUserByCredentials(String username, String password) {
		if (!username.equals("") && !password.equals("")) {
			return userDao.getByCredentials(username, password);
		}
		return null;
	}
	
	public User getUserByUsername(String username) {
		if (!username.equals("")) {
			return userDao.getByUsername(username);
		}
		return null;
	}
	
	//figure out way to get the current user and then that current user's id
	public User getUserById(int userId) {
		if (userDao.get)
	}

}
