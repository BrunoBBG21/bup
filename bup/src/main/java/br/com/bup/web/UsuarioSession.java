package br.com.bup.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.Usuario;

@SessionScoped
@Named("usuarioSession") //para pegar na jsp
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

	public Boolean isLogadoAgencia() {
		return usuarioLogado != null && usuarioLogado instanceof Agencia;
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
}
