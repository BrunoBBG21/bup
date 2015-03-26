package br.com.bup.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import br.com.bup.domain.Usuario;

@SessionScoped
public class UsuarioSession implements Serializable {
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
