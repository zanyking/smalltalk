package demo.web.ui.ctrl;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;

import demo.model.bean.Product;
import demo.web.OverQuantityException;
import demo.web.model.ProductManager;
import demo.web.model.ShoppingCartSessionImpl;

/**
 * @author Ian YT Tsai(zanyking)
 * 
 *         This is the controller for the product view as referenced in
 *         index.zul
 * 
 */
@VariableResolver(org.zkoss.zkplus.cdi.DelegatingVariableResolver.class)
public class ProductViewCtrl extends SelectorComposer<Div> {

	
	@WireVariable
	private ProductManager productManager;
	
	@WireVariable
	private ShoppingCartSessionImpl shoppingCart;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4327599559929787819L;

	@Wire
	private Grid prodGrid;

	@Override
	public void doAfterCompose(Div comp) throws Exception {
		super.doAfterCompose(comp);

		List<Product> prods = productManager.findAllAvailable();

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
			shoppingCart.add(po.getProduct(), po.getQuantity());
		} catch (OverQuantityException e) {
			po.setError(e.getMessage());
		}

		BindUtils.postGlobalCommand(null, null, "updateShoppingCart", null);
	}
}
