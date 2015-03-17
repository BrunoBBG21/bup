package br.com.bup.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class EspacoPropaganda {
	@Id
	@GeneratedValue
	private Long id;
	@Column(nullable=true)
	@NotNull
	private String local;
	private String descricao;
	@Column(nullable=true)
	@NotNull
	private Double largura;
	@Column(nullable=true)
	@NotNull
	private Double altura;
	@Column(nullable=true)
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date incioTempoLocacao;
	@Column(nullable=true)
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date fimTempoLocacao;
	
	private Long pageViews;
	
	private Integer quantidade;
	
	private Integer pesoMaximo;

	@ManyToOne
	private Midia midia;
	
	@ManyToOne
	private Anunciante pertence;
	
	@ManyToOne
	private Anunciante alugador;
	
    @Enumerated(EnumType.STRING)
	private FormatoEspacoPropaganda formatoEspacoPropaganda;
    
    @OneToMany(mappedBy = "espacoPropaganda")
	private List<Leilao> leiloes = new ArrayList<Leilao>();
    
    @OneToMany(mappedBy = "espacoPropaganda")
	private List<HistoricoAluguelEspaco> historicoAluguel = new ArrayList<HistoricoAluguelEspaco>();
    
    @ManyToMany
	private List<PublicoAlvo> publicosAlvos = new ArrayList<PublicoAlvo>();
	
	//get-set-gerados-------------------------------------------------------
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getLargura() {
		return largura;
	}
	public void setLargura(Double largura) {
		this.largura = largura;
	}
	public Double getAltura() {
		return altura;
	}
	public void setAltura(Double altura) {
		this.altura = altura;
	}
	public Long getPageViews() {
		return pageViews;
	}
	public void setPageViews(Long pageViews) {
		this.pageViews = pageViews;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Integer getPesoMaximo() {
		return pesoMaximo;
	}
	public void setPesoMaximo(Integer pesoMaximo) {
		this.pesoMaximo = pesoMaximo;
	}
	public Midia getMidia() {
		return midia;
	}
	public void setMidia(Midia midia) {
		this.midia = midia;
	}
	public Anunciante getPertence() {
		return pertence;
	}
	public void setPertence(Anunciante pertence) {
		this.pertence = pertence;
	}
	public Anunciante getAlugador() {
		return alugador;
	}
	public void setAlugador(Anunciante alugador) {
		this.alugador = alugador;
	}
	public FormatoEspacoPropaganda getFormatoEspacoPropaganda() {
		return formatoEspacoPropaganda;
	}
	public void setFormatoEspacoPropaganda(
			FormatoEspacoPropaganda formatoEspacoPropaganda) {
		this.formatoEspacoPropaganda = formatoEspacoPropaganda;
	}
	public List<Leilao> getLeiloes() {
		return leiloes;
	}
	public void setLeiloes(List<Leilao> leiloes) {
		this.leiloes = leiloes;
	}
	public List<HistoricoAluguelEspaco> getHistoricoAluguel() {
		return historicoAluguel;
	}
	public void setHistoricoAluguel(List<HistoricoAluguelEspaco> historicoAluguel) {
		this.historicoAluguel = historicoAluguel;
	}
	public List<PublicoAlvo> getPublicosAlvos() {
		return publicosAlvos;
	}
	public void setPublicosAlvos(List<PublicoAlvo> publicosAlvos) {
		this.publicosAlvos = publicosAlvos;
	}
	/**
	 * @return the fimTempoLocacao
	 */
	public Date getFimTempoLocacao() {
		return fimTempoLocacao;
	}
	/**
	 * @param fimTempoLocacao the fimTempoLocacao to set
	 */
	public void setFimTempoLocacao(Date fimTempoLocacao) {
		this.fimTempoLocacao = fimTempoLocacao;
	}
	/**
	 * @return the incioTempoLocacao
	 */
	public Date getIncioTempoLocacao() {
		return incioTempoLocacao;
	}
	/**
	 * @param incioTempoLocacao the incioTempoLocacao to set
	 */
	public void setIncioTempoLocacao(Date incioTempoLocacao) {
		this.incioTempoLocacao = incioTempoLocacao;
	}
}
