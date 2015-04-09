package br.com.bup.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAgencia;
import br.com.bup.annotation.ApenasAnunciante;
import br.com.bup.controller.IndexController;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.Validator;

@Intercepts(before = { TransactionInterceptor.class })
@RequestScoped
public class AcessoUsuarioInterceptor {
	private final static Logger LOGGER = LoggerFactory.getLogger(AcessoUsuarioInterceptor.class);

	@Inject
	private UsuarioSession usuarioSession;
	
	@Inject
	private Result result;
	
	@Inject
	private Validator validator;
	
	private boolean possuiApenasAnunciante = false;
	private boolean possuiApenasAgencia = false;

	@Accepts
	public boolean accepts(ControllerMethod method) {
	    possuiApenasAnunciante = method.containsAnnotation(ApenasAnunciante.class);
	    possuiApenasAgencia = method.containsAnnotation(ApenasAgencia.class);

	    return usuarioSession.isLogado() 
	    		&& (possuiApenasAnunciante && !(usuarioSession.getUsuario() instanceof Anunciante)
	    			|| possuiApenasAgencia && !(usuarioSession.getUsuarioLogado() instanceof Agencia));
	}
	
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) throws Exception {
		LOGGER.debug("Redirecionando para a pagina index...");
		
		if (possuiApenasAnunciante) {
			validator.add(new I18nMessage("info", "acesso.usuario.anunciante.invalido", Severity.INFO));
			
		} else if (possuiApenasAgencia) {
			validator.add(new I18nMessage("info", "acesso.usuario.agencia.invalido", Severity.INFO));
		}
		
		result.redirectTo(IndexController.class).index();
//		stack.next(); // continua a execução
	}
}
