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
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
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
	
	@Inject
	private UsuarioApplication usuarioApplication;
	
	@Inject
	private LeilaoApplication leilaoApplication;
	
	@Inject
	private LeilaoDAO leilaoDAO;

	@Override
	public void update(Observable arg0, Object arg1) {
		LOGGER.debug("updateupdateupdateupdateupdate: " + arg0.toString());
	}
	
	public void atualizarLeiloesInscritosObserver() {
		idsLeiloesObserver = usuarioLogado == null ? new ArrayList<Long>() : leilaoDAO.buscarIdsLeiloesObservarPorUsuarioId(usuarioLogado.getId());
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
	 * Verifica se o usuario logado está sendo gerenciado.
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
}
