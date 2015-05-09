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
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class EspacoPropagandaController {
	private final static Logger LOGGER = LoggerFactory.getLogger(EspacoPropagandaController.class);
	
	private final Result result;
	private final Validator validator;
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	private final MidiaDAO midiaDAO;
	private final UsuarioSession usuarioSession;
	private final PublicoAlvoDAO publicoAlvoDAO;
	private final ResourceBundle i18n;
	
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
		this.result = result;
		this.validator = validator;
		this.midiaDAO = midiaDAO;
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.publicoAlvoDAO = publicoAlvoDAO;
		this.usuarioSession = usuarioSession;
		this.i18n = i18n;
	}
	
	@OpenTransaction
	@ApenasAnunciante
	@AnuncianteNaoGerenciado
	public void formulario() {
		result.include("formatosEspaco", FormatoEspacoPropaganda.values());
		result.include("midias", midiaDAO.buscarTodos());
		List<PublicoAlvo> alvos = publicoAlvoDAO.buscarTodos();
		Collections.sort(alvos, c);
//		result.include("publicosAlvos", alvos);
		
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
		
		Usuario usuario = usuarioSession.getUsuario();
		if (!(usuario instanceof Anunciante)) {
			validator.add(new SimpleMessage("error", "Usuario deve ser do tipo Anunciante"));
			validator.onErrorRedirectTo(this).formulario();
			
		} else {
			espacoPropaganda.setPertence((Anunciante) usuario);
		}
		
		// validacoes...
		validarCriar(espacoPropaganda);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		espacoPropaganda = espacoPropagandaDAO.salvar(espacoPropaganda);
		
		result.include("success", "Espaco criado com sucesso.");
		result.redirectTo(this).listar();
	}
	
	private void validarCriar(EspacoPropaganda espacoPropaganda) {
		validator.validate(espacoPropaganda);
		
		// TODO validar inclusao repetida
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
		
		result.include("success", i18n.getString("msg.success.apagar"));
		result.redirectTo(this).listar();
	}
	@OpenTransaction
	@Path("/espacoPropaganda/editar/{id}")
	public void editar(Long id) {
		LOGGER.debug("carregando formulario de midia com id: " + id);
		EspacoPropaganda espacoPropaganda = espacoPropagandaDAO.buscarPorId(id);
		
		result.include("espacoPropaganda", espacoPropaganda);
		formulario();
	}
	@OpenTransaction
	public void atualizar(@NotNull EspacoPropaganda espacoPropaganda) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		
		LOGGER.debug("atualizando espaço de propaganda: " + espacoPropaganda);
		
		// validacoes...
		validator.validate(espacoPropaganda);
		validator.onErrorRedirectTo(this).editar(espacoPropaganda.getId());
		
		// recupera os dados q nao estao no formulario
		espacoPropaganda = atualizarEntidadeDoFormulario(espacoPropaganda);
		
		// atualiza
		espacoPropaganda = espacoPropagandaDAO.salvar(espacoPropaganda);
		
		result.include("success", "midia atualizada com sucesso.");
		result.redirectTo(IndexController.class).index();
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
		
		//TODO testar o BeanBuild... sl oq
		try {
			NotNullBeanUtilsBean.getInstance().copyProperties(espacoPropagandaAtualizado, espacoPropaganda);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return espacoPropagandaAtualizado;
	}
}
