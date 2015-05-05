package br.com.bup.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.AnuncianteDAO;
import br.com.bup.dao.EspacoPropagandaDAO;
import br.com.bup.dao.LanceLeilaoDAO;
import br.com.bup.dao.LeilaoDAO;
import br.com.bup.dao.ModalidadePagamentoDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.LanceLeilao;
import br.com.bup.domain.Leilao;
import br.com.bup.domain.ModalidadePagamento;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class LeilaoController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AgenciaController.class);
	
	private final Result result;
	private final Validator validator;
	private final UsuarioSession usuarioSession;
	private final UsuarioDAO usuarioDAO;
	
	private final LanceLeilaoDAO lancesLeilaoDAO;
	
	private final AnuncianteDAO anuncianteDAO;
	
	private final ModalidadePagamentoDAO modalidadePagamentoDAO;
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	
	private final LeilaoDAO leilaoDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected LeilaoController() {
		this(null, null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public LeilaoController(Result result, Validator validator, LeilaoDAO leilaoDAO,
			ModalidadePagamentoDAO modalidadePagamentoDAO, EspacoPropagandaDAO espacoPropagandaDAO,
			LanceLeilaoDAO lancesLeilaoDAO, AnuncianteDAO anuncianteDAO, UsuarioSession usuarioSession, UsuarioDAO usuarioDAO) {
		this.result = result;
		this.validator = validator;
		this.leilaoDAO = leilaoDAO;
		this.modalidadePagamentoDAO = modalidadePagamentoDAO;
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.lancesLeilaoDAO = lancesLeilaoDAO;
		this.anuncianteDAO = anuncianteDAO;
		this.usuarioSession = usuarioSession;
		this.usuarioDAO = usuarioDAO;
	}
	
	public void formulario() {
		LOGGER.debug("carregando formulario de agencia");
		result.include("lances", lancesLeilaoDAO.buscarTodos());
		result.include("anunciantes", anuncianteDAO.buscarTodos());
		result.include("espacos", espacoPropagandaDAO.buscarTodos());
		result.include("modalidades", modalidadePagamentoDAO.buscarTodos());
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}
	
	@OpenTransaction
	public void criar(@NotNull Date dataInicio, @NotNull Date dataFim, @NotNull ModalidadePagamento modalidadePagamento,
			@NotNull EspacoPropaganda espacoPropaganda, List<LanceLeilao> lances, List<Anunciante> inscritos, BigDecimal reserva,
			BigDecimal inscricao, Boolean ativo) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando leilao: dataInicio - " + dataInicio + ", dataFim - " + dataFim + ", ModalidadePagamento - "
				+ modalidadePagamento.getTipo() + ", espacoPropaganda - " + espacoPropaganda.getDescricao());
		
		Leilao leilao = new Leilao();
		leilao.setAtivo(ativo);
		leilao.setDataFim(dataFim);
		leilao.setDataInicio(dataInicio);
		leilao.setEspacoPropaganda(espacoPropaganda);
		leilao.setInscricao(inscricao);
		leilao.setInscritos(inscritos);
		leilao.setLances(lances);
		leilao.setModalidadePagamento(modalidadePagamento);
		leilao.setReserva(reserva);
		// validacoes...
		validarCriar(leilao);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		leilao = leilaoDAO.salvar(leilao);
		
		result.include("success", "conta bancaria criada com sucesso.");
		result.redirectTo(IndexController.class).index();
	}
	
	private void validarCriar(Leilao leilao) {
		validator.validate(leilao);
		
		// TODO validar inclusao repetida
	}
}
