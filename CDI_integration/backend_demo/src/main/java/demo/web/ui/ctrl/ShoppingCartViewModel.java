package demo.web.ui.ctrl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;

import demo.model.DAOs;
import demo.model.bean.CartItem;
import demo.web.ShoppingCart;

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
	public void submitOrder() {
		DAOs.getOrderDAO().createOrder(UserUtils.getCurrentUserId(), getCartItems(), getOrderNote());
		clearOrders();
	}
	
	@Command
	@NotifyChange({"cartItems", "shoppingCart"})
	public void clearOrders() {
		getShoppingCart().clear();
	}
	
	@Command
	@NotifyChange({"cartItems", "shoppingCart"})
	public void deleteOrder(@BindingParam("cartItem") CartItem cartItem) {
		getShoppingCart().remove(cartItem.getProduct().getId());
	}
	
	@GlobalCommand
	@NotifyChange("cartItems")
	public void updateShoppingCart() {
		//no post processing to be done
	}
}
