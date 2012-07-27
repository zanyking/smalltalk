package demo.web;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import demo.model.bean.User;

/**
 * @author zkessentials store
 * 
 *         This class provides a class which manages user authentication
 * 
 */
public class UserCredentialManager {

	private static final String KEY_USER_MODEL = UserCredentialManager.class
			.getName()
			+ "_MODEL";
	private User user;

	private UserCredentialManager() {
	}

	public static UserCredentialManager getIntance() {
		Session session = Executions.getCurrent().getSession();
		synchronized (session) {
			UserCredentialManager userModel = (UserCredentialManager) session
					.getAttribute(KEY_USER_MODEL);
			if (userModel == null) {
				session.setAttribute(KEY_USER_MODEL,
						userModel = new UserCredentialManager());
			}
			return userModel;
		}
	}

	public synchronized void login(String name, String password) {
		User tempUser = DAOs.getUserDAO().findUserByName(name);
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
