package demo.model;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import demo.model.Querys.Invocation;
import demo.model.bean.Product;

/**
 * @author zkessentials store
 * 
 *         This class provides functionality to access the {@code Product} model
 *         storage system
 * 
 */
@RequestScoped
@Named(value = "productDao")
public class ProductDAO {

	@Inject
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Product> findAll() {
        Query query = em.createQuery("from products");
        List<Product> products = query.getResultList();
        return products;
	}

	public List<Product> findAllAvailable() {
        return Querys.findEquals(Product.class, "available", true, em);
	}
	
	
	private Product findById(long productId){
		return Querys.findSingle(Product.class, "id", productId, em);
	}
	

	public Product remove(final long productId) {
		return Querys.transact(new Invocation<Product>() {
			public Product invoke(EntityManager em) {
				Product product = findById(productId);
		        if(product != null) {
		            product.setAvailable(false);
		            em.persist(product);
		        }
		        return product;
			}
		}, em);
	}

	public Product putOn(final long productId) {
		return Querys.transact(new Invocation<Product>() {
			public Product invoke(EntityManager em) {
				Product product = findById(productId);
		        if(product != null) {
		            product.setAvailable(true);
		            em.persist(product);
		        }
		        return product;
			}
		}, em);

		
		
	}
	
}

