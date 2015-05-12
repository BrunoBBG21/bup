package br.com.bup.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.Public;
import br.com.bup.controller.LoginController;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

@Intercepts(before = { TransactionInterceptor.class, AcessoUsuarioInterceptor.class })
@RequestScoped
public class LoginInterceptor {
	private final static Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Inject
	private UsuarioSession usuarioSession;
	
	@Inject
	private Result result;
	
	@Accepts
	public boolean accepts(ControllerMethod method) {
		Boolean job = method.getController().toString().contains("QuartzController");
		
		return !job && !method.containsAnnotation(Public.class) && !usuarioSession.isLogado();
	}
	
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) throws Exception {
		LOGGER.debug("Redirecionando para a pagina de login...");
		result.redirectTo(LoginController.class).login(null, null);
		// stack.next(); // continua a execução
	}
}
