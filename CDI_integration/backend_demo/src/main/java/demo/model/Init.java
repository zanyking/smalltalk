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
import javax.persistence.PersistenceContext;

/**
 * @author Ian YT Tsai (Zanyking)
 * 
 */
@Named(value = "init")
@Dependent
public class Init implements Serializable {
	@Produces
	@Dependent
	@PersistenceContext(unitName = "breakfast")
	EntityManager em;
}
