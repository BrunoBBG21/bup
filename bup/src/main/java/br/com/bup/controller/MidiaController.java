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
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.Midia;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class MidiaController {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(MidiaController.class);

	private final Result result;
	private final Validator validator;
	private final MidiaDAO midiaDAO;
	private final UsuarioSession usuarioSession;
	private final ResourceBundle i18n;

	/**
	 * @deprecated CDI eyes only
	 */
	protected MidiaController() {
		this(null, null, null, null, null);
	}

	@Inject
	public MidiaController(Result result, Validator validator,
			MidiaDAO midiaDAO, UsuarioSession usuarioSession,
			JstlLocalization local) {
		this.result = result;
		this.validator = validator;
		this.midiaDAO = midiaDAO;
		this.usuarioSession = usuarioSession;
		if (local != null) {
			this.i18n = local.getBundle(local.getLocale());
		} else {
			this.i18n = null;
		}
	}

	@ApenasAdministrador
	public void formulario() {
		LOGGER.debug("carregando formulario de midia");
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}

	@OpenTransaction
	@ApenasAdministrador
	public void criar(@NotNull String tipo) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando midia: " + tipo);

		Midia midia = new Midia();
		midia.setTipo(tipo);

		// validacoes...
		validarCriar(midia);
		validator.onErrorRedirectTo(this).formulario();

		// salva
		midiaDAO.salvar(midia);

		result.include("success", "Midia criado com sucesso.");
		result.redirectTo(IndexController.class).index();
	}

	private void validarCriar(Midia midia) {
		validator.validate(midia);

		// TODO validar inclusao repetida
	}

	@OpenTransaction
	@ApenasAdministrador
	public List<Midia> listar() {
		LOGGER.debug("Listando as midias ");

		return midiaDAO.buscarTodos();
	}

	@Path("/midia/apagar/{id}")
	@OpenTransaction
	@ApenasAdministrador
	public void apagar(Long id) {
		midiaDAO.apagarPorId(id);
		if (i18n != null) {
			result.include("success", i18n.getString("msg.success.apagar"));
		}
		result.redirectTo(this).listar();
	}
}
