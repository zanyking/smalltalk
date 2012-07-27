package demo.web.ui.ctrl;

import java.util.HashMap;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Label;
import org.zkoss.zul.Spinner;

import demo.model.bean.Product;

public class ProductOrder extends Cell implements IdSpace, AfterCompose {

	/**
	 * 
	 */
	private static final long serialVersionUID = 237399685255201046L;

	@Wire
	Spinner spnQuantity;

	@Wire
	Label lblError;

	public void afterCompose() {
		// 1. Render the template
		Executions.createComponents("/WEB-INF/composite/productorder.zul",
				this, new HashMap<String, Object>() {
					private static final long serialVersionUID = 7141348964577773718L;

					{
						put("maximumQuantity", getMaximumQuantity());
					}
				});

		// 2. Wire variables, components and event listeners (optional)
		Selectors.wireVariables(this, this, null);
		Selectors.wireComponents(this, this, false);
		Selectors.wireEventListeners(this, this);
	}

	private int maximumQuantity;
	private Product product;
	
	@Listen("onClick=#btnAdd")
	public void addProduct() {
		Events.postEvent(this, new AddProductOrderEvent());
	}

	public int getMaximumQuantity() {
		return maximumQuantity;
	}

	public void setMaximumQuantity(int maximumQuantity) {
		this.maximumQuantity = maximumQuantity;
	}

	public int getQuantity() {
		return spnQuantity.getValue();
	}

	public void setError(String error) {
		lblError.setValue(error);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	// Customize Event
	public static final String ON_ADD_PRODUCTORDER = "onAddProductOrder";

	// Editable Event which contains the content of title and description
	public class AddProductOrderEvent extends Event {
		/**
		 * 
		 */
		private static final long serialVersionUID = -9166887434130928894L;

		public AddProductOrderEvent() {
			super(ON_ADD_PRODUCTORDER, ProductOrder.this);
		}
	}

}
