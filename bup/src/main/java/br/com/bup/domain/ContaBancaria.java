package br.com.bup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


/**
 * Classe que representa as contas bancarias FISICAS do usuario. 
 */
@NamedQueries(value={
		   @NamedQuery(
				      name = "ContaBancaria.buscarPorUsuarioId",
				      query="select c from ContaBancaria c where c.usuario.id = :id")
		}) 

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"agencia","conta","banco"}))
public class ContaBancaria {
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
	private Boolean ativa = Boolean.FALSE;
	
	@ManyToOne
	private Usuario usuario;
	
	@OneToMany(mappedBy = "conta")
	private List<TransacaoBancaria> transacoes = new ArrayList<TransacaoBancaria>();

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
