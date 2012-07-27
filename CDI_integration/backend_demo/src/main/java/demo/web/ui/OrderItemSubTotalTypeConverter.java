package demo.web.ui;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import demo.model.bean.OrderItem;

/**
 * @author zkessentials store
 * 
 *         This class provides a type converter for an {@code OrderItem}. This
 *         converter takes the OrderItem object and converts it into better
 *         representation for a user
 * 
 */
public class OrderItemSubTotalTypeConverter implements Converter {

	public Object coerceToBean(Object arg0, Component arg1, BindContext arg2) {
		throw new UnsupportedOperationException();
	}

	public Object coerceToUi(Object arg0, Component arg1, BindContext arg2) {
		OrderItem item = (OrderItem) arg0;
		String subTotal = "$ " + (item.getQuantity() * item.getPrice());
		return subTotal;
	}

}
