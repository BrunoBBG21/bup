package br.com.bup.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.Usuario;

@SessionScoped
@Named("usuarioSession")
// para pegar na jsp
public class UsuarioSession implements Serializable {
	private static final long serialVersionUID = 5135507409377401886L;
	private Usuario usuarioLogado;
	private Anunciante usuarioGerenciado;
	
	public void logar(Usuario usuario) {
		this.usuarioLogado = usuario;
		usuarioGerenciado = null;
	}
	
	public void deslogar() {
		this.usuarioLogado = null;
		usuarioGerenciado = null;
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
}
