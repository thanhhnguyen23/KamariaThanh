package com.revature.ers.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.ers.dao.UserDao;
import com.revature.ers.models.User;

public class UserService {
	
	private UserDao userDao = new UserDao();
	private static Logger log = Logger.getLogger(UserService.class);
	
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
	
	//TODO -- look at bookstore v3 for inspiration
	//figure out way to get the current user and then that current user's id

	//////////////////////////////
	// things to consider per WS
	//awt - filter
	//////////////////////////////
	public User getUserById(int userId) {

		return userDao.getById(userId);
	}
	

}
