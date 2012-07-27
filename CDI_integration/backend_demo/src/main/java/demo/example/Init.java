/**
 * 
 */
package demo.example;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ian YT Tsai (Zanyking)
 * 
 */
@Named(value = "init")
@ApplicationScoped
public class Init implements Serializable {
	@Produces
	@ApplicationScoped
	@PersistenceContext(unitName = "foo")
	EntityManager em;
}
