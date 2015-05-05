package br.com.bup.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAdministrador;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.PublicoAlvoDAO;
import br.com.bup.domain.PublicoAlvo;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class PublicoAlvoController {
	private final static Logger LOGGER = LoggerFactory.getLogger(PublicoAlvoController.class);
	
	private final Result result;
	private final Validator validator;
	private final PublicoAlvoDAO publicoAlvoDAO;
	private final UsuarioSession usuarioSession;
	private final ResourceBundle i18n;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected PublicoAlvoController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public PublicoAlvoController(Result result, Validator validator, PublicoAlvoDAO publicoAlvoDAO,
			UsuarioSession usuarioSession, ResourceBundle i18n) {
		this.result = result;
		this.validator = validator;
		this.publicoAlvoDAO = publicoAlvoDAO;
		this.usuarioSession = usuarioSession;
		this.i18n = i18n;
	}
	
	@OpenTransaction
	public void formulario() {
		LOGGER.debug("carregando formulario de publico alvo.");
	}
	
	@OpenTransaction
	public List<PublicoAlvo> listar() {
		LOGGER.debug("Listando os publicos alvos. ");
		
		return publicoAlvoDAO.buscarTodos();
	}
	
	@OpenTransaction
	public void criar(@NotNull PublicoAlvo publicoAlvo) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando publico alvo: " + publicoAlvo);
		
		// validacoes...
		validarCriar(publicoAlvo);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		publicoAlvo = publicoAlvoDAO.salvar(publicoAlvo);
		
		result.include("success", "Publico alvo criado com sucesso.");
		result.redirectTo(IndexController.class).index();
	}
	
	private void validarCriar(PublicoAlvo publicoAlvo) {
		validator.validate(publicoAlvo);
	}
	
	@Path("/publicoAlvo/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		publicoAlvoDAO.apagarPorId(id);
		
		result.include("success", i18n.getString("msg.success.apagar"));
		result.redirectTo(this).listar();
	}
}
