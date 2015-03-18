package br.com.bup.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"valor","data","anunciante_id","leilao_id"}))
public class LancesLeilao {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@NotNull
	private BigDecimal valor; //talvez seja uma boa mudar para bigdecimal...
	
	@Column(nullable=false)
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private Boolean vencedor = Boolean.FALSE;
	
	@ManyToOne
	private Agencia agencia;
	
	@ManyToOne
	@JoinColumn(name="anunciante_id",nullable=false)
	@NotNull
	private Anunciante anunciante;
	
	@ManyToOne
	@JoinColumn(name="leilao_id",nullable=false)
	@NotNull
	private Leilao leilao;
	
	//get-set-gerados-------------------------------------------------------
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
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
	public Leilao getLeilao() {
		return leilao;
	}
	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
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
}
