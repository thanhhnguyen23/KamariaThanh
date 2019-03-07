package com.revature.ers.services;

import java.util.List;

import javax.servlet.annotation.WebServlet;

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

	// used for UI validation
	public Boolean isUsernameAvailable(String username) {
		
		// if username is found, then username is NOT available
		if(userDao.getByUsername(username) == null) {
			return true;
		}
		// if username is found, then username IS AVAILABLE
		else {
			return false;
		}
	}	
	
	public User getUserByUsername(String username) {
		if(!username.equals("")) {
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
	
	public User addUser(User newUser) {
		/////////////////////////////////////////////////
		User oldUser = new User();
		
		String oldUsername = oldUser.getUsername();
		/////////////////////////////////////////////////

		if (newUser.getUsername().equals("") || 
				newUser.getPassword().equals("") || 
				newUser.getFirstName().equals("") || 
				newUser.getLastName().equals("")) {
			return null;
		}
		///////////////////////////////////////////////////////////////////////
		// check to see if new username is currently in the database
		if(newUser.getUsername().equals(userDao.getByUsername(oldUsername))){
			return null;
		}
		///////////////////////////////////////////////////////////////////////

		return userDao.add(newUser);
	}


}
