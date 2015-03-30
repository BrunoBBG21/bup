package br.com.bup.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"maxParcela","valorMinParcela","midia_id"}))
public class ModalidadePagamento {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@NotNull
	private String tipo;
	
	@Column(nullable=false)
	@NotNull
	private Integer maxParcela;
	
	@Column(nullable=false)
	@NotNull
	private BigDecimal valorMinParcela;
	
	@ManyToOne
	@JoinColumn(name="midia_id",nullable=false)
	@NotNull
	private Midia midia;
	
	@OneToMany(mappedBy = "modalidadePagamento")
	private List<Leilao> leiloes = new ArrayList<Leilao>();
	
	//get-set-gerados-------------------------------------------------------
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getMaxParcela() {
		return maxParcela;
	}
	public void setMaxParcela(Integer maxParcela) {
		this.maxParcela = maxParcela;
	}
	public BigDecimal getValorMinParcela() {
		return valorMinParcela;
	}
	public void setValorMinParcela(BigDecimal valorMinParcela) {
		this.valorMinParcela = valorMinParcela;
	}
	public Midia getMidia() {
		return midia;
	}
	public void setMidia(Midia midia) {
		this.midia = midia;
	}
	public List<Leilao> getLeiloes() {
		return leiloes;
	}
	public void setLeiloes(List<Leilao> leiloes) {
		this.leiloes = leiloes;
	}
}
