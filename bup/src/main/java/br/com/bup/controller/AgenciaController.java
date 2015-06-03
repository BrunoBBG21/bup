package br.com.bup.controller;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAnunciante;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.AgenciaDAO;
import br.com.bup.dao.AnuncianteDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Named("agenciaController")
public class AgenciaController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(AgenciaController.class);
	
	private final UsuarioDAO usuarioDAO;
	private final AgenciaDAO agenciaDAO;
	private final AnuncianteDAO anuncianteDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected AgenciaController() {
		this(null, null, null, null, null, null, null);
	}
	
	@Inject
	public AgenciaController(Result result, Validator validator, AgenciaDAO agenciaDAO, 
			AnuncianteDAO anuncianteDAO, UsuarioSession usuarioSession, UsuarioDAO usuarioDAO, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.agenciaDAO = agenciaDAO;
		this.anuncianteDAO = anuncianteDAO;
		this.usuarioDAO = usuarioDAO;
	}
	
	@Path("/agencia/associar/{id}")
	@OpenTransaction
	@ApenasAnunciante
	public void associar(Long id) {
		Anunciante a = anuncianteDAO.buscarPorId(usuarioSession.getUsuarioLogado().getId());
		Agencia ag = agenciaDAO.buscarPorId(id);
		a.setGerenciado(ag);
		ag = agenciaDAO.salvar(ag);
		
		addSuccessMsg("msg.success.associar");
		result.include("success", i18n.getString("msg.success.associar"));
		result.redirectTo(this).listar();
	}
	
	@OpenTransaction
	public void gerenciar(Long id) {
		if (id != null && id > 0) {
			Anunciante anunciante = (Anunciante) usuarioDAO.buscarPorId(id);
			usuarioSession.gerenciar(anunciante);
			addSuccessMsg("msg.anunciante.gerenciar.success", anunciante.getNome());
			
		} else if (id != null && id < 0) {
			usuarioSession.desGerenciar();
			addSuccessMsg("msg.anunciante.desgerenciar.success");
		}
		result.redirectTo(IndexController.class).index();
	}
	
	@OpenTransaction
	public List<Agencia> listar() {
		LOGGER.debug("Listando os agentes. ");
		
		return agenciaDAO.buscaNaoGerenciados(usuarioSession.getUsuarioLogado().getId());
	}
	
	/**
	 * Busca os nomes e ids dos Anunciantes que s�o gerenciados pela agencia.
	 * 
	 * @return map com id e nome dos anunciantes.
	 */
	public List<Map<String, Object>> getGerenciados() {
		return agenciaDAO.buscaGerenciados(usuarioSession.getUsuarioLogadoComoAgencia());
	}
}
