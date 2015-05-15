package br.com.bup.controller.job;

import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.Public;
import br.com.bup.dao.LeilaoDAO;
import br.com.bup.domain.Leilao;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.LeilaoApplication;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.quartzjob.CronTask;
import br.com.caelum.vraptor.validator.Validator;

/**
 * Job que executa de tempo em tempo afim de atualizar os leiloes... Para mandar rodar o job deve pelo menos uma vez fazer um
 * request para http://localhost:8080/bup/jobs/configure.
 *
 */
@Controller
public class JobLeilaoController extends BaseWeb implements CronTask {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobLeilaoController.class);
	private final static String FREQUENCY = "0/5 * * * * ?"; //1 ciclo a cada 5 segundos
	private final static Long FREQUENCY_ATUALIZAR_LEILOES = 4L; //4 ciclos para atualizar
	private final static Long MAX_COUNT_FREQUENCY = Long.MAX_VALUE;
	
	private static Long countFrequency = 0L;
	
	private final LeilaoApplication leilaoApplication;
	private final LeilaoDAO leilaoDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected JobLeilaoController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public JobLeilaoController(Result result, Validator validator, ResourceBundle i18n, UsuarioSession usuarioSession,
			LeilaoApplication leilaoApplication, LeilaoDAO leilaoDAO) {
		super(result, validator, usuarioSession, i18n);
		this.leilaoApplication = leilaoApplication;
		this.leilaoDAO = leilaoDAO;
	}
	
	@Override
	@Public
	public void execute() {
		LOGGER.debug("Executando job...");
		
		if (countFrequency.equals(MAX_COUNT_FREQUENCY)) {
			countFrequency = 0L;
		} else {
			countFrequency++;
		}
		
		if (countFrequency % FREQUENCY_ATUALIZAR_LEILOES == 0) {
			atualizarLeilaoApplication();
		}
		
		leilaoApplication.removerLeiloesFinalizados();
		leilaoApplication.notificarObservers();
		
		result.nothing();
	}
	
	/**
	 * Atualiza a lista de leiloes em andamento do LeilaoApplication.
	 */
	private void atualizarLeilaoApplication() {
		List<Leilao> leiloes = leilaoDAO.buscarTodosEmAndamentoOuAguardando();
		leilaoApplication.atualizarLeiloes(leiloes);
	}
	
	/**
	 * Coisa de louco... veja http://www.quartz-scheduler.org/documentation/quartz-2.2.x/tutorials/tutorial-lesson-06
	 */
	@Override
	public String frequency() {
		return FREQUENCY;
	}
}
