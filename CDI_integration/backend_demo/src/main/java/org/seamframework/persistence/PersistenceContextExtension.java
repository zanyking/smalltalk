/* TestManager.java

	Purpose:
		
	Description:
		
	History:
		Jul 25, 2012, Created by Ian Tsai(Zanyking)

Copyright (C) 2010 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under ZOL in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.seamframework.persistence;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.Producer;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 * Support for managed persistence contexts in Java SE environment.
 * 
 * @author Gavin King
 */
public class PersistenceContextExtension implements Extension {

	private Bean<EntityManagerFactory> emfBean;

	/**
	 * For @PersistenceContext producer fields, make a bean for the EMF, then
	 * wrap the producer CDI creates, to get the EM from the EMF bean we made
	 * instead of trying to get it from the Java EE component environment.
	 */
	void processProducer(@Observes ProcessProducer<?, EntityManager> pp,
			final BeanManager bm) {

		if (pp.getAnnotatedMember().isAnnotationPresent(
				PersistenceContext.class)) {

			if (emfBean == null) {
				AnnotatedField<?> field = (AnnotatedField<?>) pp.getAnnotatedMember();
				final String unitName = 
					field.getAnnotation( PersistenceContext.class).unitName();
				
				final Class<?> module = field.getJavaMember().getDeclaringClass();
				final Set<Annotation> qualifiers = new HashSet<Annotation>();
				
				for (Annotation ann : field.getAnnotations()) {
					Class<? extends Annotation> annotationType = ann.annotationType();
					// if ( bm.isQualifier(annotationType)) {
					if (annotationType.isAnnotationPresent(Qualifier.class)) { // work
																				// around
																				// bug
																				// in
																				// Weld
						qualifiers.add(ann);
					}
				}
				if (qualifiers.isEmpty()) {
					qualifiers.add(new AnnotationLiteral<Default>() {});
				}
				qualifiers.add(new AnnotationLiteral<Any>() {});
				
				final boolean alternative = field.isAnnotationPresent(Alternative.class);
				final Set<Type> types = new HashSet<Type>() {
					{
						add(EntityManagerFactory.class);
						add(Object.class);
					}
				};

				// create a bean for the EMF
				emfBean = new Bean<EntityManagerFactory>() {
					@Override
					public Set<Type> getTypes() {
						return types;
					}

					@Override
					public Class<? extends Annotation> getScope() {
						return ApplicationScoped.class;
					}

					@Override
					public EntityManagerFactory create(
							CreationalContext<EntityManagerFactory> ctx) {
						return Persistence.createEntityManagerFactory(unitName);
					}

					@Override
					public void destroy(EntityManagerFactory emf,
							CreationalContext<EntityManagerFactory> ctx) {
						emf.close();
						ctx.release();
					}

					@Override
					public Class<?> getBeanClass() {
						return module;
					}

					@Override
					public Set<InjectionPoint> getInjectionPoints() {
						return Collections.EMPTY_SET;
					}

					@Override
					public String getName() {
						return null;
					}

					@Override
					public Set<Annotation> getQualifiers() {
						return qualifiers;
					}

					@Override
					public Set<Class<? extends Annotation>> getStereotypes() {
						return Collections.EMPTY_SET; // TODO!
					}

					@Override
					public boolean isAlternative() {
						return alternative;
					}

					@Override
					public boolean isNullable() {
						return false;
					}
				};

			} else {
				throw new RuntimeException(
						"Only one EMF per application is supported");
			}

			Producer<EntityManager> producer = new Producer<EntityManager>() {
				@Override
				public Set<InjectionPoint> getInjectionPoints() {
					return Collections.EMPTY_SET;
				}

				@Override
				public EntityManager produce(
						CreationalContext<EntityManager> ctx) {
					return getFactory(ctx).createEntityManager();
				}

				private EntityManagerFactory getFactory(
						CreationalContext<EntityManager> ctx) {
					return (EntityManagerFactory) bm.getReference(emfBean,
							EntityManagerFactory.class, ctx);
				}

				@Override
				public void dispose(EntityManager em) {
					if (em.isOpen()) // work around what I suspect is a bug in
										// Weld
					{
						em.close();
					}
				}
			};
			pp.setProducer(producer);
		}

	}

	/**
	 * Register the EMF bean with the container.
	 */
	void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
		abd.addBean(emfBean);
		System.out.println("finished the scanning process");

	}

	void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {

		System.out.println("beginning the scanning process");

	}

	<T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat) {

		System.out.println("scanning type: "
				+ pat.getAnnotatedType().getJavaClass().getName());

	}

}
