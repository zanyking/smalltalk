/**
 * 
 */
package demo.web.model;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import demo.model.bean.CartItem;
import demo.model.bean.Product;
import demo.web.OverQuantityException;

/**
 * @author Ian YT Tsai(zanyking)
 *
 */
public interface ShoppingCart {

	public String getDescription();
	public void setDescription(String description);
	
	public List<CartItem> getItems();
	public void add(Product prod, int amount) throws OverQuantityException;
	public void remove(CartItem cartItem);
	public void clear();
	public float getTotalPrice();
}
