package br.com.bup.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


/**
 * Classe que representa as contas bancarias FISICAS do usuario. 
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"agencia","conta","banco"}))
public class ContaBancariaUsuario {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable=false)
	@NotNull
	private String agencia;
	@Column(nullable=false)
	@NotNull
	private String conta;
	@Column(nullable=false)
	@NotNull
	private String banco;
	@Column(nullable=false)
	@NotNull
	private Boolean ativa;
	
	@ManyToOne
	private Usuario usuario;

	//get-set-gerados-------------------------------------------------------
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public Boolean getAtiva() {
		return ativa;
	}
	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
