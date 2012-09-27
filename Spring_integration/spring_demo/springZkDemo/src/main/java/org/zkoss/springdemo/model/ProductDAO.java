package org.zkoss.springdemo.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.springdemo.model.bean.Product;


/**
 * @author zkessentials store
 * 
 *         This class provides functionality to access the {@code Product} model
 *         storage system
 * 
 */
@Repository
@Scope("request")
public class ProductDAO {
	@PersistenceContext
	EntityManager em;
	
	public ProductDAO(){}
	
	
	public ProductDAO(EntityManager em) {
		this.em = em;
	}

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
	
	@Transactional(value="txManager", propagation=Propagation.REQUIRES_NEW)
	public Product remove(final long productId) {
		Product product = findById(productId);
        if(product != null) {
            product.setAvailable(false);
            em.persist(product);
        }
        return product;
	}

	@Transactional(value="txManager", propagation=Propagation.REQUIRES_NEW)
	public Product putOn(final long productId) {
		Product product = findById(productId);
        if(product != null) {
            product.setAvailable(true);
            em.persist(product);
        }
        return product;
	}
	
}

