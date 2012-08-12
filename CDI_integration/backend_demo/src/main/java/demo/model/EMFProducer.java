/**
 * 
 */
package demo.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * @author Ian YT Tsai (Zanyking)
 * 
 */
@Singleton
public class EMFProducer implements Serializable{
	private static final long serialVersionUID = -5026045312064002123L;
	
	
	private static EntityManagerFactory factory;

	@Produces
	public EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("breakfast");
		}
		return factory;
	}
	
	@Produces
	@RequestScoped
	public EntityManager produceEntityManager() {
		System.out.println(">>>> EMFProducer: EntityManager created.");
		return getEntityManagerFactory().createEntityManager();
	}
}
