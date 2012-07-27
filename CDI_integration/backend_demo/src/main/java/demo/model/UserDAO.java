package demo.model;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> r = criteria.from(User.class);
		criteria.select(r).where(cb.equal(r.get("id"), id));
		
		TypedQuery<User> q = em.createQuery(criteria); 
		User user = q.getSingleResult();

        return user;
	}

	public User findUserByName(String name) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		Root<User> r = criteria.from(User.class);
		criteria.select(r).where(cb.equal(r.get("name"), name));
		
		TypedQuery<User> q = em.createQuery(criteria); 
		User user = q.getSingleResult();
		
        return user;
	}

}
