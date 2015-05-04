package br.com.bup.controller;

import java.util.List;

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
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;
@Controller
public class PublicoAlvoController {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(PublicoAlvoController.class);

	private final Result result;
	private final Validator validator;
	private final PublicoAlvoDAO publicoAlvoDAO;
	private final UsuarioSession usuarioSession;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected PublicoAlvoController() {
		this(null, null, null, null);
	}
	@Inject
	public PublicoAlvoController(Result result, Validator validator,
			PublicoAlvoDAO publicoAlvoDAO, 
			UsuarioSession usuarioSession) {
		this.result = result;
		this.validator = validator;
		this.publicoAlvoDAO = publicoAlvoDAO;
		this.usuarioSession = usuarioSession;
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
		validator.onErrorRedirectTo(this).formulario(); //caso seja null...
		LOGGER.debug("criando publico alvo: " + publicoAlvo);
		
		//validacoes...
		validarCriar(publicoAlvo);
        validator.onErrorRedirectTo(this).formulario();		
		
        //salva
        publicoAlvoDAO.salvar(publicoAlvo);
		
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
		try {
			publicoAlvoDAO.apagarPorId(id);
			result.redirectTo(this).listar();
		} catch (Exception e) {
			validator.add(new I18nMessage("Público Alvo", "msg.error.apagar")).onErrorRedirectTo(this).listar();
		}
		
	}
}
