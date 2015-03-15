package br.com.bup.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class LancesLeilao {
	@Id
	@GeneratedValue
	private Long id;
	private Double valor; //talvez seja uma boa mudar para bigdecimal...
	
	@Temporal(TemporalType.DATE)
	private Date data;
	private Boolean vencedor = Boolean.FALSE;
	
	@ManyToOne
	private Agencia agencia;
	
	@ManyToOne
	private Anunciante anunciante;
	
	@ManyToOne
	private Leilao leilao;
	
	//get-set-gerados-------------------------------------------------------
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Boolean getVencedor() {
		return vencedor;
	}
	public void setVencedor(Boolean vencedor) {
		this.vencedor = vencedor;
	}
	public Agencia getAgencia() {
		return agencia;
	}
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	public Anunciante getAnunciante() {
		return anunciante;
	}
	public void setAnunciante(Anunciante anunciante) {
		this.anunciante = anunciante;
	}
	public Leilao getLeilao() {
		return leilao;
	}
	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}
}
