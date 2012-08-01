/**
 * 
 */
package demo.model;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.solder.core.ExtensionManaged;

/**
 * @author Ian YT Tsai (Zanyking)
 * 
 */
@Singleton
public class EMFProducer implements Serializable{
	
//	@ExtensionManaged
//	@Produces
//	@PersistenceContext(unitName = "breakfast")
//	protected EntityManager em;
	
//	@ExtensionManaged
//	@Produces
//	@ConversationScoped
//    @PersistenceUnit(unitName = "breakfast")
//    EntityManagerFactory entityManagerFactory;
	
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
		return getEntityManagerFactory().createEntityManager();
	}
}
