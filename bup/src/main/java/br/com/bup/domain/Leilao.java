package br.com.bup.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Leilao {
	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	private Double reserva;
	private Boolean ativo;
	
	@ManyToOne
	private ModalidadePagamento modalidadePagamento;
	
	@ManyToOne
	private EspacoPropaganda espacoPropaganda;
	
	@OneToMany(mappedBy = "leilao")
	private List<LancesLeilao> lances = new ArrayList<LancesLeilao>();
	
	@ManyToMany
	private List<Anunciante> inscritos = new ArrayList<Anunciante>();
	
	//get-set-gerados-------------------------------------------------------
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public Double getReserva() {
		return reserva;
	}
	public void setReserva(Double reserva) {
		this.reserva = reserva;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public ModalidadePagamento getModalidadePagamento() {
		return modalidadePagamento;
	}
	public void setModalidadePagamento(ModalidadePagamento modalidadePagamento) {
		this.modalidadePagamento = modalidadePagamento;
	}
	public EspacoPropaganda getEspacoPropaganda() {
		return espacoPropaganda;
	}
	public void setEspacoPropaganda(EspacoPropaganda espacoPropaganda) {
		this.espacoPropaganda = espacoPropaganda;
	}
	public List<LancesLeilao> getLances() {
		return lances;
	}
	public void setLances(List<LancesLeilao> lances) {
		this.lances = lances;
	}
	public List<Anunciante> getInscritos() {
		return inscritos;
	}
	public void setInscritos(List<Anunciante> inscritos) {
		this.inscritos = inscritos;
	}
}
