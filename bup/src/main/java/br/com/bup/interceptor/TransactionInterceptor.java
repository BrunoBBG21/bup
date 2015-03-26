package br.com.bup.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

@Intercepts
@RequestScoped
public class TransactionInterceptor {
	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionInterceptor.class); 
	
	@Inject
	private EntityManager entityManager;

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) throws Exception {
		LOGGER.debug("Abrindo uma transaçao...");
		
		entityManager.getTransaction().begin();
		
		try {
			stack.next(); // continua a execução
			LOGGER.debug("commit...");
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.debug("rollback...");
			entityManager.getTransaction().rollback();
			throw e;
		}
	}
}
