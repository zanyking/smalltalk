package demo.web.ui.ctrl;

import org.zkoss.zk.ui.event.Event;

public class ShoppingEvent extends Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6082843618140247037L;

	public ShoppingEvent(Type type) {
		super("onShopping");
		eventType = type;
	}
	
	public enum Type {
		CREATEORDER, ADDTOCART
	}
	
	private Type eventType;

	public Type getType() {
		return eventType;
	}
}