package br.com.bup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *	Classe que representa as agencias de propaganda. 
 */
@Entity
@Table
public class Agencia extends Usuario {
	private String cnpj;
	
	@OneToMany(mappedBy = "agencia")
	private List<LancesLeilao> lances = new ArrayList<LancesLeilao>();
	
	@OneToMany(mappedBy = "gerenciado")
	private List<Anunciante> gerencia = new ArrayList<Anunciante>();
	
	//get-set-gerados-------------------------------------------------------

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public List<LancesLeilao> getLances() {
		return lances;
	}
	public void setLances(List<LancesLeilao> lances) {
		this.lances = lances;
	}
	public List<Anunciante> getGerencia() {
		return gerencia;
	}
	public void setGerencia(List<Anunciante> gerencia) {
		this.gerencia = gerencia;
	}
}
