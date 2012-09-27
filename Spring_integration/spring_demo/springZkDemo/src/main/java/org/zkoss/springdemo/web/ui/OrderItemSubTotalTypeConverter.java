package org.zkoss.springdemo.web.ui;

import java.text.DecimalFormat;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.springdemo.model.bean.OrderItem;
import org.zkoss.zk.ui.Component;

/**
 * @author Ian YT Tsai(zanyking)
 * 
 *         This class provides a type converter for an {@code OrderItem}. This
 *         converter takes the OrderItem object and converts it into better
 *         representation for a user
 * 
 */
@SuppressWarnings("rawtypes")
public class OrderItemSubTotalTypeConverter implements Converter {

	public Object coerceToBean(Object arg0, Component arg1, BindContext arg2) {
		throw new UnsupportedOperationException();
	}

	public Object coerceToUi(Object arg0, Component arg1, BindContext arg2) {
		OrderItem item = (OrderItem) arg0;
		DecimalFormat df = new DecimalFormat("###,##0.0");
		String subTotal = "$ " + df.format(item.getQuantity() * item.getPrice());
		return subTotal;
	}

}
