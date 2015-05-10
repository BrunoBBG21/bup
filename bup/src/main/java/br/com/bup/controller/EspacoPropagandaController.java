package br.com.bup.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.AnuncianteNaoGerenciado;
import br.com.bup.annotation.ApenasAnunciante;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.EspacoPropagandaDAO;
import br.com.bup.dao.MidiaDAO;
import br.com.bup.dao.PublicoAlvoDAO;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.FormatoEspacoPropaganda;
import br.com.bup.domain.Midia;
import br.com.bup.domain.PublicoAlvo;
import br.com.bup.domain.Usuario;
import br.com.bup.util.NotNullBeanUtilsBean;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class EspacoPropagandaController extends BaseController{
	private final static Logger LOGGER = LoggerFactory.getLogger(EspacoPropagandaController.class);
	
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	private final MidiaDAO midiaDAO;
	private final PublicoAlvoDAO publicoAlvoDAO;
	
	private final Comparator<PublicoAlvo> c = new Comparator<PublicoAlvo>() {
		public int compare(PublicoAlvo o1, PublicoAlvo o2) {
			if (o1 == null && o2 == null)
				return 0;
			if (o1 != null && o2 == null)
				return +1;
			if (o1 == null && o2 != null)
				return -1;
			if (o1.equals(o2))
				return 0;
			if (o1.getDescricao() == null && o2.getDescricao() == null)
				return 0;
			if (o1.getDescricao() != null && o2.getDescricao() == null)
				return +1;
			if (o1.getDescricao() == null && o2.getDescricao() != null)
				return -1;
			if (o1.getDescricao().equals(o2.getDescricao()))
				return 0;
			
			return o1.getDescricao().compareToIgnoreCase(o2.getDescricao());
			
		}
	};
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected EspacoPropagandaController() {
		this(null, null, null, null, null, null, null);
	}
	
	@Inject
	public EspacoPropagandaController(Result result, Validator validator, MidiaDAO midiaDAO,
			EspacoPropagandaDAO espacoPropagandaDAO, PublicoAlvoDAO publicoAlvoDAO, UsuarioSession usuarioSession,
			ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.midiaDAO = midiaDAO;
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.publicoAlvoDAO = publicoAlvoDAO;
	}
	
	@OpenTransaction
	@ApenasAnunciante
	@AnuncianteNaoGerenciado
	public void formulario() {
		result.include("formatosEspaco", FormatoEspacoPropaganda.values());
		result.include("midias", midiaDAO.buscarTodos());
		List<PublicoAlvo> alvos = publicoAlvoDAO.buscarTodos();
		Collections.sort(alvos, c);
		
		List<List<PublicoAlvo>> categoriaAlvos = new ArrayList<List<PublicoAlvo>>();
		List<PublicoAlvo> alvosAux = new ArrayList<PublicoAlvo>();
		for (PublicoAlvo publicoAlvo : alvos) {
			if (!alvosAux.isEmpty() 
					&& !alvosAux.get(0).getDescricao().equals(publicoAlvo.getDescricao())) {
				categoriaAlvos.add(alvosAux);
				alvosAux = new ArrayList<PublicoAlvo>();
			}
			alvosAux.add(publicoAlvo);
		}
		if (!alvosAux.isEmpty()) {
			categoriaAlvos.add(alvosAux);
		}
		
		result.include("categoriaAlvos", categoriaAlvos);
	}
	
	@OpenTransaction
	@ApenasAnunciante
	@AnuncianteNaoGerenciado
	public void criar(@NotNull EspacoPropaganda espacoPropaganda) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando espaco: " + espacoPropaganda);
		
		// validacoes...
		validar(espacoPropaganda);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		espacoPropaganda = espacoPropagandaDAO.salvar(espacoPropaganda);
		addSuccessMsg("msg.success.espaco_propaganda.criar");
		result.redirectTo(this).listar();
	}
	
	private void validar(EspacoPropaganda espacoPropaganda) {
		Usuario usuario = usuarioSession.getUsuario();
		if (!(usuario instanceof Anunciante)) {
			addErrorMsg("msg.error.espaco_propaganda.anunciante");
			validator.onErrorRedirectTo(this).formulario();
		} else {
			espacoPropaganda.setPertence((Anunciante) usuario);
		}
		validator.validate(espacoPropaganda);
		if (!espacoPropagandaDAO.unikConstraintValida(espacoPropaganda)) {
			addErrorMsg("msg.error.salvar");
		}
	}

	@OpenTransaction
	public List<EspacoPropaganda> listar() {
		LOGGER.debug("Listando os espacos ");
		result.include("publicosAlvos", publicoAlvoDAO.buscarTodos());
		return espacoPropagandaDAO.buscarPorAnuncianteId(usuarioSession.getUsuario().getId());
	}
	
	@Path("/espacoPropaganda/apagar/{id}")
	@OpenTransaction
	@ApenasAnunciante
	public void apagar(Long id) {
		espacoPropagandaDAO.apagarLogado(id, usuarioSession.getUsuarioLogado().getId());
		addSuccessMsg("msg.success.apagar");
		result.redirectTo(this).listar();
	}
	@OpenTransaction
	@Path("/espacoPropaganda/editar/{id}")
	public void editar(Long id) {
		LOGGER.debug("carregando formulario de espaco propaganda com id: " + id);
		EspacoPropaganda espacoPropaganda = espacoPropagandaDAO.buscarPorId(id);
		
		result.include("espacoPropaganda", espacoPropaganda);
		formulario();
	}
	@OpenTransaction
	public void atualizar(@NotNull EspacoPropaganda espacoPropaganda) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		
		LOGGER.debug("atualizando espaço de propaganda: " + espacoPropaganda);
		
		// validacoes...
		
		validar(espacoPropaganda);
		validator.onErrorRedirectTo(this).editar(espacoPropaganda.getId());
		
		// recupera os dados q nao estao no formulario
		espacoPropaganda = atualizarEntidadeDoFormulario(espacoPropaganda);
		
		// atualiza
		espacoPropaganda = espacoPropagandaDAO.salvar(espacoPropaganda);
		addSuccessMsg("msg.success.espaco_propaganda.atualizar");
		result.redirectTo(this).listar();
	}
	
	/**
	 * Retorna uma entidade atualizada com o banco e a passada pro metodo,
	 * mantendo os atributos do formulario da entidade passada.
	 * 
	 * @param modalidadePagamento
	 * @return Entidade atualizada.
	 */
	private EspacoPropaganda atualizarEntidadeDoFormulario(EspacoPropaganda espacoPropaganda) {
		EspacoPropaganda espacoPropagandaAtualizado = espacoPropagandaDAO.buscarPorId(espacoPropaganda.getId());
		
		try {
			NotNullBeanUtilsBean.getInstance().copyProperties(espacoPropagandaAtualizado, espacoPropaganda);
		} catch (InvocationTargetException|IllegalAccessException ex) {
			addErrorMsg("msg.error.editar");
		}
		
		return espacoPropagandaAtualizado;
	}
}
