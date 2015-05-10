package br.com.bup.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.Public;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class IndexController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected IndexController() {
		this(null, null, null, null);
	}
	
	@Inject
	public IndexController(Result result, Validator validator, UsuarioSession usuarioSession, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
	}
	
	@Path("/")
	@Public
	public void index() {
		LOGGER.debug("index");
	}
}
