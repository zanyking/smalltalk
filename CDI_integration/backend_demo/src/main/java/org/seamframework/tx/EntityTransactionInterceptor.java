package org.seamframework.tx;

import java.io.Serializable;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

/**
 * Declarative JPA EntityTransactions
 * 
 * @author Gavin King
 */
@Interceptor
@Transactional
public class EntityTransactionInterceptor implements Serializable {
	private static final long serialVersionUID = 4422550967351153385L;
	
	@Inject
	@Any
	private EntityManager em;

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		boolean act = !em.getTransaction().isActive();
		if (act) {
			em.getTransaction().begin();
			System.out.println(">>> Transaction BEGIN");
		}
		try {
			Object result = ic.proceed();
			if (act) {
				em.getTransaction().commit();
				System.out.println(">>> Transaction COMMIT");
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			if (act) {
				em.getTransaction().rollback();
				System.out.println(">>> Transaction ROLLBACK ");
			}
			throw e;
		}
	}
	
	public void doInit(){
		
	}
}
