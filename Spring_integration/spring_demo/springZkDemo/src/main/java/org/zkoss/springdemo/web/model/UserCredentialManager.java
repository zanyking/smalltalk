package org.zkoss.springdemo.web.model;

import java.io.Serializable;

import org.zkoss.springdemo.model.UserDAO;
import org.zkoss.springdemo.model.bean.User;


/**
 * @author Ian YT Tsai(zanyking)
 * 
 *         This class provides a class which manages user authentication
 * 
 */
public class UserCredentialManager implements Serializable{

	private static final long serialVersionUID = 4789033910089502945L;

	public UserCredentialManager(){}
	
	private User user;

	private UserDAO userDao;
	
	public UserDAO getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public synchronized void login(String name, String password) {
		User tempUser = userDao.findUserByName(name);
		if (tempUser != null && tempUser.getPassword().equals(password)) {
			user = tempUser;
		} else {
			user = null;
		}
	}

	public synchronized void logOff() {
		this.user = null;
	}

	public synchronized User getUser() {
		return user;
	}

	public synchronized boolean isAuthenticated() {
		return user != null;
	}

}
