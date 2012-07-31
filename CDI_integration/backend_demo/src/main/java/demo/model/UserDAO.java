package demo.model;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import demo.model.bean.User;

/**
 * @author zkessentials store
 * 
 *         This class provides functionality to access the {@code User} model
 *         storage system
 * 
 */
@Dependent
@Named(value = "userDao")
public class UserDAO{
	
	
	@Inject
	EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
        Query query = em.createQuery("from users");
        List<User> users = query.getResultList();
        return users;
	}

	public User findById(long id) {
		return Querys.findSingle(User.class, "id", id, em);
	}

	public User findUserByName(String name) {
        return Querys.findSingle(User.class, "name", name, em);
	}


}
