package br.com.bup.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.CaseFormat;

import br.com.bup.annotation.ApenasAnunciante;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.EspacoPropagandaDAO;
import br.com.bup.dao.MidiaDAO;
import br.com.bup.dao.PublicoAlvoDAO;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.FormatoEspacoPropaganda;
import br.com.bup.domain.PublicoAlvo;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class EspacoPropagandaController {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(EspacoPropagandaController.class);

	private final Result result;
	private final Validator validator;
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	private final MidiaDAO midiaDAO;
	private final UsuarioSession usuarioSession;
	private final PublicoAlvoDAO publicoAlvoDAO;
	private final ResourceBundle i18n;
	private final Comparator<PublicoAlvo> c = new Comparator<PublicoAlvo>() {
		

		public int compare(PublicoAlvo o1, PublicoAlvo o2) {
			if (o1 == null && o2 == null)
				return 0;
			if (o1 != null && o2 == null)
				return +1;
			if (o1 == null && o2 != null)
				return -1;
			if (o1.equals(o2))
				return 0;
			if (o1.getDescricao() == null && o2.getDescricao() == null)
				return 0;
			if (o1.getDescricao() != null && o2.getDescricao() == null)
				return +1;
			if (o1.getDescricao() == null && o2.getDescricao() != null)
				return -1;
			if (o1.getDescricao().equals(o2.getDescricao()))
				return 0;

			return o1.getDescricao().compareToIgnoreCase(o2.getDescricao());

		}
	};

	/**
	 * @deprecated CDI eyes only
	 */
	protected EspacoPropagandaController() {
		this(null, null, null, null, null, null,null);
	}

	@Inject
	public EspacoPropagandaController(Result result, Validator validator,
			MidiaDAO midiaDAO, EspacoPropagandaDAO espacoPropagandaDAO,
			PublicoAlvoDAO publicoAlvoDAO, UsuarioSession usuarioSession,JstlLocalization local) {
		this.result = result;
		this.validator = validator;
		this.midiaDAO = midiaDAO;
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.publicoAlvoDAO = publicoAlvoDAO;
		this.usuarioSession = usuarioSession;
		if (local != null) {
			this.i18n = local.getBundle(local.getLocale());
		} else {
			this.i18n = null;
		}
	}

	@OpenTransaction
	@ApenasAnunciante
	public void formulario() {
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
		result.include("formatosEspaco", FormatoEspacoPropaganda.values());
		result.include("midias", midiaDAO.buscarTodos());
		List<PublicoAlvo> alvos = publicoAlvoDAO.buscarTodos();
		Collections.sort(alvos, c);
		result.include("publicosAlvos", alvos);
	}

	@OpenTransaction
	@ApenasAnunciante
	public void criar(@NotNull EspacoPropaganda espacoPropaganda) {
		try{
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando espaco: " + espacoPropaganda);

		Usuario usuario = usuarioSession.getUsuario();
		if (!(usuario instanceof Anunciante)) {
			validator.add(new SimpleMessage("error",
					"Usuario deve ser do tipo Anunciante"));
			validator.onErrorRedirectTo(this).formulario();

		} else {
			espacoPropaganda.setPertence((Anunciante) usuario);
		}
		
		// validacoes...
		validarCriar(espacoPropaganda);
		validator.onErrorRedirectTo(this).formulario();

		// salva
		espacoPropagandaDAO.salvar(espacoPropaganda);

		result.include("success", "Espaco criado com sucesso.");
		result.redirectTo(this).listar();
		} catch (Exception e) {
			validator.add(new I18nMessage("Espaço de Propaganda", "msg.error.salvar")).onErrorRedirectTo(this).listar();
		}
	}
	
	private void validarCriar(EspacoPropaganda espacoPropaganda) {
		validator.validate(espacoPropaganda);

		// TODO validar inclusao repetida
	}

	@OpenTransaction
	public List<EspacoPropaganda> listar() {
		LOGGER.debug("Listando os espacos ");
		result.include("publicosAlvos", publicoAlvoDAO.buscarTodos());
		return espacoPropagandaDAO.buscarPorAnuncianteId(usuarioSession
				.getUsuario().getId());
	}

	@Path("/espacoPropaganda/apagar/{id}")
	@OpenTransaction
	@ApenasAnunciante
	public void apagar(Long id) {
		try {
			espacoPropagandaDAO.apagarLogado(id, usuarioSession
					.getUsuarioLogado().getId());
			if (i18n != null) {
				result.include("success", i18n.getString("msg.success.apagar"));
			}
			result.redirectTo(this).listar();
		} catch (Exception e) {
			validator
					.add(new I18nMessage("Espaço de Propaganda",
							"msg.error.apagar")).onErrorRedirectTo(this)
					.listar();
		}
	}
}
