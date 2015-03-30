package br.com.bup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"nome","descricao"}))
public class PublicoAlvo {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@NotNull
	private String nome;
	
	private String descricao;
	
	@ManyToMany
	private List<EspacoPropaganda> espacosPropagandas = new ArrayList<EspacoPropaganda>();
	
	//get-set-gerados-------------------------------------------------------
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<EspacoPropaganda> getEspacosPropagandas() {
		return espacosPropagandas;
	}
	public void setEspacosPropagandas(List<EspacoPropaganda> espacosPropagandas) {
		this.espacosPropagandas = espacosPropagandas;
	}
}
