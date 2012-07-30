/**
 * 
 */
package demo.web.model;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
@SessionScoped
public class UserOrderManager implements Serializable{

	private static final long serialVersionUID = 7248193010849518118L;

	@Inject
	@RequestScoped
	transient 
	private OrderDAO orderDao;
	
	@Inject
	private UserCredentialManager userCredentialManager;

	public List<Order> findAll() {
		return orderDao.findByUser(userCredentialManager.getUser());
	}

	
	public void cancelOrder(Order order) {
		orderDao.cancelOrder(order);
	}


	public Order createOrder(List<CartItem> cartItems, String orderNote) {
		return orderDao.createOrder(userCredentialManager.getUser(), cartItems, orderNote);
	}

	
	
	
}
