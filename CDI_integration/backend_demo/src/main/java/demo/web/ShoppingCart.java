package demo.web;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import demo.model.CartitemDAO;
import demo.model.bean.CartItem;
import demo.model.bean.Product;
import demo.web.model.UserCredentialManager;

/**
 * @author Ian Y.T Tsai(zanyking)
 * 
 *         This class provides a representation of a shopping cart
 * 
 */
@Named("shoppingCart")
@SessionScoped
public class ShoppingCart {


	
	@Inject
	@RequestScoped
	private CartitemDAO cartitemDAO;
	
	@Inject
	private UserCredentialManager userCredentialManager;
	
	private Long getUserId(){
		return userCredentialManager.getUser().getId();
	}
	public List<CartItem> getItems() {
		
		return cartitemDAO.findByUser(getUserId());
	}

	public CartItem getItem(long prodId) {
		return cartitemDAO.findByProduct(getUserId(), prodId);
	}


	public void add(Product prod, int amount)
			throws OverQuantityException {
		
		CartItem cItem = this.getItem(prod.getId());
		validate(cItem, prod, amount);
		if (cItem == null) {
			cItem = new CartItem(getUserId(), prod);
		}
		cItem.add(amount);
		cartitemDAO.insertUpdate(cItem);
	}

	private static void validate(CartItem item, Product prod, int amount)
			throws OverQuantityException {
		int oriAmount = item == null ? 0 : item.getAmount();
		int total = oriAmount + amount;
		if (total > prod.getQuantity()) {
			String errMesg = "too much: " + oriAmount + " + " + amount + " > "
					+ prod.getQuantity();
			throw new OverQuantityException(errMesg);
		}
	}

	public void remove(CartItem cartItem) {
		cartitemDAO.delete(cartItem);
	}

	public void clear() {
		cartitemDAO.clear(getUserId());
	}

	public float getTotalPrice() {
		float subTotal = 0;
		for (CartItem item : getItems()) {
			subTotal += item.getProduct().getPrice() * item.getAmount();
		}
		return subTotal;
	}

}
