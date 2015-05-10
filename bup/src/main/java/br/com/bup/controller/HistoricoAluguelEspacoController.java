package br.com.bup.controller;

import java.util.Date;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.AnuncianteDAO;
import br.com.bup.dao.EspacoPropagandaDAO;
import br.com.bup.dao.HistoricoAluguelEspacoDAO;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.HistoricoAluguelEspaco;
import br.com.bup.domain.Usuario;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class HistoricoAluguelEspacoController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(HistoricoAluguelEspacoController.class);
	
	private final HistoricoAluguelEspacoDAO historicoAluguelEspacoDAO;
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	private final AnuncianteDAO anuncianteDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected HistoricoAluguelEspacoController() {
		this(null, null, null, null, null, null, null);
	}
	
	@Inject
	public HistoricoAluguelEspacoController(Result result, Validator validator,
			HistoricoAluguelEspacoDAO historicoAluguelEspacoDAO, EspacoPropagandaDAO espacoPropagandaDAO,
			AnuncianteDAO anuncianteDAO, UsuarioSession usuarioSession, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.historicoAluguelEspacoDAO = historicoAluguelEspacoDAO;
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.anuncianteDAO = anuncianteDAO;
	}
	
	public void formulario() {
		LOGGER.debug("carregando formulario de conta");
		result.include("anunciantes", anuncianteDAO.buscarTodos());
		result.include("espacos", espacoPropagandaDAO.buscarTodos());
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}
	
	@OpenTransaction
	public void criar(@NotNull Date dataInicio, @NotNull Date dataFim, @NotNull Anunciante anunciante,
			@NotNull EspacoPropaganda espacoPropaganda) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando historico espaco propaganda:data inicio - " + dataInicio + ",data  fim - " + dataFim
				+ ", anunciante - " + anunciante.getNome() + ", espaco propaganda - " + espacoPropaganda.getDescricao());
		Usuario logado = usuarioSession.getUsuario();
		if (!("admin".equals(logado.getNome()) && logado.getId() == 1)) {
			addErrorMsg("msg.error.admin");
			validator.onErrorRedirectTo(this).formulario();
		} else {
			HistoricoAluguelEspaco historicoAluguelEspaco = new HistoricoAluguelEspaco();
			historicoAluguelEspaco.setAnunciante(anunciante);
			historicoAluguelEspaco.setEspacoPropaganda(espacoPropaganda);
			historicoAluguelEspaco.setDataInicio(dataInicio);
			historicoAluguelEspaco.setDataFim(dataFim);
			// validacoes...
			validarCriar(historicoAluguelEspaco);
			validator.onErrorRedirectTo(this).formulario();
			
			// salva
			historicoAluguelEspaco = historicoAluguelEspacoDAO.salvar(historicoAluguelEspaco);
			
			result.include("success", "historico aluguel de espaco da propaganda criada com sucesso.");
			result.redirectTo(IndexController.class).index();
		}
	}
	
	private void validarCriar(HistoricoAluguelEspaco historicoAluguelEspaco) {
		validator.validate(historicoAluguelEspaco);
		
		// TODO validar inclusao repetida
	}
}
