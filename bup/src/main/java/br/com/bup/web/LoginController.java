package br.com.bup.web;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import br.com.bup.annotation.Public;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Usuario;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;

@Controller
public class LoginController {
	private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Inject
    private Result result;
	
	@Inject
	private UsuarioDAO dao;
	
	@Inject
	private UsuarioSession usuarioSession;
	
	@Public
	public void login(String email, String senha) {
		if (!Strings.isNullOrEmpty(email) && !Strings.isNullOrEmpty(senha)) {
			LOGGER.debug("Usuario '"+email+"' tentando logar...");
			Usuario usuario = dao.buscarPorEmailSenha(email, senha);
			
			if (usuario != null) {
				usuarioSession.logar(usuario);
				LOGGER.debug("Usuario '" + usuario.getId() + "' logado.");
				
			} else {
				result.include("msgErro", "Login ou/e Senha incorretos.");
			}
		}
	}
}