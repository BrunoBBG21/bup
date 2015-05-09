package br.com.bup.controller;

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
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Severity;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class PublicoAlvoController {
	private final static Logger LOGGER = LoggerFactory.getLogger(PublicoAlvoController.class);
	
	private final Result result;
	private final Validator validator;
	private final PublicoAlvoDAO publicoAlvoDAO;
	private final UsuarioSession usuarioSession;
	private final ResourceBundle i18n;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected PublicoAlvoController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public PublicoAlvoController(Result result, Validator validator, PublicoAlvoDAO publicoAlvoDAO,
			UsuarioSession usuarioSession, ResourceBundle i18n) {
		this.result = result;
		this.validator = validator;
		this.publicoAlvoDAO = publicoAlvoDAO;
		this.usuarioSession = usuarioSession;
		this.i18n = i18n;
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
		validator.validate(publicoAlvo);
		validator.onErrorRedirectTo(this).editar(publicoAlvo.getId());
		
		// recupera os dados q nao estao no formulario
		publicoAlvo = atualizarEntidadeDoFormulario(publicoAlvo);
		
		// atualiza
		publicoAlvo = publicoAlvoDAO.salvar(publicoAlvo);
		
//		result.include("success", "msg.success.publico_alvo");
		validator.add(new I18nMessage("success", "msg.success.publico_alvo.atualizar", Severity.SUCCESS));
//		result.redirectTo(IndexController.class).index();
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
		
		//TODO testar o BeanBuild... sl oq
		pubAtualizada.setDescricao(pub.getDescricao());
		pubAtualizada.setNome(pub.getNome());
		
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
		validarCriar(publicoAlvo);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		publicoAlvo = publicoAlvoDAO.salvar(publicoAlvo);
		
//		result.include("success", "msg.success.publico_alvo.criar");
		validator.add(new I18nMessage("success", "msg.success.publico_alvo.criar", Severity.SUCCESS));
//		result.redirectTo(IndexController.class).index();
		result.redirectTo(this).listar();
	}
	
	private void validarCriar(PublicoAlvo publicoAlvo) {
		validator.validate(publicoAlvo);
	}
	
	@Path("/publicoAlvo/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		publicoAlvoDAO.apagarPorId(id);
		
//		result.include("success", i18n.getString("msg.success.apagar"));
		validator.add(new I18nMessage("success", "msg.success.apagar", Severity.SUCCESS));
		result.redirectTo(this).listar();
	}
}
