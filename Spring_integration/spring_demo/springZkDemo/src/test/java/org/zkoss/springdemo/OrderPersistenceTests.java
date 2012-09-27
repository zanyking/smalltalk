package org.zkoss.springdemo;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.springdemo.model.bean.Order;



@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderPersistenceTests {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Order order = new Order();
		entityManager.persist(order);
		entityManager.flush();
		assertNotNull(order.getId());
	}




}
