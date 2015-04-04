package br.com.bup.domain;

public enum TipoUsuario {
	AGENCIA("Agencia"),
	ANUNCIANTE("Anunciante");
	
	private String descricao;
	
	private TipoUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
