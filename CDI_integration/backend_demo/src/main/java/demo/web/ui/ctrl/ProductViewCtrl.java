package demo.web.ui.ctrl;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;

import demo.model.DAOs;
import demo.model.bean.Product;
import demo.web.OverQuantityException;

/**
 * @author zkessentials store
 * 
 *         This is the controller for the product view as referenced in
 *         index.zul
 * 
 */
public class ProductViewCtrl extends SelectorComposer<Div> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4327599559929787819L;

	@Wire
	private Grid prodGrid;

	@Override
	public void doAfterCompose(Div comp) throws Exception {
		super.doAfterCompose(comp);

		List<Product> prods = DAOs.getProductDAO().findAllAvailable();

		ListModelList<Product> prodModel = new ListModelList<Product>(prods);
		prodGrid.setModel(prodModel);
	}
	
	@Listen("onAddProductOrder=#PrdoDiv #prodGrid row productOrder")
	public void addProduct(Event fe) {

		if (!(fe.getTarget() instanceof ProductOrder)) {
			return;
		}

		ProductOrder po = (ProductOrder) fe.getTarget();

		try {
			UserUtils.getShoppingCart()
					.add(po.getProduct(), po.getQuantity());
		} catch (OverQuantityException e) {
			po.setError(e.getMessage());
		}

		BindUtils.postGlobalCommand(null, null, "updateShoppingCart", null);
	}
}
