package demo.model;

import static demo.model.bean.Order.CANCELED;

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
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.seamframework.tx.Transactional;

import demo.model.bean.CartItem;
import demo.model.bean.Order;
import demo.model.bean.OrderItem;
import demo.model.bean.Product;

/**
 * @author zkessentials store
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

	public List<Order> findByUser(long userId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Order> criteria = cb.createQuery(Order.class);

		Root<Order> r = criteria.from(Order.class);
		criteria.select(r).where(cb.equal(r.get("userId"), userId));
		
		TypedQuery<Order> q = em.createQuery(criteria); 
		
		return q.getResultList();
	}

	
	private void add(Order order) {
		em.persist(order);
	}

	@Transactional
	public Order createOrder(long userId, List<CartItem> items,
			String description) {

		Order order = new Order(null, userId, Order.PROCESSING, new Date(),
				description);

		this.add(order);

		for (CartItem item : items) {
			Product prod = item.getProduct();

			OrderItem oItem = new OrderItem(null, prod.getId(), prod.getName(),
					prod.getPrice(), item.getAmount());

			em.persist(oItem);
			order.addItem(oItem);
		}

		em.persist(order);


		return order;
	}

	public Order findById(long orderId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = cb.createQuery(Order.class);

		Root<Order> r = criteria.from(Order.class);
		criteria.select(r).where(cb.equal(r.get("orderId"), orderId));
		
		TypedQuery<Order> q = em.createQuery(criteria);
		
		return q.getSingleResult();
	}
	
	@Transactional
	public Order cancelOrder(long orderId) {
		Order order = findById(orderId);
		order.setStatus(CANCELED);
		em.persist(order);
		return order;
	}

}
