package br.com.bup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class ModalidadePagamento {
	@Id
	@GeneratedValue
	private Long id;
	private String tipo;
	private Integer maxParcela;
	private Double valorMinParcela;
	
	@ManyToOne
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
	public Double getValorMinParcela() {
		return valorMinParcela;
	}
	public void setValorMinParcela(Double valorMinParcela) {
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
