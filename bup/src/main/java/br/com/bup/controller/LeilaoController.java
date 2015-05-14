package br.com.bup.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAnunciante;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.EspacoPropagandaDAO;
import br.com.bup.dao.LanceLeilaoDAO;
import br.com.bup.dao.LeilaoDAO;
import br.com.bup.dao.ModalidadePagamentoDAO;
import br.com.bup.dao.PublicoAlvoDAO;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.LanceLeilao;
import br.com.bup.domain.Leilao;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.LeilaoApplication;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class LeilaoController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(AgenciaController.class);
	
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	private final PublicoAlvoDAO publicoAlvoDAO;
	private final LeilaoDAO leilaoDAO;
	private final ModalidadePagamentoDAO modalidadePagamentoDAO;
	private final LanceLeilaoDAO lanceLeilaoDAO;
	private final LeilaoApplication leilaoApplication; 
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected LeilaoController() {
		this(null, null, null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public LeilaoController(Result result, Validator validator, ResourceBundle i18n, EspacoPropagandaDAO espacoPropagandaDAO,
			UsuarioSession usuarioSession, PublicoAlvoDAO publicoAlvoDAO, LeilaoDAO leilaoDAO,
			ModalidadePagamentoDAO modalidadePagamentoDAO, LanceLeilaoDAO lanceLeilaoDAO, LeilaoApplication leilaoApplication) {
		super(result, validator, usuarioSession, i18n);
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.publicoAlvoDAO = publicoAlvoDAO;
		this.leilaoDAO = leilaoDAO;
		this.modalidadePagamentoDAO = modalidadePagamentoDAO;
		this.lanceLeilaoDAO = lanceLeilaoDAO;
		this.leilaoApplication = leilaoApplication;
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public List<EspacoPropaganda> listarEspacos() {
		LOGGER.debug("Listando os espacos para o leilao");
		result.include("publicosAlvos", publicoAlvoDAO.buscarTodos());
		return espacoPropagandaDAO.buscarLivresPorAnuncianteId(usuarioSession.getUsuario().getId());
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public void formulario(@NotNull Long espacoId) {
		validator.onErrorRedirectTo(this).listarEspacos();
		
		LOGGER.debug("carregando formulario de inclusao de leilao para o espaco " + espacoId);
		EspacoPropaganda espaco = espacoPropagandaDAO.buscarPorId(espacoId);
		
		if (espaco == null) {
			addErrorMsg("espaco.selecionado.invalido");
			validator.onErrorRedirectTo(this).listarEspacos();
		}
		
		Leilao leilao = new Leilao();
		leilao.setEspacoPropaganda(espaco);
		
		result.include("leilao", leilao);
		result.include("modalidadePagamentoList", modalidadePagamentoDAO.buscarTodos());
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public void criar(@Valid Leilao leilao) {
		if (leilao == null) {
			validator.onErrorRedirectTo(this).listarEspacos();
		} else {
			validator.onErrorRedirectTo(this).formulario(leilao.getEspacoPropaganda().getId());
		}
		
		LOGGER.debug("criando Leilao: " + leilao);
		
		// salva
		leilao = leilaoDAO.salvar(leilao);
		
		addSuccessMsg("leilao.criar.sucesso");
		result.redirectTo(IndexController.class).index();
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public List<Leilao> listar() {
		LOGGER.debug("Listando os leiloes do anunciante");
		
		return leilaoDAO.buscarPorAnuncianteId(usuarioSession.getUsuario().getId());
	}
	
	@OpenTransaction
	@Path("/leilao/apagar/{id}")
	public void apagar(Long id) {
		validarApagar(id);
		validator.onErrorRedirectTo(this).listar();
		
		leilaoDAO.apagarPorId(id);
		
		addSuccessMsg("msg.success.apagar");
		result.redirectTo(this).listar();
	}
	
	/**
	 * Verifica se o leilao pode ser apagado. Só pode apagar caso o estado do leilao seja ESPERANDO e não possua nenhum inscrito.
	 * 
	 * @param id
	 */
	private void validarApagar(Long id) {
		Leilao leilao = leilaoDAO.buscarPorId(id);
		if (!leilao.isEstadoEsperando() || !leilao.getInscritos().isEmpty()) {
			addErrorMsg("leilao.apagar.msg.erro");
		}
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public List<Leilao> inscrever() {
		LOGGER.debug("Listando os leiloes para inscrição");
		
		return leilaoDAO.buscarTodosEsperandoMenosAnuncianteId(usuarioSession.getUsuario().getId());
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public void inscricao(Long leilaoId) {
		LOGGER.debug("Se inscrevendo no leilao " + leilaoId);
		
		validarInscricao(leilaoId);
		validator.onErrorRedirectTo(this).inscrever();
		
		leilaoDAO.addInscritoNoLeilao(usuarioSession.getUsuario().getId(), leilaoId);
		
		addSuccessMsg("leilao.inscrever.msg.sucesso");
		result.redirectTo(this).inscrever();
	}
	
	/**
	 * Valida se o usuario logado/gerenciado pode se inscrever no leilao. O leilao deve esta no estado ESPERANDO para aceitar
	 * novos inscritos.
	 * 
	 * @param leilaoId
	 *            id do leilao.
	 */
	private void validarInscricao(Long leilaoId) {
		Leilao leilao = leilaoDAO.buscarPorId(leilaoId);
		
		if (!leilao.isEstadoEsperando()) {
			addErrorMsg("leilao.inscrever.msg.erro.periodo.inscricao");
		}
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public List<Leilao> listarInscritos() {
		LOGGER.debug("Listando os leiloes inscritos");
		
		return leilaoDAO.buscarPorInscritoId(usuarioSession.getUsuario().getId());
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public Leilao leilao(Long leilaoId) {
		LOGGER.debug("Carregando tela do leilao: " + leilaoId);
		
		Leilao leilao = leilaoApplication.getLeilaoPorId(leilaoId);
		LanceLeilao ultimoLance = null;
		if (leilao == null) {
			leilao = leilaoDAO.buscarPorId(leilaoId);
			ultimoLance = lanceLeilaoDAO.buscarUltimoPorLeilaoId(leilaoId);
			
		} else {
			ultimoLance = leilao.getUltimoLance();
		}
		
		result.include("ultimoLance", ultimoLance);
		
		return leilao;
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public void lancarLance(Long leilaoId, BigDecimal valor) {
		//TODO 
	}
}
