package br.com.bup.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.dao.LeilaoDAO;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.LanceLeilao;
import br.com.bup.domain.Leilao;
import br.com.bup.domain.Notificacao;
import br.com.bup.domain.Usuario;

@SessionScoped
// para pegar na jsp
@Named("usuarioSession")
public class UsuarioSession implements Serializable, Observer {
	private static final long serialVersionUID = 5135507409377401886L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioSession.class);
	private Usuario usuarioLogado;
	private Anunciante usuarioGerenciado;
	private Date dataUltimoRequest = new Date();
	private List<Long> idsLeiloesObserver = new ArrayList<Long>();
	private final List<Notificacao> notificacoesNaoLidas = new ArrayList<Notificacao>();
	
	@Inject
	private UsuarioApplication usuarioApplication;
	@Inject
	private LeilaoApplication leilaoApplication;
	
	@Inject
	private LeilaoDAO leilaoDAO;
	@Inject
	private UsuarioDAO usuarioDAO;
	
//	@Inject
//	private ResourceBundle i18n; //esta tendo algum erro de injecao...
	
	public Notificacao getNotificacao(Long notificacaoId) {
		Notificacao value = null;
		for (Notificacao notificacao : notificacoesNaoLidas) {
			if (notificacao.getId().equals(notificacaoId)) {
				value = notificacao;
				break;
			}
		}
		return value;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		updateLeilao(arg0);
	}
	
	/**
	 * Deve: qunado mudar o estado deve anunciar.. (comecou o leilao, terminou...) quando o usuario logado nao estiver mais
	 * ganhando o leilao
	 * 
	 * @param arg0
	 */
	private void updateLeilao(Observable arg0) {
		if (!(arg0 instanceof Leilao)) {
			return;
		}
		
		Leilao leilao = (Leilao) arg0;
		if (leilao.isEstadoEmAndamento()) { //acabou de iniciar o leilao
			notificarInicioLeilao(leilao);
			
		} else if (leilao.isEstadoAguardando()) { //durante a fase do leilao...
			if (verificarLanceSuperado(leilao)) {
				notificarLanceSuperado(leilao);
			}
		}
	}
	
	private boolean verificarLanceSuperado(Leilao leilao) {
		LanceLeilao penultimoLance = leilao.getPenultimoLance();
		LanceLeilao ultimoLance = leilao.getUltimoLance();
		
		Anunciante penulLancAnunciante = penultimoLance == null ? null : penultimoLance.getAnunciante();
		Agencia penulLancAgencia = penultimoLance == null ? null : penultimoLance.getAgencia();
		Anunciante ultLancAnunciante = ultimoLance.getAnunciante();
		Agencia ultLancAgencia = ultimoLance.getAgencia();
		
		return ((penulLancAnunciante != null && penulLancAnunciante.getId().equals(usuarioLogado.getId())) 
				 || (penulLancAgencia != null && penulLancAgencia.getId().equals(usuarioLogado.getId())))
				&& !ultLancAnunciante.getId().equals(usuarioLogado.getId())
				&& (ultLancAgencia == null || !ultLancAgencia.getId().equals(usuarioLogado.getId()));
	}
	
	private void notificarLanceSuperado(Leilao leilao) {
		LOGGER.debug("Notificando lance superado...");
		Notificacao notificacao = new Notificacao();
		notificacao.setMensagem("Alguem deu um lance maior que o seu!");
//		notificacao.setMensagem(i18n.getString("notificacao.leilao.lance.superado"));
		notificacao.setLeilaoId(leilao.getId());
		notificacoesNaoLidas.add(notificacao);
	}
	
	private void notificarInicioLeilao(Leilao leilao) {
		LOGGER.debug("Notificando inicio do leilao...");
		Notificacao notificacao = new Notificacao();
		notificacao.setMensagem("Leilao iniciado!");
//		notificacao.setMensagem(i18n.getString("notificacao.leilao.iniciado"));
		notificacao.setLeilaoId(leilao.getId());
		notificacoesNaoLidas.add(notificacao);
	}
	
	public void atualizarLeiloesInscritosObserver() {
		idsLeiloesObserver = usuarioLogado == null ? new ArrayList<Long>() : leilaoDAO
				.buscarIdsLeiloesObservarPorUsuarioId(usuarioLogado.getId());
		leilaoApplication.atualizarObserver(this);
	}
	
	public void atualizarDataUltimoRequest() {
		dataUltimoRequest = new Date();
	}
	
	public Date getDataUltimoRequest() {
		return dataUltimoRequest;
	}
	
	public void logar(Usuario usuario) {
		usuarioLogado = usuario;
		usuarioGerenciado = null;
		usuarioApplication.addUsuarioLogado(this);
		atualizarLeiloesInscritosObserver();
	}
	
	public void deslogar() {
		usuarioLogado = null;
		usuarioGerenciado = null;
		usuarioApplication.removerUsuarioLogado(this);
		atualizarLeiloesInscritosObserver();
	}
	
	public Boolean podeDeletar() {
		
		if (this != null && this.getUsuarioLogado() != null && usuarioDAO != null)
			return usuarioDAO.podeDeletarPorId(this.getUsuarioLogado().getId());
		else
			return true;
		
	}
	
	public Boolean isAdministrador() {
		return usuarioLogado != null && usuarioLogado.getId() == 1;
	}
	
	public Boolean isLogado() {
		return usuarioLogado != null;
	}
	
	public Boolean isLogadoAgencia() {
		return usuarioLogado != null && usuarioLogado instanceof Agencia;
	}
	
	public Boolean isLogadoAnunciante() {
		return usuarioLogado != null && usuarioLogado instanceof Anunciante;
	}
	
	public Boolean isGerenciando() {
		return usuarioGerenciado != null;
	}
	
	public Long getIdGerenciado() {
		return usuarioGerenciado != null ? usuarioGerenciado.getId() : null;
	}
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	public Agencia getUsuarioLogadoComoAgencia() {
		return (Agencia) usuarioLogado;
	}
	
	public String getNomeUsuarioLogado() {
		return usuarioLogado == null ? "" : usuarioLogado.getNome();
	}
	
	public void gerenciar(Anunciante anunciante) {
		this.usuarioGerenciado = anunciante;
	}
	
	public void desGerenciar() {
		this.usuarioGerenciado = null;
	}
	
	/**
	 * @return Usuario - Anunciante gerenciado, caso nulo, usuario Logado.
	 */
	public Usuario getUsuario() {
		return usuarioGerenciado == null ? usuarioLogado : usuarioGerenciado;
	}
	
	/**
	 * Verifica se o usuario logado est� sendo gerenciado.
	 * 
	 * @return Boolean
	 */
	public Boolean isUsuarioLogadoGerenciado() {
		Boolean value = Boolean.FALSE;
		
		if (usuarioLogado != null && usuarioLogado instanceof Anunciante) {
			Anunciante usuarioLogadoAnunciante = (Anunciante) usuarioLogado;
			value = usuarioLogadoAnunciante.getGerenciado() != null;
		}
		
		return value;
	}
	
	/**
	 * Tempo ocioso em minutos.
	 * 
	 * @return
	 */
	public Long getTempoOcioso() {
		return ((new Date()).getTime() - dataUltimoRequest.getTime()) / 1000 / 60;
	}
	
	public List<Long> getIdsLeiloesObserver() {
		return idsLeiloesObserver;
	}

	public List<Notificacao> getNotificacoesNaoLidas() {
		return notificacoesNaoLidas;
	}
}
