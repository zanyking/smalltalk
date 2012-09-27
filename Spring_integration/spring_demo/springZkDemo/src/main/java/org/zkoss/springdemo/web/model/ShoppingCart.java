/**
 * 
 */
package org.zkoss.springdemo.web.model;

import java.util.List;

import org.zkoss.springdemo.model.bean.CartItem;
import org.zkoss.springdemo.model.bean.Product;
import org.zkoss.springdemo.web.OverQuantityException;

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
