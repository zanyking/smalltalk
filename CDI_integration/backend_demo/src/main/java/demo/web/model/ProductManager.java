/**
 * 
 */
package demo.web.model;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import demo.model.ProductDAO;
import demo.model.bean.Product;

/**
 * @author Ian YT Tsai (Zanyking)
 *
 */
@Named("productManager")
@ApplicationScoped
public class ProductManager {

	
	@Inject
	@ApplicationScoped
	private ProductDAO productDao;

	public List<Product> findAllAvailable() {
		return productDao.findAllAvailable();
	}
	
	
	
	
}
