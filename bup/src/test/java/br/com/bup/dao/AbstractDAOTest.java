package br.com.bup.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractDAOTest {
	protected static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bupTest");
	protected EntityManager entityManager;

	@Before
	public void before() throws Exception {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	@After
	public void after() throws Exception {
		if (entityManager != null) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}
}
