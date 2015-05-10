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
import br.com.bup.dao.PublicoAlvoDAO;
import br.com.bup.domain.ModalidadePagamento;
import br.com.bup.domain.PublicoAlvo;
import br.com.bup.util.NotNullBeanUtilsBean;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class PublicoAlvoController extends BaseController{
	private final static Logger LOGGER = LoggerFactory.getLogger(PublicoAlvoController.class);
	
	private final PublicoAlvoDAO publicoAlvoDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected PublicoAlvoController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public PublicoAlvoController(Result result, Validator validator, PublicoAlvoDAO publicoAlvoDAO,
			UsuarioSession usuarioSession, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.publicoAlvoDAO = publicoAlvoDAO;
	}
	
	@OpenTransaction
	public void formulario() {
		LOGGER.debug("carregando formulario de publico alvo.");
	}
	@OpenTransaction
	@Path("/publicoAlvo/editar/{id}")
	public void editar(Long id) {
		LOGGER.debug("carregando formulario de publico alvo com id: " + id);
		PublicoAlvo publicoAlvo = publicoAlvoDAO.buscarPorId(id);
		
		result.include("publicoAlvo", publicoAlvo);
		formulario();
	}
	@OpenTransaction
	public void atualizar(@NotNull PublicoAlvo publicoAlvo) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		
		LOGGER.debug("atualizando publico alvo: " + publicoAlvo);
		
		// validacoes...
		validar(publicoAlvo);
		validator.onErrorRedirectTo(this).editar(publicoAlvo.getId());
		
		// recupera os dados q nao estao no formulario
		publicoAlvo = atualizarEntidadeDoFormulario(publicoAlvo);
		
		// atualiza
		publicoAlvo = publicoAlvoDAO.salvar(publicoAlvo);
		
		addSuccessMsg("msg.success.publico_alvo.atualizar");
		result.redirectTo(this).listar();
	}
	
	/**
	 * Retorna uma entidade atualizada com o banco e a passada pro metodo,
	 * mantendo os atributos do formulario da entidade passada.
	 * 
	 * @param modalidadePagamento
	 * @return Entidade atualizada.
	 */
	private PublicoAlvo atualizarEntidadeDoFormulario(PublicoAlvo pub) {
		PublicoAlvo pubAtualizada = publicoAlvoDAO.buscarPorId(pub.getId());
		try {
			NotNullBeanUtilsBean.getInstance().copyProperties(pubAtualizada, pub);
		} catch (InvocationTargetException|IllegalAccessException e) {
			addErrorMsg("msg.error.editar");
		}
		
		return pubAtualizada;
	}
	@OpenTransaction
	public List<PublicoAlvo> listar() {
		LOGGER.debug("Listando os publicos alvos. ");
		
		return publicoAlvoDAO.buscarTodos();
	}
	
	@OpenTransaction
	public void criar(@NotNull PublicoAlvo publicoAlvo) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando publico alvo: " + publicoAlvo);
		
		// validacoes...
		validar(publicoAlvo);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		publicoAlvo = publicoAlvoDAO.salvar(publicoAlvo);
		addSuccessMsg("msg.success.publico_alvo.criar");
		result.redirectTo(this).listar();
	}
	
	private void validar(PublicoAlvo publicoAlvo) {
		validator.validate(publicoAlvo);
		if (!publicoAlvoDAO.unikConstraintValida(publicoAlvo)) {
			addErrorMsg("msg.error.salvar");
		}
	}
	
	@Path("/publicoAlvo/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		publicoAlvoDAO.apagarPorId(id);
		addSuccessMsg("msg.success.apagar");
		result.redirectTo(this).listar();
	}
}
