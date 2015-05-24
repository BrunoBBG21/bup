package br.com.bup.domain;

import java.util.Date;

/**
 * Classe com as notificacoes para o usuario. 
 */
public class Notificacao { //ATENCAO! posteriormente se tivermos tempo... persistir no banco...
	private Long id = (new Date()).getTime(); //temporario...
//	private Usuario usuario;
	private Boolean lido = Boolean.FALSE;
	private String mensagem;
	
	//caso seja uma msg de leilao...
	private Long leilaoId;

	//get-set-gerados--------------------------------------------------------------------------------
	
	public Boolean getLido() {
		return lido;
	}
	public void setLido(Boolean lido) {
		this.lido = lido;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Long getLeilaoId() {
		return leilaoId;
	}
	public void setLeilaoId(Long leilaoId) {
		this.leilaoId = leilaoId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	} 
}
