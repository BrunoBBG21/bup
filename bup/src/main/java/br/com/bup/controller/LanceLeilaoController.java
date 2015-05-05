package br.com.bup.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.AgenciaDAO;
import br.com.bup.dao.AnuncianteDAO;
import br.com.bup.dao.LanceLeilaoDAO;
import br.com.bup.dao.LeilaoDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.LanceLeilao;
import br.com.bup.domain.Leilao;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.JstlLocalization;
import br.com.caelum.vraptor.validator.I18nMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class LanceLeilaoController {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(LanceLeilaoController.class);

	private final Result result;
	private final Validator validator;
	private final LanceLeilaoDAO lanceLeilaoDAO;
	private final LeilaoDAO leilaoDAO;
	private final AnuncianteDAO anuncianteDAO;
	private final AgenciaDAO agenciaDAO;
	private final UsuarioSession usuarioSession;
	private final UsuarioDAO usuarioDAO;
	private final ResourceBundle i18n;


	/**
	 * @deprecated CDI eyes only
	 */
	protected LanceLeilaoController() {
		this(null, null, null, null, null, null,null, null,null);
	}

	@Inject
	public LanceLeilaoController(Result result, Validator validator,
			LanceLeilaoDAO lanceLeilaoDAO,LeilaoDAO leilaoDAO,AgenciaDAO agenciaDAO,
			AnuncianteDAO anuncianteDAO, UsuarioSession usuarioSession,
			UsuarioDAO usuarioDAO,JstlLocalization local) {
		this.result = result;
		this.validator = validator;
		this.lanceLeilaoDAO = lanceLeilaoDAO;
		this.leilaoDAO = leilaoDAO;
		this.agenciaDAO = agenciaDAO;
		this.anuncianteDAO = anuncianteDAO;
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
		result.include("anunciantes", anuncianteDAO.buscarTodos());
		result.include("agencias", agenciaDAO.buscarTodos());
		result.include("leiloes", leilaoDAO.buscarTodos());
		// simples formulario... futuramente receendo id para editar... ou
		// nao...
	}

	@OpenTransaction
	public void criar(@NotNull BigDecimal valor, @NotNull Date data,
			@NotNull Anunciante anunciante,@NotNull Leilao leilao,Boolean vencedor, Agencia agencia) {
		try{
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		LOGGER.debug("criando Lance Leilao do espaco propaganda:valor - " + valor
				+ ",data  - " + data + ", anunciante - " + anunciante.getNome() + ", Leilao - "
				+ leilao.getEspacoPropaganda().getDescricao());
		
		LanceLeilao lanceLeilao = new LanceLeilao();
		lanceLeilao.setAnunciante(anunciante);
		lanceLeilao.setAgencia(agencia);
		lanceLeilao.setData(data);
		lanceLeilao.setLeilao(leilao);
		lanceLeilao.setValor(valor);
		lanceLeilao.setVencedor(vencedor);
		// validacoes...
			validarCriar(lanceLeilao);
			validator.onErrorRedirectTo(this).formulario();

			// salva
			lanceLeilaoDAO.salvar(lanceLeilao);

			result.include("success", "lance leilao criado com sucesso.");
			result.redirectTo(IndexController.class).index();
		} catch (Exception e) {
			validator.add(new I18nMessage("Lance do leilão", "msg.error.salvar")).onErrorRedirectTo(IndexController.class).index();
		}
	}

	private void validarCriar(LanceLeilao lanceLeilao) {
		validator.validate(lanceLeilao);

		// TODO validar inclusao repetida
	}
}
