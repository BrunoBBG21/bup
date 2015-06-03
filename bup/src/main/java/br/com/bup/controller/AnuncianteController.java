package br.com.bup.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.AnuncianteDAO;
import br.com.bup.domain.Anunciante;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class AnuncianteController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(AnuncianteController.class);
	
	private final AnuncianteDAO anuncianteDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected AnuncianteController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public AnuncianteController(Result result, Validator validator, UsuarioSession usuarioSession, ResourceBundle i18n,
			AnuncianteDAO anuncianteDAO) {
		super(result, validator, usuarioSession, i18n);
		this.anuncianteDAO = anuncianteDAO;
	}
	
	@OpenTransaction
	public List<Anunciante> listar() {
		LOGGER.debug("Listando os anunciantes. ");
		
		return anuncianteDAO.buscarTodos();
	}
}
