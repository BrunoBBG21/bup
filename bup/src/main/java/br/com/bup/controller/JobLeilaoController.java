package br.com.bup.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.Public;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.quartzjob.CronTask;
import br.com.caelum.vraptor.validator.Validator;

/**
 * 	Job que executa de tempo em tempo afim de atualizar os leiloes...
 * 	Para mandar rodar o job deve pelo menos uma vez fazer um request para http://localhost:8080/bup/jobs/configure.
 *
 */
@Controller
public class JobLeilaoController extends BaseWeb implements CronTask {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobLeilaoController.class);
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected JobLeilaoController() {
		this(null, null, null, null);
	}
	
	@Inject
	public JobLeilaoController(Result result, Validator validator, ResourceBundle i18n, UsuarioSession usuarioSession) {
		super(result, validator, usuarioSession, i18n);
	}
	
	@Override
	@Public
	public void execute() {
		LOGGER.debug("Executando job...");
		
		result.redirectTo(IndexController.class).index(); //pq o maldito tiles tenta procurar uma jsp execute...
	}
	
	/**
	 * Coisa de louco... 
	 * veja http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-06 
	 */
	@Override
	public String frequency() {
		return "0/5 * * * * ?";
	}
}
