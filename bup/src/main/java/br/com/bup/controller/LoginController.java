package br.com.bup.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.annotation.Public;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Usuario;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

import com.google.common.base.Strings;

@Controller
public class LoginController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	private UsuarioDAO dao;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected LoginController() {
		this(null, null, null, null, null);
	}
	@Inject
	public LoginController(Result result, Validator validator, UsuarioSession usuarioSession, ResourceBundle i18n, UsuarioDAO dao) {
		super(result, validator, usuarioSession, i18n);
		this.dao = dao;
	}
	
	@Public
	@OpenTransaction
	public void login(String email, String senha) {
		if (!Strings.isNullOrEmpty(email) && !Strings.isNullOrEmpty(senha)) {
			LOGGER.debug("Usuario '" + email + "' tentando logar...");
			Usuario usuario = dao.buscarPorEmailSenha(email, senha);
			
			if (usuario != null) {
				usuarioSession.logar(usuario);
				LOGGER.debug("Usuario '" + usuario.getId() + "' logado.");
				result.redirectTo(IndexController.class).index();
			} else {
				result.include("msgErro", "Login ou/e Senha incorretos.");
			}
		} else if (usuarioSession.isLogado()) {
			result.redirectTo(IndexController.class).index();
		}
	}
	
	public void logout() {
		usuarioSession.deslogar();
		result.redirectTo(IndexController.class).index();
	}
}