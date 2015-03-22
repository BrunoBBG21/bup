package br.com.bup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstractTest {
	protected static EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;

	@BeforeClass
	public static void beforeClass() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("bupTest");
	}
	
	@Before
	public void before() throws Exception {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
	}

	@After
	public void after() throws Exception {
		if ( entityManager != null ) {
			entityManager.getTransaction().rollback();
			entityManager.close();
		}
	}
}
