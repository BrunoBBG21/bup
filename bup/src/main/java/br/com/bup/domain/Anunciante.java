package br.com.bup.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Classe que representa os Anunciantes de espa�o e de propaganda.
 */
@Entity
@Table
public class Anunciante extends Usuario implements Serializable {
	private static final long serialVersionUID = 3089548922425561276L;
	
	@Column(unique = true, nullable = false)
	@NotNull
	private String cpf;
	
	@ManyToOne
	private Agencia gerenciado;
	
	@OneToMany(mappedBy = "anunciante")
	private List<LanceLeilao> lances = new ArrayList<LanceLeilao>();
	
	@ManyToMany
	private List<Leilao> leiloesInscrito = new ArrayList<Leilao>();
	
	@OneToMany(mappedBy = "pertence")
	private List<EspacoPropaganda> espacosPossuidos = new ArrayList<EspacoPropaganda>();
	
	@OneToMany(mappedBy = "alugador")
	private List<EspacoPropaganda> espacosAlugados = new ArrayList<EspacoPropaganda>();
	
	//get-set-gerados-------------------------------------------------------
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public Agencia getGerenciado() {
		return gerenciado;
	}
	
	public void setGerenciado(Agencia gerenciado) {
		this.gerenciado = gerenciado;
	}
	
	public List<Leilao> getLeiloesInscrito() {
		return leiloesInscrito;
	}
	
	public void setLeiloesInscrito(List<Leilao> leiloesInscrito) {
		this.leiloesInscrito = leiloesInscrito;
	}
	
	public List<EspacoPropaganda> getEspacosPossuidos() {
		return espacosPossuidos;
	}
	
	public void setEspacosPossuidos(List<EspacoPropaganda> espacosPossuidos) {
		this.espacosPossuidos = espacosPossuidos;
	}
	
	public List<EspacoPropaganda> getEspacosAlugados() {
		return espacosAlugados;
	}
	
	public void setEspacosAlugados(List<EspacoPropaganda> espacosAlugados) {
		this.espacosAlugados = espacosAlugados;
	}
	
	public List<LanceLeilao> getLances() {
		return lances;
	}
	
	public void setLances(List<LanceLeilao> lances) {
		this.lances = lances;
	}
}
