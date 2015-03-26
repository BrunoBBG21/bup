package br.com.bup.web;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private HttpServletRequest request; 

	@Inject
	private UsuarioDAO dao;
	
	@Inject
	private UsuarioSession usuarioSession;
	
	public void login(String email, String senha) {
		Usuario usuario = dao.buscarPorEmailSenha(email, senha);
		
		if (usuario != null) {
//			request.getSession().setAttribute("Usuario", usuario);
			usuarioSession.setUsuario(usuario);
		}
	}
}