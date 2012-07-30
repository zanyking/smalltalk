package demo.web.ui.ctrl;

import java.util.HashMap;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;

import demo.model.bean.CartItem;
import demo.web.ShoppingCart;
/**
 * 
 * @author Ian Y.T Tsai(zanyking)
 *
 */
public class ShoppingCartViewModel {
	
	private String orderNote;
	private CartItem selectedItem;
	
	
	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public CartItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(CartItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public List<CartItem> getCartItems() {
		return UserUtils.getShoppingCart().getItems();
	}
	
	public ShoppingCart getShoppingCart() {
		return UserUtils.getShoppingCart();
	}
	
	@Command
	@NotifyChange({"cartItems", "shoppingCart", "orderNote"})
	public void submit() {
		HashMap<String, Object> args = new HashMap<String, Object>();
		args.put("cartItems", getCartItems());
		args.put("orderNote", getOrderNote());
		BindUtils.postGlobalCommand(null, null, "submitNewOrder", args);
		clearShoppingCart();
	}
	
	@Command
	@NotifyChange({"cartItems", "shoppingCart"})
	public void clearShoppingCart() {
		getShoppingCart().clear();
	}
	
	@Command
	@NotifyChange({"cartItems", "shoppingCart"})
	public void removeCartitem(@BindingParam("cartItem") CartItem cartItem) {
		getShoppingCart().remove(cartItem);
	}
	
	@GlobalCommand
	@NotifyChange({"cartItems", "shoppingCart"})
	public void updateShoppingCart() {
		//no post processing to be done
	}
	
	public String formateMoney(Object item){
		return "formated String!!!";
	}
}
