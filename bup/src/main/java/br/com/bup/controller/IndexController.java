package br.com.bup.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.Public;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class IndexController {
	private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
	
    private final Result result;
	private final Validator validator;
	private final UsuarioSession usuarioSession;
	
	/**
     * @deprecated CDI eyes only
     */
	protected IndexController() {
		this(null, null, null);
	}
	
	@Inject
	public IndexController(Result result, Validator validator, UsuarioSession usuarioSession) {
		this.result = result;
		this.validator = validator;
		this.usuarioSession = usuarioSession;
	}
	
	@Path("/")
	@Public
	public void index() {
		
	}
}
