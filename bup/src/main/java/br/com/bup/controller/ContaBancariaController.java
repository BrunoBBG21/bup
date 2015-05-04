package br.com.bup.controller;

import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.ContaBancariaDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.ContaBancaria;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class ContaBancariaController {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ContaBancariaController.class);

	private final Result result;
	private final Validator validator;
	private final ContaBancariaDAO contaBancariaDAO;
	private final UsuarioSession usuarioSession;
	private final UsuarioDAO usuarioDAO;
	private final ResourceBundle i18n;
	/**
	 * @deprecated CDI eyes only
	 */
	protected ContaBancariaController() {
		this(null, null, null, null,null,null);
	}

	@Inject
	public ContaBancariaController(Result result, Validator validator,
			ContaBancariaDAO contaBancariaDAO, UsuarioSession usuarioSession,UsuarioDAO usuarioDAO,JstlLocalization local) {
		this.result = result;
		this.validator = validator;
		this.contaBancariaDAO = contaBancariaDAO;
		this.usuarioSession = usuarioSession;
		this.usuarioDAO = usuarioDAO;
		if(local!=null){
			this.i18n = local.getBundle(local.getLocale());
		}else{
			this.i18n = null;
		}
	}

	public void formulario() {
		LOGGER.debug("carregando formulario de conta");
		result.include("usuarios", usuarioDAO.buscarTodos());
		// simples formulario... futuramente receendo id para editar... ou
		// nao... reebe o id se for diferente de null ele edita 
	}

	@OpenTransaction
	public void criar(@NotNull ContaBancaria contaBancaria) {
		//se tem id buscar do banco a conta bancaria e atribuir 
		//o que nao vem da tela na entidade que veio da tela
		//se nao tem id ele vai e segue normal
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando conta bancaria");
		contaBancaria.setUsuario(usuarioSession.getUsuarioLogado());
			// validacoes...
			validarCriar(contaBancaria);
			validator.onErrorRedirectTo(this).formulario();

			// salva
			contaBancariaDAO.salvar(contaBancaria);

			result.include("success", "conta bancaria criada com sucesso.");
			result.redirectTo(IndexController.class).index();
		
	}

	private void validarCriar(ContaBancaria contaBancaria) {
		validator.validate(contaBancaria);

		// TODO validar inclusao repetida
	}
	@OpenTransaction
	public List<ContaBancaria> listar() {
		LOGGER.debug("Listando as contas bancarias");  
		return contaBancariaDAO.buscarPorUsuarioId(usuarioSession.getUsuarioLogado().getId());
	}
	@Path("/contaBancaria/apagar/{id}")
	@OpenTransaction
	public void apagar(Long id){
		try{
			contaBancariaDAO.apagarLogado(id, usuarioSession.getUsuarioLogado().getId());
			if(i18n!=null){
				result.include("success", i18n.getString("msg.success.apagar"));
			}
			result.redirectTo(this).listar();
		} catch (Exception e) {
			validator.add(new I18nMessage("Conta bancária", "msg.error.apagar")).onErrorRedirectTo(this).listar();
		}
	}
}
