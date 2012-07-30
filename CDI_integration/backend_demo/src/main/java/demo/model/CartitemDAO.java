/* CartitemDAO.java

	Purpose:
		
	Description:
		
	History:
		Jul 30, 2012, Created by Ian Tsai(Zanyking)

Copyright (C) 2010 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under ZOL in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package demo.model;

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

import org.seamframework.tx.Transactional;

import demo.model.bean.CartItem;

/**
 * @author Ian Y.T Tsai(zanyking)
 *
 */
@Dependent
@Named(value = "cartitemDao")
public class CartitemDAO {

	@Inject
	EntityManager em;
	
	public List<CartItem> findByUser(Long userId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<CartItem> criteria = cb.createQuery(CartItem.class);

		Root<CartItem> r = criteria.from(CartItem.class);
		criteria.
			select(r).
			where(cb.equal(r.get( "userId"), userId)).
			orderBy(cb.asc(r.get("createDate")));
		
		TypedQuery<CartItem> q = em.createQuery(criteria); 
		return q.getResultList();
	}
	
	
	public CartItem findByProduct(Long userId, Long prodId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<CartItem> criteria = cb.createQuery(CartItem.class);

		Root<CartItem> r = criteria.from(CartItem.class);
		criteria.
			select(r).
			where(cb.and(cb.equal(r.get( "userId"), userId), 
					cb.equal(r.get( "prodId"), prodId))).
			orderBy(cb.asc(r.get("createDate")));
		
		TypedQuery<CartItem> q = em.createQuery(criteria); 
		return q.getSingleResult();
	}
	
	@Transactional
	public void insertUpdate(CartItem citem){
		em.persist(citem);
	}
	
	@Transactional
	public void delete(CartItem citem){
		em.remove(citem);
	}
	@Transactional
	public void clear(Long userId){
		Query query = em.createQuery("DELETE FROM cartitems c WHERE c.userId = :userId");
		query.setParameter("userId", userId);
		int deleted = query.executeUpdate();
		System.out.println(">>>>  DELETE FROM cartitems c WHERE " +
				"c.userId = "+userId+"  result:"+deleted);
		
	}
	
}
