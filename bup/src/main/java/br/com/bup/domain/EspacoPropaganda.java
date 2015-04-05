package br.com.bup.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"url","posicaoTela","largura","altura","midia_id"}))
public class EspacoPropaganda {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	@NotNull
	private String url;
	
	@Column(nullable=false)
	@NotNull
	private String posicaoTela;
	
	private String descricao;
	
	@Column(nullable=false)
	@NotNull
	private Double largura;
	
	@Column(nullable=false)
	@NotNull
	private Double altura;
	
	@Column(nullable=false)
	@NotNull
	private Integer periodo;
	
	private Long pageViews;
	
	private Integer pesoMaximo;

	@ManyToOne
	@JoinColumn(name="midia_id",nullable=false)
	@NotNull
	private Midia midia;
	
	@ManyToOne
	@JoinColumn(name="pertence_id",nullable=false)
	@NotNull
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
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPosicaoTela() {
		return posicaoTela;
	}
	public void setPosicaoTela(String posicaoTela) {
		this.posicaoTela = posicaoTela;
	}
}
