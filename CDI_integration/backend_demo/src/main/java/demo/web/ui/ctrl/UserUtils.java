package demo.web.ui.ctrl;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import demo.web.ShoppingCart;

public class UserUtils {
	
	
	public static ShoppingCart getShoppingCart() {
		Session session = Executions.getCurrent().getSession();
		ShoppingCart cart = (ShoppingCart) session.getAttribute("ShoppingCart");
		if (cart == null) {
			session.setAttribute("ShoppingCart", cart = new ShoppingCart());
		}
		return cart;
	}
}
