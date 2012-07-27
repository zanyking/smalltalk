package demo.model;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.seamframework.tx.Transactional;


import demo.model.bean.Order;
import demo.model.bean.Product;

/**
 * @author zkessentials store
 * 
 *         This class provides functionality to access the {@code Product} model
 *         storage system
 * 
 */
@Dependent
@Named(value = "productDAO")
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
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		Root<Product> r = criteria.from(Product.class);
		criteria.select(r).where(cb.equal(r.get("available"), true));
		
		TypedQuery<Product> q = em.createQuery(criteria); 
        List<Product> products = q.getResultList();
        return products;
	}
	
	
	private Product findById(long productId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		Root<Product> r = criteria.from(Product.class);
		criteria.select(r).where(cb.equal(r.get("id"), productId));
		
		TypedQuery<Product> q = em.createQuery(criteria); 
		Product product = q.getSingleResult();
		
		return product;
	}
	

	@Transactional
	public Product remove(long productId) {
		Product product = findById(productId);
        if(product != null) {
            product.setAvailable(false);
            em.persist(product);
        }
        return product;
	}

	public Product putOn(long productId) {
		Product product = findById(productId);
        if(product != null) {
            product.setAvailable(true);
            em.persist(product);
        }

        return product;
	}

}

