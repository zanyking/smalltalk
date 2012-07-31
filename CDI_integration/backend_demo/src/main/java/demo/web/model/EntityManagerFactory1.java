/* TransactionManager.java

	Purpose:
		
	Description:
		
	History:
		Jul 31, 2012, Created by Ian Tsai(Zanyking)

Copyright (C) 2010 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under ZOL in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package demo.web.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import demo.model.CartitemDAO;

/**
 * @author Ian Y.T Tsai(zanyking)
 *
 */
@Named("emFactory")
@SessionScoped
public class EntityManagerFactory1 implements Serializable {

	@Inject
	@RequestScoped
	transient private EntityManager em;
	
	@Inject
	@RequestScoped
	transient
	private CartitemDAO cartitemDAO;
	
	public EntityManager getEntityManager(){
		EntityManager em1 = cartitemDAO.getEntityManager();
		System.out.println("compare em1 and em: "+em1+" : "+em);
		return em1;
	}
}
