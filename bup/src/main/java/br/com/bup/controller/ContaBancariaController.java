package br.com.bup.controller;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.ContaBancariaDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.ContaBancaria;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
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

	/**
	 * @deprecated CDI eyes only
	 */
	protected ContaBancariaController() {
		this(null, null, null, null,null);
	}

	@Inject
	public ContaBancariaController(Result result, Validator validator,
			ContaBancariaDAO contaBancariaDAO, UsuarioSession usuarioSession,UsuarioDAO usuarioDAO) {
		this.result = result;
		this.validator = validator;
		this.contaBancariaDAO = contaBancariaDAO;
		this.usuarioSession = usuarioSession;
		this.usuarioDAO = usuarioDAO;
	}

	public void formulario() {
		LOGGER.debug("carregando formulario de conta");
		result.include("usuarios", usuarioDAO.buscarTodos());
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}

	@OpenTransaction
	public void criar(@NotNull String agencia,@NotNull String conta,@NotNull String banco,@NotNull Boolean ativa,Usuario usuario) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando conta bancaria: agencia - " + agencia+ ", conta - "+conta+ ", banco - "+banco+ ", ativa - "+ativa);
		
			ContaBancaria contaBancaria = new ContaBancaria();
			contaBancaria.setAgencia(agencia);
			contaBancaria.setConta(conta);
			contaBancaria.setBanco(banco);
			contaBancaria.setAtiva(ativa);
			contaBancaria.setUsuario(usuario);
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
}
