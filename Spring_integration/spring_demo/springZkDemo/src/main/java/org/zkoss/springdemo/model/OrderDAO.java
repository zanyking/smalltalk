package org.zkoss.springdemo.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.springdemo.model.bean.CartItem;
import org.zkoss.springdemo.model.bean.Order;
import org.zkoss.springdemo.model.bean.OrderItem;
import org.zkoss.springdemo.model.bean.Product;
import org.zkoss.springdemo.model.bean.User;


/**
 * @author Ian Y.T Tsai(zanyking)
 * 
 *         This class provides functionality to access the {@code Order} model
 *         storage system
 */
@Repository
//@Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class OrderDAO {
	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Order> findAll() {
		Query query = em.createQuery("from orders");
		List<Order> orders = query.getResultList();
		return orders;
	}
	
	public List<Order> findByUser(User user) {
		if(user==null)return new ArrayList<Order>();
		return findByUser(user.getId());
	}
	
	public List<Order> findByUser(long userId) {
		return Querys.findEquals(Order.class, "userId", userId, em);
	}
	
	@Transactional(value="txManager", propagation=Propagation.REQUIRES_NEW)
	public Order createOrder(User user, List<CartItem> items, String description) {
		Order order = new Order(null, user.getId(), Order.PROCESSING, new Date(), description);
		em.persist(order);	
		System.out.println("createOrder persised: "+order+" id="+order.getId());
		for (CartItem item : items) {
			Product prod = item.getProduct();
			OrderItem oItem = new OrderItem(null, 
					order.getId(), 
					prod.getId(), 
					prod.getName(),
					prod.getPrice(), 
					item.getAmount());
			em.persist(oItem);
			System.out.println("oItem persised: "+oItem+" id="+oItem.getId());
			order.addItem(oItem);
		}
		return order;
	}

	public Order findById(Long orderId) {
		return Querys.findSingle(Order.class, "orderId", orderId, em);
	}
	
	@Transactional(value="txManager", propagation=Propagation.REQUIRES_NEW)
	public Order cancelOrder(final Order order) {
		if(order==null)return null;
		order.setStatus(Order.CANCELED);
		return em.merge(order);
	}
	
}
