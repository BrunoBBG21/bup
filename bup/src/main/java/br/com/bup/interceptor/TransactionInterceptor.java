package br.com.bup.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.controller.IndexController;
import br.com.bup.dao.MidiaDAO;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.validator.Validator;

@Intercepts
@RequestScoped
public class TransactionInterceptor {
	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionInterceptor.class);
	private static final java.lang.Exception Exception = null; 
	
	@Inject
	private EntityManager entityManager;
	
	@Accepts
	public boolean accepts(ControllerMethod method) {
	    return method.containsAnnotation(OpenTransaction.class);
	}
	
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) throws Exception {
		LOGGER.debug("Abrindo uma transa�ao...");

		if (entityManager.getTransaction().isActive()) {
			LOGGER.debug("J� existe uma transa�ao em aberto...");
			
		} else {
			entityManager.getTransaction().begin();
		}
		
		try {
			stack.next(); // continua a execu��o
			LOGGER.debug("commit...");
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.debug("rollback...");
//			try{
				entityManager.getTransaction().rollback();
				throw e;
//			} catch (Exception ex) {
//				LOGGER.debug("Está em uso apague os vinculos primeiro.");
//			}
		}
	}
}
