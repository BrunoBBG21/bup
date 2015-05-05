package br.com.bup.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.AgenciaDAO;
import br.com.bup.dao.AnuncianteDAO;
import br.com.bup.dao.EspacoPropagandaDAO;
import br.com.bup.dao.HistoricoAluguelEspacoDAO;
import br.com.bup.dao.LanceLeilaoDAO;
import br.com.bup.dao.LeilaoDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.HistoricoAluguelEspaco;
import br.com.bup.domain.LanceLeilao;
import br.com.bup.domain.Leilao;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class AnuncianteController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AnuncianteController.class);
	
	private final Result result;
	private final Validator validator;
	private final UsuarioSession usuarioSession;
	private final UsuarioDAO usuarioDAO;
	
	private final AgenciaDAO agenciaDAO;
	
	private final LanceLeilaoDAO lancesLeilaoDAO;
	
	private final AnuncianteDAO anuncianteDAO;
	
	private final LeilaoDAO leilaoDAO;
	
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	
	private final HistoricoAluguelEspacoDAO historicoAluguelEspacoDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected AnuncianteController() {
		this(null, null, null, null, null, null, null, null, null, null);
	}
	
	@Inject
	public AnuncianteController(Result result, Validator validator, HistoricoAluguelEspacoDAO historicoAluguelEspacoDAO,
			AgenciaDAO agenciaDAO, EspacoPropagandaDAO espacoPropagandaDAO, LeilaoDAO leilaoDAO, LanceLeilaoDAO lancesLeilaoDAO,
			AnuncianteDAO anuncianteDAO, UsuarioSession usuarioSession, UsuarioDAO usuarioDAO) {
		this.result = result;
		this.validator = validator;
		this.agenciaDAO = agenciaDAO;
		this.lancesLeilaoDAO = lancesLeilaoDAO;
		this.leilaoDAO = leilaoDAO;
		this.anuncianteDAO = anuncianteDAO;
		this.usuarioSession = usuarioSession;
		this.usuarioDAO = usuarioDAO;
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.historicoAluguelEspacoDAO = historicoAluguelEspacoDAO;
	}
	
	public void formulario() {
		LOGGER.debug("carregando formulario de agencia");
		result.include("lances", lancesLeilaoDAO.buscarTodos());
		result.include("agencias", agenciaDAO.buscarTodos());
		result.include("usuarios", usuarioDAO.buscarTodos());
		result.include("leiloes", leilaoDAO.buscarTodos());
		result.include("espacos", espacoPropagandaDAO.buscarTodos());
		result.include("historicos", historicoAluguelEspacoDAO.buscarTodos());
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}
	
	@OpenTransaction
	public void criar(@NotNull String cpf, @NotNull Usuario usuario, Agencia gerenciado, List<LanceLeilao> lances,
			List<Leilao> leiloesInscrito, List<EspacoPropaganda> espacosPossuidos, List<EspacoPropaganda> espacosAlugados,
			List<HistoricoAluguelEspaco> historicosAlugueis) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando anunciante: anunciante - " + cpf + ", usuario - " + usuario.getNome());
		
		Anunciante anunciante = new Anunciante();
		anunciante.setCpf(cpf);
		anunciante.setGerenciado(gerenciado);
		anunciante.setLances(lances);
		anunciante.setLeiloesInscrito(leiloesInscrito);
		anunciante.setEspacosAlugados(espacosAlugados);
		anunciante.setEspacosPossuidos(espacosPossuidos);
		anunciante.setHistoricosAlugueis(historicosAlugueis);
		anunciante.setCep(usuario.getCep());
		anunciante.setEmail(usuario.getEmail());
		anunciante.setEndereco(usuario.getEndereco());
		anunciante.setNome(usuario.getNome());
		anunciante.setPassword(usuario.getPassword());
		anunciante.setSaldo(usuario.getSaldo());
		anunciante.setTelefone(usuario.getTelefone());
		anunciante.setLances(anunciante.getLances());
		anunciante.setContasBancarias(usuario.getContasBancarias());
		// validacoes...
		validarCriar(anunciante);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		anunciante = anuncianteDAO.salvar(anunciante);
		
		result.include("success", "anunciante criado com sucesso.");
		result.redirectTo(IndexController.class).index();
	}
	
	private void validarCriar(Anunciante anunciante) {
		validator.validate(anunciante);
		
		// TODO validar inclusao repetida
	}
	
	@OpenTransaction
	public List<Anunciante> listar() {
		LOGGER.debug("Listando os anunciantes. ");
		
		return anuncianteDAO.buscarTodos();
	}
}
