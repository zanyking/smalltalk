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
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import demo.model.Querys.Invocation;
import demo.model.bean.CartItem;
import demo.model.bean.Product;

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
			where(cb.and(
					cb.equal(r.get( "userId"), userId), 
					cb.equal(r.get( "product"), prodId))).
			orderBy(cb.asc(r.get("createDate")));
		
		TypedQuery<CartItem> q = em.createQuery(criteria);
		try{
			return q.getSingleResult();	
		}catch(NoResultException e){
			return null;
		}
	}
	
	
	
	public void insertUpdate(final CartItem citem){
		Querys.transact(new Invocation<CartItem>() {
			public CartItem invoke(EntityManager em) {
				
		        em.persist(citem);
		        return citem;
			}
		}, em);
	}
	
	public void delete(final CartItem citem){
		Querys.transact(new Invocation<CartItem>() {
			public CartItem invoke(EntityManager em) {
				
				em.remove(citem);
		        return citem;
			}
		}, em);
		
	}
	public void clear(final Long userId){
		Querys.transact(new Invocation<CartItem>() {
			public CartItem invoke(EntityManager em) {
				Query query = em.createQuery("DELETE FROM CartItem c WHERE c.userId = :userId");
				query.setParameter("userId", userId);
				System.out.println(">>>> CartitemDAO clear() em: "+em);
				int deleted = query.executeUpdate();
				System.out.println(">>>>  DELETE FROM cartitems c WHERE " +
						"c.userId = "+userId+"  deleted:"+deleted);
				
		        return null;
			}
		}, em);
	}
	
}
