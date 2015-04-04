package br.com.bup.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractControllerTest {
	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractControllerTest.class);
	protected static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bupTest");
	protected EntityManager entityManager;

	@Before
	public void before() throws Exception {
		LOGGER.debug("criando entityManager...");
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	@After
	public void after() throws Exception {
		if (entityManager != null) {
			if (entityManager.getTransaction().isActive()) {
				LOGGER.debug("rollback...");
				entityManager.getTransaction().rollback();
			}
			if (entityManager.isOpen()) {
				LOGGER.debug("fechando entityManager...");
				entityManager.close();
			}
		}
	}
}