package br.com.bup.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *	Classe base para todos os usuarios no projeto. 
 */
@NamedQueries(value={
   @NamedQuery(
      name = "Usuario.buscarPorEmailSenha",
      query="from Usuario u where u.email = :email AND u.password = :password")
}) 
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements Serializable {
	private static final long serialVersionUID = -8066548068818800938L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true,nullable=false)
	@NotNull
	private String email;
	
	@Column(nullable=false)
	@NotNull
	private String password;
	
	@Column(nullable=false)
	@NotNull
	private String nome;
	
	@Column(nullable=false)
	@NotNull
	private String endereco;
	
	@Column(nullable=false)
	@NotNull
	private String cep;
	
	private String telefone;
	
	@Column(nullable=false)
	@NotNull
	private BigDecimal saldo = BigDecimal.valueOf(0);
	
	@OneToMany(mappedBy = "usuario")
	private List<ContaBancariaUsuario> contasBancarias = new ArrayList<ContaBancariaUsuario>();
	
	//get-set-gerados-------------------------------------------------------
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public List<ContaBancariaUsuario> getContasBancarias() {
		return contasBancarias;
	}
	public void setContasBancarias(List<ContaBancariaUsuario> contasBancarias) {
		this.contasBancarias = contasBancarias;
	}
}
