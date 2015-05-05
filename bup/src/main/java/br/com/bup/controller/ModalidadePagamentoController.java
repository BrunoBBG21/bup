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
import br.com.bup.domain.Midia;
import br.com.bup.domain.ModalidadePagamento;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class ModalidadePagamentoController {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(ModalidadePagamentoController.class);

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
		this(null, null, null, null,null,null);
	}

	@Inject
	public ModalidadePagamentoController(Result result, Validator validator,
			ModalidadePagamentoDAO modalidadePagamentoDAO, MidiaDAO midiaDAO,
			UsuarioSession usuarioSession,JstlLocalization local) {
		this.result = result;
		this.validator = validator;
		this.modalidadePagamentoDAO = modalidadePagamentoDAO;
		this.midiaDAO = midiaDAO;
		if(local!=null){
			this.i18n = local.getBundle(local.getLocale());
		}else{
			this.i18n = null;
		}
		this.usuarioSession = usuarioSession;
	}
	@OpenTransaction
	public void formulario() {
		LOGGER.debug("carregando formulario de modalidade de pagamento.");
		result.include("listaMidias",midiaDAO.buscarTodos());
	}
	@OpenTransaction
	public List<ModalidadePagamento> listar() {
		LOGGER.debug("Listando as modalidades de pagamento. ");
		
		return modalidadePagamentoDAO.buscarTodos();
	}
	@OpenTransaction
	public void criar(@NotNull ModalidadePagamento modalidadePagamento) {
		try{
		validator.onErrorRedirectTo(this).formulario(); //caso seja null...
		LOGGER.debug("criando modalidade de pagamento: " + modalidadePagamento);
		
		//validacoes...
		validarCriar(modalidadePagamento);
        validator.onErrorRedirectTo(this).formulario();		
		
        //salva
        modalidadePagamentoDAO.salvar(modalidadePagamento);
		
		result.include("success", "Modalidade de pagamento criada com sucesso.");
		result.redirectTo(IndexController.class).index();
	} catch (Exception e) {
		validator.add(new I18nMessage("Modalidade de Pagamento", "msg.error.apagar"))
				.onErrorRedirectTo(this).listar();
	}
	}
	private void validarCriar(ModalidadePagamento modalidadePagamento) {
		validator.validate(modalidadePagamento);
	}
	@Path("/modalidadePagamento/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		try {
			modalidadePagamentoDAO.apagarPorId(id);
			if(i18n!=null){
				result.include("success", i18n.getString("msg.success.apagar"));
			}
			result.redirectTo(this).listar();
		} catch (Exception e) {
			validator.add(new I18nMessage("Modalidade de Pagamento", "msg.error.apagar")).onErrorRedirectTo(this).listar();
		}
		
	}
}
