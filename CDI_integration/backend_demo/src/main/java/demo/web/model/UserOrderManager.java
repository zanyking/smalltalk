/**
 * 
 */
package demo.web.model;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import demo.model.OrderDAO;
import demo.model.bean.CartItem;
import demo.model.bean.Order;

/**
 * @author Ian YT Tsai (Zanyking)
 *
 */
@Named("userOrderManager")
@ApplicationScoped
public class UserOrderManager{


	@Inject
	private OrderDAO orderDao;
	
	@Inject
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

	
	
	
}
