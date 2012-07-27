package demo.web.ui;

import java.text.SimpleDateFormat;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

import demo.model.bean.Order;

/**
 * @author zkessentials store
 * 
 *         This class provides a type converter for an {@code Order}. This
 *         converter takes the Order object and converts it into better
 *         representation for a user
 * 
 */
public class OrderInfoTypeConverter implements Converter {
	
	//save
	public Object coerceToBean(Object arg0, Component arg1, BindContext arg2) {
		throw new UnsupportedOperationException();
	}
	
	//load
	public Object coerceToUi(Object arg0, Component arg1, BindContext arg2) {
		Order order = (Order) arg0;
		String info = order.getStatus()
				+ " : "
				+ new SimpleDateFormat("yyyy/MM/dd hh:mm").format(order
						.getCreateDate());
		return info;
	}

}
