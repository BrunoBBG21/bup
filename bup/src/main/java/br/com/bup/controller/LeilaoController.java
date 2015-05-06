package br.com.bup.controller;

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
import br.com.bup.dao.LeilaoDAO;
import br.com.bup.dao.ModalidadePagamentoDAO;
import br.com.bup.dao.PublicoAlvoDAO;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.Leilao;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class LeilaoController extends BaseController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AgenciaController.class);
	
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	private final PublicoAlvoDAO publicoAlvoDAO;
	private final LeilaoDAO leilaoDAO;
	private final ModalidadePagamentoDAO modalidadePagamentoDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected LeilaoController() {
		this(null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public LeilaoController(Result result, Validator validator, ResourceBundle i18n, EspacoPropagandaDAO espacoPropagandaDAO,
			UsuarioSession usuarioSession, PublicoAlvoDAO publicoAlvoDAO, LeilaoDAO leilaoDAO,
			ModalidadePagamentoDAO modalidadePagamentoDAO) {
		super(result, validator, usuarioSession, i18n);
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.publicoAlvoDAO = publicoAlvoDAO;
		this.leilaoDAO = leilaoDAO;
		this.modalidadePagamentoDAO = modalidadePagamentoDAO;
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public List<EspacoPropaganda> listarEspacos() {
		LOGGER.debug("Listando os espacos para o leilao");
		result.include("publicosAlvos", publicoAlvoDAO.buscarTodos());
		return espacoPropagandaDAO.buscarPorAnuncianteId(usuarioSession.getUsuario().getId());
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
		
		setSuccessMsg("leilao.criar.sucesso");
		result.redirectTo(IndexController.class).index();
	}
}
