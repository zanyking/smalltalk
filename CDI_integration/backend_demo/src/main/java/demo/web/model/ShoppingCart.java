package demo.web.model;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import demo.model.CartitemDAO;
import demo.model.bean.CartItem;
import demo.model.bean.Product;
import demo.web.OverQuantityException;

/**
 * @author Ian Y.T Tsai(zanyking)
 * 
 *         This class provides a representation of a shopping cart
 * 
 */
@Named("shoppingCart")
@SessionScoped
public class ShoppingCart implements Serializable{

	private static final long serialVersionUID = 3017413864798939685L;

	@Inject
	@RequestScoped
	transient
	private CartitemDAO cartitemDAO;
	
	@Inject
	private UserCredentialManager userCredentialManager;
	
	private Long getUserId(){
		if(!userCredentialManager.isAuthenticated())
			throw new UnAuthenticatedException();
		return userCredentialManager.getUser().getId();
	}
	public List<CartItem> getItems() {
		List<CartItem> citems = cartitemDAO.findByUser(getUserId());
		System.out.println(">>>>> shoppingcart getItems: size="+citems.size());
		return citems;
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
		System.out.println(">>>> cartitemDAO.insertUpdate: "+cItem);
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
