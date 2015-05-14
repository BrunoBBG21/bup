package br.com.bup.domain;

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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "dataInicio", "dataFim", "anunciante_id", "espacoPropaganda_id" }))
public class HistoricoAluguelEspaco {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column(nullable = false)
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dataFim;
	
	@ManyToOne
	@JoinColumn(name = "anunciante_id", nullable = false)
	@NotNull
	private Anunciante anunciante;
	
	@ManyToOne
	@JoinColumn(name = "espacoPropaganda_id", nullable = false)
	@NotNull
	private EspacoPropaganda espacoPropaganda;
	
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
	
	public Anunciante getAnunciante() {
		return anunciante;
	}
	
	public void setAnunciante(Anunciante anunciante) {
		this.anunciante = anunciante;
	}
	
	public EspacoPropaganda getEspacoPropaganda() {
		return espacoPropaganda;
	}
	
	public void setEspacoPropaganda(EspacoPropaganda espacoPropaganda) {
		this.espacoPropaganda = espacoPropaganda;
	}
}
