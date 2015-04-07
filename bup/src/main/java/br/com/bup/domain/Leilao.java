package br.com.bup.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"dataInicio","dataFim","modalidadePagamento_id","espacoPropaganda_id"}))
public class Leilao {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column(nullable=false)
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	private BigDecimal reserva;
	
	private BigDecimal inscricao;
	
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name="modalidadePagamento_id",nullable=false)
	@NotNull
	private ModalidadePagamento modalidadePagamento;
	
	@ManyToOne
	@JoinColumn(name="espacoPropaganda_id",nullable=false)
	@NotNull
	private EspacoPropaganda espacoPropaganda;
	
	@OneToMany(mappedBy = "leilao")
	private List<LanceLeilao> lances = new ArrayList<LanceLeilao>();
	
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
	public BigDecimal getReserva() {
		return reserva;
	}
	public void setReserva(BigDecimal reserva) {
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
	public List<LanceLeilao> getLances() {
		return lances;
	}
	public void setLances(List<LanceLeilao> lances) {
		this.lances = lances;
	}
	public List<Anunciante> getInscritos() {
		return inscritos;
	}
	public void setInscritos(List<Anunciante> inscritos) {
		this.inscritos = inscritos;
	}
	public BigDecimal getInscricao() {
		return inscricao;
	}
	public void setInscricao(BigDecimal inscricao) {
		this.inscricao = inscricao;
	}
}
