package br.com.bup.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAdministrador;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.TransacaoBancariaDAO;
import br.com.bup.domain.Midia;
import br.com.bup.domain.TransacaoBancaria;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class TransacaoBancariaController extends BaseWeb{
	private final static Logger LOGGER = LoggerFactory.getLogger(TransacaoBancariaController.class);
	
	private final TransacaoBancariaDAO transacaoBancariaDAO;
	/**
	 * @deprecated CDI eyes only
	 */
	protected TransacaoBancariaController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public TransacaoBancariaController(Result result, Validator validator, TransacaoBancariaDAO transacaoBancariaDAO, UsuarioSession usuarioSession,
			ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.transacaoBancariaDAO = transacaoBancariaDAO;
	}
	@ApenasAdministrador
	public void formularioCreditar() {
		LOGGER.debug("carregando formulario de creditar");
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}
	public void formularioRetirar() {
		LOGGER.debug("carregando formulario de retirar");
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}
	@OpenTransaction
	@ApenasAdministrador
	public void criarCreditar(@NotNull TransacaoBancaria transacaoBancaria) {
		validator.onErrorRedirectTo(this).formularioCreditar(); // caso seja null...
		LOGGER.debug("criando credito: " + transacaoBancaria);
		
		// validacoes...
		validarCreditar(transacaoBancaria);
		validator.onErrorRedirectTo(this).formularioCreditar();
		
		// salva
		transacaoBancaria = transacaoBancariaDAO.salvar(transacaoBancaria);
		
		addSuccessMsg("msg.success.transacao.bancaria.creditar.criar");
		result.redirectTo(this).listar();
	}	
	private void validarCreditar(TransacaoBancaria transacaoBancaria) {
		validator.validate(transacaoBancaria);
		if(transacaoBancaria!=null&&transacaoBancaria.getSaldo()!=null&&-1==transacaoBancaria.getSaldo().signum()){
			transacaoBancaria.setSaldo(transacaoBancaria.getSaldo().negate());
		}
	}
	private void validarRetirar(TransacaoBancaria transacaoBancaria) {
		validator.validate(transacaoBancaria);
		if(transacaoBancaria!=null&&transacaoBancaria.getSaldo()!=null&&1==transacaoBancaria.getSaldo().signum()){
			transacaoBancaria.setSaldo(transacaoBancaria.getSaldo().negate());
		}
	}
	@OpenTransaction
	@ApenasAdministrador
	public List<TransacaoBancaria> listar() {
		LOGGER.debug("Listando os creditos ");
		
		return transacaoBancariaDAO.buscarTodos();
	}
}
