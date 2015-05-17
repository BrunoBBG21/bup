package br.com.bup.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.ContaBancariaDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.ContaBancaria;
import br.com.bup.util.BaseWeb;
import br.com.bup.util.NotNullBeanUtilsBean;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class ContaBancariaController extends BaseWeb {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ContaBancariaController.class);
	
	private final ContaBancariaDAO contaBancariaDAO;
	private final UsuarioDAO usuarioDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected ContaBancariaController() {
		this(null, null, null, null, null, null);
	}
	
	@Inject
	public ContaBancariaController(Result result, Validator validator, ContaBancariaDAO contaBancariaDAO,
			UsuarioSession usuarioSession, UsuarioDAO usuarioDAO, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		this.contaBancariaDAO = contaBancariaDAO;
		this.usuarioDAO = usuarioDAO;
	}
	
	public void formulario() {
		LOGGER.debug("carregando formulario de conta");
		result.include("usuarios", usuarioDAO.buscarTodos());
		// simples formulario... futuramente receendo id para editar... ou
		// nao... reebe o id se for diferente de null ele edita
	}
	
	@OpenTransaction
	public void criar(@NotNull ContaBancaria contaBancaria) {
		// se tem id buscar do banco a conta bancaria e atribuir
		// o que nao vem da tela na entidade que veio da tela
		// se nao tem id ele vai e segue normal
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando conta bancaria");
		contaBancaria.setUsuario(usuarioSession.getUsuarioLogado());
		// validacoes...
		validar(contaBancaria);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		contaBancaria = contaBancariaDAO.salvar(contaBancaria);
		addSuccessMsg("msg.success.conta_bancaria.criar");
		result.redirectTo(this).listar();
	}
	
	private void validar(ContaBancaria contaBancaria) {
		validator.validate(contaBancaria);
		
		if (!contaBancariaDAO.unikConstraintValida(contaBancaria)) {
			addErrorMsg("msg.error.salvar");
		}
		if (contaBancaria.getId() != null && 
				contaBancariaDAO.unikConstraintDiferenteId(contaBancaria.getAgencia(), contaBancaria.getConta(), contaBancaria.getBanco(), contaBancaria.getId())){
			addErrorMsg("agencia", "ja.existe.conta");
			addErrorMsg("conta", "ja.existe.conta");
			addErrorMsg("banco", "ja.existe.conta");
		}
	}
	
	@OpenTransaction
	public List<ContaBancaria> listar() {
		LOGGER.debug("Listando as contas bancarias");
		return contaBancariaDAO.buscarPorUsuarioId(usuarioSession.getUsuarioLogado().getId());
	}
	
	@Path("/contaBancaria/apagar/{id}")
	@OpenTransaction
	public void apagar(Long id) {
		contaBancariaDAO.apagarLogado(id, usuarioSession.getUsuarioLogado().getId());
		addSuccessMsg("msg.success.apagar");
		result.redirectTo(this).listar();
	}
	
	@OpenTransaction
	public void atualizar(@NotNull ContaBancaria contaBancaria) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		
		LOGGER.debug("atualizando espaço de propaganda: " + contaBancaria);
		
		// validacoes...
		validar(contaBancaria);
		validator.onErrorRedirectTo(this).editar(contaBancaria.getId());
		
		// recupera os dados q nao estao no formulario
		contaBancaria = atualizarEntidadeDoFormulario(contaBancaria);
		
		// atualiza
		contaBancaria = contaBancariaDAO.salvar(contaBancaria);
		addSuccessMsg("msg.success.conta_bancaria.atualizar");
		result.redirectTo(this).listar();
	}
	
	/**
	 * Retorna uma entidade atualizada com o banco e a passada pro metodo, mantendo os atributos do formulario da entidade
	 * passada.
	 * 
	 * @param modalidadePagamento
	 * @return Entidade atualizada.
	 */
	private ContaBancaria atualizarEntidadeDoFormulario(ContaBancaria contaBancaria) {
		ContaBancaria contaBancariaAtualizada = contaBancariaDAO.buscarPorId(contaBancaria.getId());
		
		try {
			NotNullBeanUtilsBean.getInstance().copyProperties(contaBancariaAtualizada, contaBancaria);
		} catch (InvocationTargetException | IllegalAccessException e) {
			addErrorMsg("msg.error.editar");
		}
		
		return contaBancariaAtualizada;
	}
	
	@OpenTransaction
	@Path("/contaBancaria/editar/{id}")
	public void editar(Long id) {
		LOGGER.debug("carregando formulario de conta bancaria com id: " + id);
		ContaBancaria contaBancaria = contaBancariaDAO.buscarPorId(id);
		
		result.include("contaBancaria", contaBancaria);
		formulario();
	}
}
