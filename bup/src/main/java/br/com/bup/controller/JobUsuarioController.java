package br.com.bup.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.Public;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioApplication;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.quartzjob.CronTask;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class JobUsuarioController extends BaseWeb implements CronTask {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobUsuarioController.class);
	
	private final UsuarioApplication usuarioApplication;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected JobUsuarioController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public JobUsuarioController(Result result, Validator validator, ResourceBundle i18n, UsuarioSession usuarioSession,
			UsuarioApplication usuarioApplication) {
		super(result, validator, usuarioSession, i18n);
		this.usuarioApplication = usuarioApplication;
	}
	
	@Override
	@Public
	public void execute() {
		LOGGER.debug("Executando job...");
		
		usuarioApplication.deslogarLogadosOciosos();
		
		result.nothing();
	}
	
	/**
	 * Coisa de louco... veja
	 * http://www.quartz-scheduler.org/documentation/quartz
	 * -2.2.x/tutorials/tutorial-lesson-06
	 */
	@Override
	public String frequency() {
		return "0 0/5 * * * ?";
	}
}
