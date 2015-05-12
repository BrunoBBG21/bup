package br.com.bup.web;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.Usuario;

@SessionScoped
// para pegar na jsp
@Named("usuarioSession")
public class UsuarioSession implements Serializable {
	private static final long serialVersionUID = 5135507409377401886L;
	private Usuario usuarioLogado;
	private Anunciante usuarioGerenciado;
	private Date dataUltimoRequest = new Date();
	
	@Inject
	private UsuarioApplication usuarioApplication;
	
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
	}
	
	public void deslogar() {
		usuarioLogado = null;
		usuarioGerenciado = null;
		usuarioApplication.removerUsuarioLogado(this);
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
	 * @return
	 */
	public Long getTempoOcioso() {
		return ((new Date()).getTime() - dataUltimoRequest.getTime()) / 1000 / 60;
	}
}
