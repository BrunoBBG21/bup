package br.com.bup.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.AgenciaDAO;
import br.com.bup.dao.AnuncianteDAO;
import br.com.bup.dao.LanceLeilaoDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Named("agenciaController")
public class AgenciaController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AgenciaController.class);

	private final Result result;
	private final Validator validator;
	private final UsuarioSession usuarioSession;
	private final UsuarioDAO usuarioDAO;

	private final AgenciaDAO agenciaDAO;

	private final LanceLeilaoDAO lancesLeilaoDAO;
	
	private final AnuncianteDAO anuncianteDAO;

	/**
	 * @deprecated CDI eyes only
	 */
	protected AgenciaController() {
		this(null, null, null, null,null, null, null);
	}

	@Inject
	public AgenciaController(Result result, Validator validator,
			AgenciaDAO agenciaDAO,LanceLeilaoDAO lancesLeilaoDAO,AnuncianteDAO anuncianteDAO, UsuarioSession usuarioSession,UsuarioDAO usuarioDAO) {
		this.result = result;
		this.validator = validator;
		this.agenciaDAO = agenciaDAO;
		this.lancesLeilaoDAO = lancesLeilaoDAO;
		this.anuncianteDAO = anuncianteDAO;
		this.usuarioSession = usuarioSession;
		this.usuarioDAO = usuarioDAO;
	}

	/**
	 * Busca os nomes e ids dos Anunciantes que s�o gerenciados pela agencia.
	 * @return map com id e nome dos anunciantes. 
	 */
	public List<Map<String, Object>> getGerenciados() {
		return agenciaDAO.buscaGerenciados(usuarioSession.getUsuarioLogadoComoAgencia());
	}
	
	public void formulario() {
		LOGGER.debug("carregando formulario de agencia");
		result.include("lances", lancesLeilaoDAO.buscarTodos());
		result.include("gerencias", anuncianteDAO.buscarTodos());
		result.include("usuarios", usuarioDAO.buscarTodos());
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}
	
	@OpenTransaction
	public void gerenciar(Long id) {
		if (id != null && id > 0) {
			usuarioSession.gerenciar((Anunciante) usuarioDAO.buscarPorId(id));
			
		} else if (id != null && id < 0) { 
			usuarioSession.desGerenciar();
		}
		result.redirectTo(IndexController.class).index();
	}

	@OpenTransaction
	public void criar(@NotNull String cnpj,@NotNull Usuario usuario,List<Anunciante> gerencias) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando agencia: agencia - " + cnpj+ ", usuario - "+usuario.getNome());
		Usuario logado = usuarioSession.getUsuario();
		if (!("admin".equals(logado.getNome()) && logado.getId() == 1)) {
			validator.add(new SimpleMessage("error", "Usuario deve ser o administrador"));
			validator.onErrorRedirectTo(this).formulario();
		} else {
			Agencia agencia = new Agencia();
			agencia.setCnpj(cnpj);
			agencia.setGerencia(gerencias);
			agencia.setCep(usuario.getCep());
			agencia.setEmail(usuario.getEmail());
			agencia.setEndereco(usuario.getEndereco());
			agencia.setNome(usuario.getNome());
			agencia.setPassword(usuario.getPassword());
			agencia.setSaldo(usuario.getSaldo());
			agencia.setTelefone(usuario.getTelefone());
			agencia.setLances(agencia.getLances());
			agencia.setContasBancarias(usuario.getContasBancarias());
			// validacoes...
			validarCriar(agencia);
			validator.onErrorRedirectTo(this).formulario();

			// salva
			agenciaDAO.salvar(agencia);

			result.include("success", "conta bancaria criada com sucesso.");
			result.redirectTo(IndexController.class).index();
		}
	}

	private void validarCriar(Agencia agencia) {
		validator.validate(agencia);

		// TODO validar inclusao repetida
	}
	@OpenTransaction
	public List<Agencia> listar() {
		LOGGER.debug("Listando os agentes. ");
		
		return agenciaDAO.buscarTodos();
	}
}
