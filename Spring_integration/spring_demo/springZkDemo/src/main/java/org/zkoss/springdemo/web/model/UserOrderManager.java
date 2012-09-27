/**
 * 
 */
package org.zkoss.springdemo.web.model;

import java.util.List;

import org.zkoss.springdemo.model.OrderDAO;
import org.zkoss.springdemo.model.bean.CartItem;
import org.zkoss.springdemo.model.bean.Order;

/**
 * @author Ian YT Tsai (Zanyking)
 *
 */
public class UserOrderManager{


	private OrderDAO orderDao;
	
	private UserCredentialManager userCredentialManager;

	public List<Order> findAll() {
		return orderDao.findByUser(userCredentialManager.getUser());
	}

	
	public Order cancelOrder(Order order) {
		return orderDao.cancelOrder(order);
	}


	public Order createOrder(List<CartItem> cartItems, String orderNote) {
		return orderDao.createOrder(userCredentialManager.getUser(), cartItems, orderNote);
	}


	public OrderDAO getOrderDao() {
		return orderDao;
	}


	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}


	public UserCredentialManager getUserCredentialManager() {
		return userCredentialManager;
	}


	public void setUserCredentialManager(UserCredentialManager userCredentialManager) {
		this.userCredentialManager = userCredentialManager;
	}

	
	
	
}
