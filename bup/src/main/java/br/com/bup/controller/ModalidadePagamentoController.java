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
		
		validator.add(new I18nMessage("success", "msg.success.modalidade_pagamento.criar", Severity.SUCCESS));
		result.redirectTo(this).listar();
	}
	
	@Path("/modalidadePagamento/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		modalidadePagamentoDAO.apagarPorId(id);
		
		validator.add(new I18nMessage("success", "msg.success.apagar", Severity.SUCCESS));
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
		
		validator.add(new I18nMessage("success", "msg.success.modalidade_pagamento.atualizar", Severity.SUCCESS));
		result.redirectTo(this).listar();
	}
	private void validar(ModalidadePagamento modalidadePagamento) {
		validator.validate(modalidadePagamento);
		if (!modalidadePagamentoDAO.unikConstraintValida(modalidadePagamento)) {
			validator.add(new I18nMessage("Modalidade Pagamento", "msg.error.salvar",Severity.ERROR));
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
		} catch (IllegalAccessException e) {
			validator.add(new I18nMessage("error", "msg.error.editar", Severity.ERROR));
		} catch (InvocationTargetException e) {
			validator.add(new I18nMessage("error", "msg.error.editar", Severity.ERROR));
		}
		
		return modalidadeAtualizada;
	}
}
