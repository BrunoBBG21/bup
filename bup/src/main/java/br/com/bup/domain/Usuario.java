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
 * Classe base para todos os usuarios no projeto.
 */
//@formatter:off
@NamedQueries(value = {
		@NamedQuery(name = "Usuario.buscarPorEmailSenha",
				query = "select u "
						+ "from Usuario u "
						+ "where "
						+ "		u.email = :email "
						+ "	AND u.password = :password"),
						
		@NamedQuery(name = "Usuario.existeComEmail", 
		        query = "select case when (count(u) > 0) then true else false end "
		        		+ "from Usuario u "
		        		+ "where u.email = :email"),
		        		
		@NamedQuery(name = "Usuario.existeComEmailDiferenteId",
				query = "select case when (count(u) > 0) then true else false end "
						+ "from Usuario u "
						+ "where "
						+ "		u.email = :email "
						+ "	and u.id <> :id"),
				
		@NamedQuery(name = "Usuario.unikConstraintDiferenteId",
				query = "select case when (count(u) > 0) then true else false end "
						+ "from Usuario u "
						+ "where "
						+ "		("
						+ "			u.cpf = :cpfcnpj "
						+ "		or u.cnpj = :cpfcnpj"
						+ "		) "
						+ "	and u.id <> :id"),
		@NamedQuery(name = "Usuario.podeDeletarPorId",
						query = "select case when (count(u) = 0) then true else false end "
								+ "from Usuario u "
								+ "where "
								/*+ "		u.id not in (select a.gerencia.id "
					      		+ "					from Agencia a "
					      		+ "					where a.id = :id) and "*/

					      		+ "		u.id not in (select an.gerenciado.id "
					      		+ "					from Anunciante an "
					      		+ "					where an.gerenciado.id = :id) or "
					      		
								+ "		u.id not in (select c.usuario.id "
								+ "					from ContaBancaria c "
								+ "					where c.usuario.id = :id) or "
								
								+ "		u.id not in (select t.usuario.id "
								+ "					from TransacaoBancaria t "
								+ "					where t.usuario.id = :id) or "
								
								+ "		u.id not in (select l.agencia.id "
								+ "					from LanceLeilao l "
								+ "					where l.agencia.id = :id) or "
								
								+ "		u.id not in (select l.anunciante.id "
								+ "					from LanceLeilao l "
								+ "					where l.anunciante.id = :id) or "
								
								+ "		u.id not in (select anuciante.id "
								+ "					from Leilao as lei  "
								+ "					INNER JOIN lei.inscritos anuciante "
								+ "					where anuciante.id = :id) or "
								
								+ "		u.id not in (select e.pertence.id "
								+ "					from EspacoPropaganda e "
								+ "					where e.pertence.id = :id) or "
								
								+ "		u.id not in (select e.pertence.id "
								+ "					from EspacoPropaganda e "
								+ "					where e.pertence.id = :id) or "
								
								+ "		u.id not in (select e.alugador.id "
								+ "					from EspacoPropaganda e "
								+ "					where e.alugador.id = :id) "
								
								/*+ "	and u.id <> :id"*/)	
})
//@formatter:on
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Usuario implements Serializable {
	private static final long serialVersionUID = -8066548068818800938L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true, nullable = false)
	@NotNull
	private String email;
	
	@Column(nullable = false)
	@NotNull
	private String password;
	
	@Column(nullable = false)
	@NotNull
	private String nome;
	
	@Column(nullable = false)
	@NotNull
	private String endereco;
	
	@Column(nullable = false)
	@NotNull
	private String cep;
	
	private String telefone;
	
	@Column(nullable = false)
	@NotNull
	private BigDecimal saldo = BigDecimal.valueOf(0);
	
	@OneToMany(mappedBy = "usuario")
	private List<ContaBancaria> contasBancarias = new ArrayList<ContaBancaria>();
	
	@OneToMany(mappedBy = "usuario")
	private List<TransacaoBancaria> transacoes = new ArrayList<TransacaoBancaria>();
	
	public TipoUsuario getTipoUsuario() {
		return (this instanceof Agencia) ? TipoUsuario.AGENCIA : TipoUsuario.ANUNCIANTE;
	}
	
	public String getCpfCnpj() {
		return (this instanceof Agencia) ? ((Agencia) this).getCnpj() : ((Anunciante) this).getCpf();
	}
	
	// get-set-gerados-------------------------------------------------------
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
	
	public List<ContaBancaria> getContasBancarias() {
		return contasBancarias;
	}
	
	public void setContasBancarias(List<ContaBancaria> contasBancarias) {
		this.contasBancarias = contasBancarias;
	}
}
