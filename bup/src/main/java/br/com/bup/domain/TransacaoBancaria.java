package br.com.bup.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
@Entity
@Table
public class TransacaoBancaria implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable=false)
	@NotNull	
	private ContaBancaria conta;
	@Column(nullable=false)
	private Usuario usuario;
	@Column(nullable=false)
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;
	@Column(nullable=false)
	@NotNull
	private BigDecimal saldo = BigDecimal.valueOf(0);
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the conta
	 */
	public ContaBancaria getConta() {
		return conta;
	}
	/**
	 * @param conta the conta to set
	 */
	public void setConta(ContaBancaria conta) {
		this.conta = conta;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
	/**
	 * @return the saldo
	 */
	public BigDecimal getSaldo() {
		return saldo;
	}
	/**
	 * @param saldo the saldo to set
	 */
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
}
