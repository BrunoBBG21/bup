package br.com.bup.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.domain.Notificacao;
import br.com.bup.util.BaseWeb;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class NotificacaoController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(NotificacaoController.class);
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected NotificacaoController() {
		this(null, null, null, null);
	}
	
	@Inject
	public NotificacaoController(Result result, Validator validator, UsuarioSession usuarioSession, ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
	}
	
	public void abrirNotificacao(Long notificacaoId) {
		LOGGER.debug("Abrindo notificacao: " + notificacaoId);
		
		Notificacao notificacao = usuarioSession.getNotificacao(notificacaoId);
		notificacao.setLido(true);
		usuarioSession.getNotificacoesNaoLidas().remove(notificacao);
		if (notificacao.getLeilaoId() != null) {
			result.redirectTo(LeilaoController.class).leilao(notificacao.getLeilaoId());
			
		} else {
			result.nothing();
		}
	}
	
	public void marcarTodasLidas() {
		usuarioSession.getNotificacoesNaoLidas().clear();
		result.nothing();
	}
}
