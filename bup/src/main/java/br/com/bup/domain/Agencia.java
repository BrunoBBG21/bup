package br.com.bup.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *	Classe que representa as agencias de propaganda. 
 */
@NamedQueries(value={
		   @NamedQuery(
				      name = "Agencia.buscaGerenciados",
				      query="SELECT new map(a.id, a.nome) "
				      		+ "FROM Anunciante a "
				      		+ "WHERE a.gerenciado.id = :id"),
				      		 @NamedQuery(
				      name = "Agencia.buscaNaoGerenciados",
				      query="Select a from Agencia a where a.id not in (select an.gerenciado.id from Anunciante an where an.id = :id)")
		}) 
@Entity 
@Table
public class Agencia extends Usuario implements Serializable {
	private static final long serialVersionUID = -2729862041324410476L;

	@Column(unique=true,nullable=false)
	@NotNull
	private String cnpj;
	
	@OneToMany(mappedBy = "agencia")
	private List<LanceLeilao> lances = new ArrayList<LanceLeilao>();
	
	@OneToMany(mappedBy = "gerenciado") //nunca ponha EAGER... uma agencia pode gerenciar MILHOES 
	private List<Anunciante> gerencia = new ArrayList<Anunciante>();
	
	//get-set-gerados-------------------------------------------------------

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public List<LanceLeilao> getLances() {
		return lances;
	}
	public void setLances(List<LanceLeilao> lances) {
		this.lances = lances;
	}
	public List<Anunciante> getGerencia() {
		return gerencia;
	}
	public void setGerencia(List<Anunciante> gerencia) {
		this.gerencia = gerencia;
	}
}
