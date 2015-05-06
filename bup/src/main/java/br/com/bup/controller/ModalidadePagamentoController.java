package br.com.bup.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAdministrador;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.MidiaDAO;
import br.com.bup.dao.ModalidadePagamentoDAO;
import br.com.bup.domain.ModalidadePagamento;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class ModalidadePagamentoController {
	private final static Logger LOGGER = LoggerFactory.getLogger(ModalidadePagamentoController.class);
	
	private final Result result;
	private final Validator validator;
	private final ModalidadePagamentoDAO modalidadePagamentoDAO;
	private final MidiaDAO midiaDAO;
	private final UsuarioSession usuarioSession;
	private final ResourceBundle i18n;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected ModalidadePagamentoController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public ModalidadePagamentoController(Result result, Validator validator, ModalidadePagamentoDAO modalidadePagamentoDAO,
			MidiaDAO midiaDAO, UsuarioSession usuarioSession, ResourceBundle i18n) {
		this.result = result;
		this.validator = validator;
		this.modalidadePagamentoDAO = modalidadePagamentoDAO;
		this.midiaDAO = midiaDAO;
		this.i18n = i18n;
		this.usuarioSession = usuarioSession;
	}
	
	@OpenTransaction
	public void formulario() {
		LOGGER.debug("carregando formulario de modalidade de pagamento.");
		result.include("listaMidias", midiaDAO.buscarTodos());
	}
	
	@OpenTransaction
	@Path("/modalidadePagamento/formulario/{id}")
	public void formulario(Long id) {
		LOGGER.debug("carregando formulario de modalidade de pagamento com id: " + id);
		ModalidadePagamento modPag = modalidadePagamentoDAO.buscarPorId(id);
		
		result.include("modalidadePagamento", modPag);
		formulario();
	}
	
	@OpenTransaction
	public List<ModalidadePagamento> listar() {
		LOGGER.debug("Listando as modalidades de pagamento. ");
		
		return modalidadePagamentoDAO.buscarTodos();
	}
	
	@OpenTransaction
	public void criar(@NotNull ModalidadePagamento modalidadePagamento) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando modalidade de pagamento: " + modalidadePagamento);
		
		// validacoes...
		validator.validate(modalidadePagamento);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		modalidadePagamento = modalidadePagamentoDAO.salvar(modalidadePagamento);
		
		result.include("success", "Modalidade de pagamento criada com sucesso.");
		result.redirectTo(IndexController.class).index();
	}
	
	@Path("/modalidadePagamento/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		modalidadePagamentoDAO.apagarPorId(id);
		
		result.include("success", i18n.getString("msg.success.apagar"));
		result.redirectTo(this).listar();
	}
	
	@OpenTransaction
	public void atualizar(@NotNull ModalidadePagamento modalidadePagamento) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		
		LOGGER.debug("atualizando modalidade de pagamento: " + modalidadePagamento);
		
		// validacoes...
		validator.validate(modalidadePagamento);
		validator.onErrorRedirectTo(this).formulario();
		
		// recupera os dados q nao estao no formulario
		modalidadePagamento = atualizarEntidadeDoFormulario(modalidadePagamento);
		
		// atualiza
		modalidadePagamento = modalidadePagamentoDAO.salvar(modalidadePagamento);
		
		result.include("success", "Modalidade de pagamento atualizada com sucesso.");
		result.redirectTo(IndexController.class).index();
	}
	
	/**
	 * Retorna uma entidade atualizada com o banco e a passada pro metodo,
	 * mantendo os atributos do formulario da entidade passada.
	 * 
	 * @param modalidadePagamento
	 * @return Entidade atualizada.
	 */
	private ModalidadePagamento atualizarEntidadeDoFormulario(ModalidadePagamento modalidadePagamento) {
		ModalidadePagamento modalidadeAtualizada = modalidadePagamentoDAO.buscarPorId(modalidadePagamento.getId());
		
		//TODO testar o BeanBuild... sl oq
		modalidadeAtualizada.setMaxParcela(modalidadePagamento.getMaxParcela());
		modalidadeAtualizada.setMidia(modalidadePagamento.getMidia());
		modalidadeAtualizada.setTipo(modalidadePagamento.getTipo());
		modalidadeAtualizada.setValorMinParcela(modalidadePagamento.getValorMinParcela());
		
		return modalidadeAtualizada;
	}
}
