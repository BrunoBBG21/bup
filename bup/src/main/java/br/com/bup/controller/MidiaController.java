package br.com.bup.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAdministrador;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.MidiaDAO;
import br.com.bup.domain.Midia;
import br.com.bup.util.BaseWeb;
import br.com.bup.util.NotNullBeanUtilsBean;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class MidiaController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(MidiaController.class);
	
	private final MidiaDAO midiaDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected MidiaController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public MidiaController(Result result, Validator validator, MidiaDAO midiaDAO, UsuarioSession usuarioSession,
			ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.midiaDAO = midiaDAO;
	}
	
	@ApenasAdministrador
	public void formulario() {
		LOGGER.debug("carregando formulario de midia");
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}
	
	@OpenTransaction
	@Path("/midia/editar/{id}")
	public void editar(Long id) {
		LOGGER.debug("carregando formulario de midia com id: " + id);
		Midia midia = midiaDAO.buscarPorId(id);
		
		result.include("midia", midia);
		formulario();
	}
	
	@OpenTransaction
	public void atualizar(@NotNull Midia midia) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		
		LOGGER.debug("atualizando modalidade de pagamento: " + midia);
		
		// validacoes...
		validar(midia);
		validator.onErrorRedirectTo(this).editar(midia.getId());
		
		// recupera os dados q nao estao no formulario
		midia = atualizarEntidadeDoFormulario(midia);
		
		// atualiza
		midia = midiaDAO.salvar(midia);
		
		addSuccessMsg("msg.success.midia.atualizar");
		result.redirectTo(this).listar();
		
	}
	
	/**
	 * Retorna uma entidade atualizada com o banco e a passada pro metodo, mantendo os atributos do formulario da entidade
	 * passada.
	 * 
	 * @param modalidadePagamento
	 * @return Entidade atualizada.
	 */
	private Midia atualizarEntidadeDoFormulario(Midia midia) {
		Midia midiaAtualizada = midiaDAO.buscarPorId(midia.getId());
		try {
			NotNullBeanUtilsBean.getInstance().copyProperties(midiaAtualizada, midia);
		} catch (IllegalAccessException | InvocationTargetException e) {
			addErrorMsg("msg.error.editar");
		}
		return midiaAtualizada;
	}
	
	private void validar(Midia midia) {
		validator.validate(midia);
		if (!midiaDAO.unikConstraintValida(midia)) {
			addErrorMsg("msg.error.salvar");
		}
		if (midia.getId() != null && midiaDAO.unikConstraintDiferenteId(midia.getTipo(),midia.getId())) {
			addErrorMsg("tipo", "ja.existe.midia");
		}
	}
	
	@OpenTransaction
	@ApenasAdministrador
	public void criar(@NotNull Midia midia) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando midia: " + midia);
		
		// validacoes...
		validar(midia);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		midia = midiaDAO.salvar(midia);
		
		addSuccessMsg("msg.success.midia.criar");
		result.redirectTo(this).listar();
	}
	
	@OpenTransaction
	@ApenasAdministrador
	public List<Midia> listar() {
		LOGGER.debug("Listando as midias ");
		
		return midiaDAO.buscarTodos();
	}
	
	@Path("/midia/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		midiaDAO.apagarPorId(id);
		
		addSuccessMsg("msg.success.apagar");
		result.redirectTo(this).listar();
	}
}
