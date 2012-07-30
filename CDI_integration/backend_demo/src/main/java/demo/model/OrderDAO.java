package demo.model;

import static demo.model.bean.Order.CANCELED;

import java.util.ArrayList;
import java.util.Date;
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
import demo.model.bean.Order;
import demo.model.bean.OrderItem;
import demo.model.bean.Product;
import demo.model.bean.User;

/**
 * @author Ian Y.T Tsai(zanyking)
 * 
 *         This class provides functionality to access the {@code Order} model
 *         storage system
 * 
 */
@Dependent
@Named(value = "orderDao")
public class OrderDAO {


	@Inject
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Order> criteria = cb.createQuery(Order.class);

		Root<Order> r = criteria.from(Order.class);
		criteria.select(r).where(cb.equal(r.get("userId"), userId));
		
		TypedQuery<Order> q = em.createQuery(criteria); 
		
		return q.getResultList();
	}

	
	public Order createOrder(User user, List<CartItem> items, String description) {

		Order order = new Order(
				null, user.getId(), Order.PROCESSING, new Date(), description);
		em.getTransaction().begin();
		try{
			em.persist(order);	
		}finally{
			em.getTransaction().commit();
		}
		
		System.out.println("createOrder persised: "+order+" id="+order.getId());
		em.getTransaction().begin();
		try{
			for (CartItem item : items) {
				Product prod = item.getProduct();
	
				OrderItem oItem = new OrderItem(null, order.getId(), prod.getId(), prod.getName(),
						prod.getPrice(), item.getAmount());
				
				em.persist(oItem);
				System.out.println("oItem persised: "+oItem+" id="+oItem.getId());
				order.addItem(oItem);
			}
		}finally{
			em.getTransaction().commit();
		}

		return order;
	}
	
	

	public Order findById(Long orderId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = cb.createQuery(Order.class);

		Root<Order> r = criteria.from(Order.class);
		criteria.select(r).where(cb.equal(r.get("orderId"), orderId));
		
		TypedQuery<Order> q = em.createQuery(criteria);
		
		return q.getSingleResult();
	}
	
	@Transactional
	public Order cancelOrder(Order order) {
		if(order==null)return null;
		order.setStatus(CANCELED);
		em.persist(order);
		return order;
	}

}
