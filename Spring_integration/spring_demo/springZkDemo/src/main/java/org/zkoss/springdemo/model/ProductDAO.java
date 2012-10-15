package org.zkoss.springdemo.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.springdemo.model.bean.Product;


/**
 * @author Ian YT Tsai(Zanyking)
 * This class provides functionality to access the {@code Product} model storage system
 * 
 */
@Repository
//@Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ProductDAO {
	@PersistenceContext
	private EntityManager em;
	
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
		System.out.println("ProductDAO's EM:"+em);
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

