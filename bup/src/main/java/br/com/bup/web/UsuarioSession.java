package br.com.bup.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import br.com.bup.domain.Anunciante;
import br.com.bup.domain.Usuario;

@SessionScoped
public class UsuarioSession implements Serializable {
	private static final long serialVersionUID = 5135507409377401886L;
	private Usuario usuarioLogado;
	private Anunciante usuarioGerenciado;

	public void logar(Usuario usuario) {
		this.usuarioLogado = usuario;
	}
	
	public void deslogar() {
		this.usuarioLogado = null;
	}

	public Boolean isLogado() {
		return usuarioLogado != null;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
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
}
