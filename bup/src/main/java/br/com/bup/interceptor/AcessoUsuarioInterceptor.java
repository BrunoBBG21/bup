package br.com.bup.interceptor;

import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.AnuncianteNaoGerenciado;
import br.com.bup.annotation.ApenasAdministrador;
import br.com.bup.annotation.ApenasAgencia;
import br.com.bup.annotation.ApenasAnunciante;
import br.com.bup.controller.IndexController;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.util.BaseWeb;
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
public class AcessoUsuarioInterceptor extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(AcessoUsuarioInterceptor.class);
	
	private boolean possuiApenasAnunciante = false;
	private boolean possuiApenasAgencia = false;
	private boolean possuiApenasAdministrador = false;
	private boolean possuiAnuncianteNaoGerenciado = false;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected AcessoUsuarioInterceptor() {
		this(null, null, null, null);
	}
	
	@Inject
	public AcessoUsuarioInterceptor(Result result, Validator validator, UsuarioSession usuarioSession, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
	}
	
	@Accepts
	public boolean accepts(ControllerMethod method) {
		possuiApenasAnunciante = method.containsAnnotation(ApenasAnunciante.class);
		possuiApenasAgencia = method.containsAnnotation(ApenasAgencia.class);
		possuiApenasAdministrador = method.containsAnnotation(ApenasAdministrador.class);
		possuiAnuncianteNaoGerenciado = method.containsAnnotation(AnuncianteNaoGerenciado.class);
		
		return usuarioSession.isLogado()
				&& (possuiApenasAnunciante || possuiApenasAgencia || possuiApenasAdministrador || possuiAnuncianteNaoGerenciado);
	}
	
	@AroundCall
	public void intercept(SimpleInterceptorStack stack) throws Exception {
		LOGGER.debug("Validando acesso...");
		Boolean apenasAnuncianteValido = Boolean.TRUE;
		Boolean apenasAgenciaValido = Boolean.TRUE;
		Boolean apenasAdministradorValido = Boolean.TRUE;
		Boolean anuncianteNaoGerenciadoValido = Boolean.TRUE;
		
		if (possuiApenasAnunciante) {
			apenasAnuncianteValido = validarApenasAnunciante();
		}
		if (possuiApenasAgencia) {
			apenasAgenciaValido = validarApenasAgencia();
		}
		if (possuiApenasAdministrador) {
			apenasAdministradorValido = validarApenasAdministrador();
		}
		if (possuiAnuncianteNaoGerenciado) {
			anuncianteNaoGerenciadoValido = validarAnuncianteNaoGerenciado();
		}
		
		if (apenasAnuncianteValido && apenasAgenciaValido && apenasAdministradorValido && anuncianteNaoGerenciadoValido) {
			stack.next(); // continua a execução
			
		} else {
			if (!apenasAnuncianteValido) {
				addInfoMsg("acesso.usuario.anunciante.invalido");
			}
			if (!apenasAgenciaValido) {
				addInfoMsg("acesso.usuario.agencia.invalido");
			}
			if (!apenasAdministradorValido) {
				addInfoMsg("acesso.usuario.administrador.invalido");
			}
			if (!anuncianteNaoGerenciadoValido) {
				addInfoMsg("acesso.usuario.nao_gerenciado.invalido");
			}
			
			LOGGER.debug("Redirecionando para a pagina index...");
			result.redirectTo(IndexController.class).index();
		}
	}
	
	private Boolean validarAnuncianteNaoGerenciado() {
		return !usuarioSession.isUsuarioLogadoGerenciado();
	}
	
	private Boolean validarApenasAdministrador() {
		return usuarioSession.isAdministrador();
	}
	
	private Boolean validarApenasAgencia() {
		return usuarioSession.getUsuarioLogado() instanceof Agencia;
	}
	
	private Boolean validarApenasAnunciante() {
		return usuarioSession.getUsuario() instanceof Anunciante;
	}
}
