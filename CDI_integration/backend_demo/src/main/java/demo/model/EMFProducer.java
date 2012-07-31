/**
 * 
 */
package demo.model;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.solder.core.ExtensionManaged;

/**
 * @author Ian YT Tsai (Zanyking)
 * 
 */
@Named(value = "init")
public class EMFProducer implements Serializable {
//	@Produces
//	@RequestScoped
//	@PersistenceContext(unitName = "breakfast")
//	protected EntityManager em;
	
	@ExtensionManaged
    @Produces
    @PersistenceUnit(unitName = "breakfast")
//    @PersistenceContext(unitName = "breakfast")
    @RequestScoped
    EntityManagerFactory emf;
}
