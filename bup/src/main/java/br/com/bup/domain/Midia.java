package br.com.bup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Midia {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true,nullable=false)
	@NotNull
	private String tipo;
	
	@OneToMany(mappedBy = "midia")
	private List<ModalidadePagamento> modalidadesPagamento = new ArrayList<ModalidadePagamento>();
	
	@OneToMany(mappedBy = "midia")
	private List<EspacoPropaganda> espacosPropaganda = new ArrayList<EspacoPropaganda>();
	
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
	public List<ModalidadePagamento> getModalidadesPagamento() {
		return modalidadesPagamento;
	}
	public void setModalidadesPagamento(
			List<ModalidadePagamento> modalidadesPagamento) {
		this.modalidadesPagamento = modalidadesPagamento;
	}
	public List<EspacoPropaganda> getEspacosPropaganda() {
		return espacosPropaganda;
	}
	public void setEspacosPropaganda(List<EspacoPropaganda> espacosPropaganda) {
		this.espacosPropaganda = espacosPropaganda;
	}
}
