package demo.web.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import demo.model.UserDAO;
import demo.model.bean.User;

/**
 * @author Ian YT Tsai(zanyking)
 * 
 *         This class provides a class which manages user authentication
 * 
 */
@Named("userCredentialManager")
@SessionScoped
public class UserCredentialManager implements Serializable{

	private static final long serialVersionUID = 4789033910089502945L;

	public UserCredentialManager(){}
	
	private User user;

	@Inject
	@RequestScoped
	transient private UserDAO userDao;
	
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
