package demo.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import demo.model.bean.Product;
import demo.model.bean.User;

/**
 * @author zkessentials store
 * 
 *         This class provides functionality to access the {@code User} model
 *         storage system
 * 
 */
public class UserDAO {
//	private static final Map<Long, User> dbModel = Collections
//			.synchronizedMap(new HashMap<Long, User>());
//	public static final User USER1 = new User(1L, "ian", "ian", "user");
//	public static final User USER2 = new User(2L, "zk", "zk", "admin");
//	public static final User USER3 = new User(3L, "tom", "tom", "user");
//	static {
//		dbModel.put(USER1.getId(), USER1);
//		dbModel.put(USER2.getId(), USER2);
//		dbModel.put(USER3.getId(), USER3);
//	}
	
	
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
