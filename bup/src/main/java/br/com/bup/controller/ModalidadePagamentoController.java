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
import br.com.bup.dao.ModalidadePagamentoDAO;
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
public class ModalidadePagamentoController extends BaseController{
	private final static Logger LOGGER = LoggerFactory.getLogger(ModalidadePagamentoController.class);
	
	private final ModalidadePagamentoDAO modalidadePagamentoDAO;
	private final MidiaDAO midiaDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected ModalidadePagamentoController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public ModalidadePagamentoController(Result result, Validator validator, ModalidadePagamentoDAO modalidadePagamentoDAO,
			MidiaDAO midiaDAO, UsuarioSession usuarioSession, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.modalidadePagamentoDAO = modalidadePagamentoDAO;
		this.midiaDAO = midiaDAO;
	}
	
	@OpenTransaction
	public void formulario() {
		LOGGER.debug("carregando formulario de modalidade de pagamento.");
		result.include("listaMidias", midiaDAO.buscarTodos());
	}
	
	@OpenTransaction
	@Path("/modalidadePagamento/editar/{id}")
	public void editar(Long id) {
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
		validar(modalidadePagamento);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		modalidadePagamento = modalidadePagamentoDAO.salvar(modalidadePagamento);
		addSuccessMsg("msg.success.modalidade_pagamento.criar");
		result.redirectTo(this).listar();
	}
	
	@Path("/modalidadePagamento/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		modalidadePagamentoDAO.apagarPorId(id);
		
		addSuccessMsg("msg.success.apagar");
		result.redirectTo(this).listar();
	}
	
	@OpenTransaction
	public void atualizar(@NotNull ModalidadePagamento modalidadePagamento) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		
		LOGGER.debug("atualizando modalidade de pagamento: " + modalidadePagamento);
		
		// validacoes...
		validar(modalidadePagamento);
		validator.onErrorRedirectTo(this).editar(modalidadePagamento.getId());
		
		// recupera os dados q nao estao no formulario
		modalidadePagamento = atualizarEntidadeDoFormulario(modalidadePagamento);
		
		// atualiza
		modalidadePagamento = modalidadePagamentoDAO.salvar(modalidadePagamento);
		addSuccessMsg("msg.success.modalidade_pagamento.atualizar");
		result.redirectTo(this).listar();
	}
	private void validar(ModalidadePagamento modalidadePagamento) {
		validator.validate(modalidadePagamento);
		if (!modalidadePagamentoDAO.unikConstraintValida(modalidadePagamento)) {
			addErrorMsg("msg.error.salvar");
		}
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
		try {
			NotNullBeanUtilsBean.getInstance().copyProperties(modalidadeAtualizada, modalidadePagamento);
		} catch (IllegalAccessException|InvocationTargetException e) {
			addErrorMsg("msg.error.editar");
		}
		
		return modalidadeAtualizada;
	}
}
