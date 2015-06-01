package br.com.bup.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAdministrador;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.ContaBancariaDAO;
import br.com.bup.dao.TransacaoBancariaDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Midia;
import br.com.bup.domain.ModalidadePagamento;
import br.com.bup.domain.TransacaoBancaria;
import br.com.bup.domain.Usuario;
import br.com.bup.util.BaseWeb;
import br.com.bup.util.NotNullBeanUtilsBean;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class TransacaoBancariaController extends BaseWeb{
	private final static Logger LOGGER = LoggerFactory.getLogger(TransacaoBancariaController.class);
	
	private final TransacaoBancariaDAO transacaoBancariaDAO;
	private final UsuarioDAO usuarioDAO;
	private final ContaBancariaDAO contaDAO;
	/**
	 * @deprecated CDI eyes only
	 */
	protected TransacaoBancariaController() {
		this(null, null, null, null, null, null, null);
	}
	
	@Inject
	public TransacaoBancariaController(Result result, Validator validator,  ContaBancariaDAO contaDAO,TransacaoBancariaDAO transacaoBancariaDAO,UsuarioDAO usuarioDAO, UsuarioSession usuarioSession,
			ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.transacaoBancariaDAO = transacaoBancariaDAO;
		this.usuarioDAO = usuarioDAO;
		this.contaDAO=contaDAO;
	}
	@Path("/transacaoBancaria/apagar/{id}")
	@OpenTransaction
	public void apagar(Long id) {
		transacaoBancariaDAO.apagarLogado(id, usuarioSession.getUsuario().getId());
		addSuccessMsg("msg.success.apagar");
		result.redirectTo(this).listar();
	}
	public void formularioCreditar() {
		LOGGER.debug("carregando formulario de creditar");
		//busca as contas do usuario logado e solicitante da transferencia
		result.include("listaContas", contaDAO.buscarPorUsuarioId(usuarioSession.getUsuario().getId()));
	}
	public void formularioRetirar() {
		LOGGER.debug("carregando formulario de retirar");
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
		result.include("listaContas", contaDAO.buscarPorUsuarioId(usuarioSession.getUsuario().getId()));
	}
	@OpenTransaction
	public void criarCreditar(@NotNull TransacaoBancaria transacaoBancaria) {
		validator.onErrorRedirectTo(this).formularioCreditar(); // caso seja null...
		LOGGER.debug("criando credito: " + transacaoBancaria);
		if(transacaoBancaria!=null&&transacaoBancaria.getSaldo()!=null&&-1==transacaoBancaria.getSaldo().signum()){
			transacaoBancaria.setSaldo(transacaoBancaria.getSaldo().negate());
		}
		transacaoBancaria.setData(new Date());
		// validacoes...
		validarCreditar(transacaoBancaria);
		validator.onErrorRedirectTo(this).formularioCreditar();
		
		// salva
		transacaoBancaria = transacaoBancariaDAO.salvar(transacaoBancaria);
		
		addSuccessMsg("msg.success.transacao.bancaria.creditar.criar");
		result.redirectTo(this).listar();
	}
	@OpenTransaction
	public void criarRetirar(@NotNull TransacaoBancaria transacaoBancaria) {
		validator.onErrorRedirectTo(this).formularioCreditar(); // caso seja null...
		LOGGER.debug("criando credito: " + transacaoBancaria);
		if(transacaoBancaria!=null&&transacaoBancaria.getSaldo()!=null&&1==transacaoBancaria.getSaldo().signum()){
			transacaoBancaria.setSaldo(transacaoBancaria.getSaldo().negate());
		}
		transacaoBancaria.setData(new Date());
		// validacoes...
		validarRetirar(transacaoBancaria);
		validator.onErrorRedirectTo(this).formularioRetirar();
		
		// salva
		transacaoBancaria = transacaoBancariaDAO.salvar(transacaoBancaria);
		
		addSuccessMsg("msg.success.transacao.bancaria.retirar.criar");
		result.redirectTo(this).listar();
	}
	@OpenTransaction
	@Path("/transacaoBancaria/creditar/{id}")
	@ApenasAdministrador
	public void creditar(Long id){
		LOGGER.debug("carregando formulario de Transacao Bancaria com id: " + id);
		TransacaoBancaria transacaoBancaria = transacaoBancariaDAO.buscarPorId(id);
		validator.onErrorRedirectTo(this).listar(); // caso seja null...
		
		LOGGER.debug("atualizando Transacao Bancaria: " + transacaoBancaria);
		
		// validacoes...
		//usuario que fez a operação
		transacaoBancaria.setUsuario(usuarioSession.getUsuario());
		validarCreditar(transacaoBancaria);
		validator.onErrorRedirectTo(this).listar();
		
		// recupera os dados q nao estao no formulario
		//usuario solicitante é o da conta
		transacaoBancaria.getConta().getUsuario().setSaldo(transacaoBancaria.getConta().getUsuario().getSaldo().add(transacaoBancaria.getSaldo()));
		// atualiza
		transacaoBancaria = transacaoBancariaDAO.salvar(transacaoBancaria);
		addSuccessMsg("msg.success.transacao.bancaria.creditar");
		result.redirectTo(this).listar();
	}
	@OpenTransaction
	@Path("/transacaoBancaria/retirar/{id}")
	@ApenasAdministrador
	public void retirar(Long id){
		LOGGER.debug("carregando formulario de Transacao Bancaria com id: " + id);
		TransacaoBancaria transacaoBancaria = transacaoBancariaDAO.buscarPorId(id);
		validator.onErrorRedirectTo(this).listar(); // caso seja null...
		
		LOGGER.debug("atualizando Transacao Bancaria: " + transacaoBancaria);
		
		// validacoes...
		//usuario que fez a operação
		transacaoBancaria.setUsuario(usuarioSession.getUsuario());
		validarCreditar(transacaoBancaria);
		validator.onErrorRedirectTo(this).listar();
		
		// recupera os dados q nao estao no formulario
		//usuario solicitante é o da conta
		transacaoBancaria.getConta().getUsuario().setSaldo(transacaoBancaria.getConta().getUsuario().getSaldo().add(transacaoBancaria.getSaldo()));
		// atualiza
		transacaoBancaria = transacaoBancariaDAO.salvar(transacaoBancaria);
		addSuccessMsg("msg.success.transacao.bancaria.retirar");
		result.redirectTo(this).listar();
	}
	private void validarCreditar(TransacaoBancaria transacaoBancaria) {
		validator.validate(transacaoBancaria);
	}
	private void validarRetirar(TransacaoBancaria transacaoBancaria) {
		validator.validate(transacaoBancaria);
	}
	@OpenTransaction
	
	public List<TransacaoBancaria> listar() {
		LOGGER.debug("Listando os creditos ");
		
		return transacaoBancariaDAO.buscarSemTransferenciaUsuario(usuarioSession.getUsuario().getId());
	}
	@OpenTransaction
	public List<TransacaoBancaria> listarAdmin() {
		LOGGER.debug("Listando os creditos ");
		
		return transacaoBancariaDAO.buscarParaAprovar();
	}
	@OpenTransaction
	public List<TransacaoBancaria> listarLiberar() {
		LOGGER.debug("Listando os debitos ");
		
		return transacaoBancariaDAO.buscarParaLiberar();
	}
}
