package br.com.bup.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import br.com.bup.state.EstadoLeilao;
import br.com.bup.state.TipoEstadoLeilao;

//@formatter:off
@NamedQueries(value = {
		@NamedQuery(name = "Leilao.buscarPorAnuncianteId",
				query = "select l "
						+ "from Leilao l "
						+ "where "
						+ "		l.espacoPropaganda.pertence.id = :anuncianteId"),
						
		@NamedQuery(name = "Leilao.buscarTodosEsperandoMenosAnuncianteId",
				query = "select l "
						+ "from Leilao l "
						+ "where "
						+ "		l.estado = 'ESPERANDO' "
						+ "	AND not l.espacoPropaganda.pertence.id = :anuncianteId "
						+ "	AND not exists("
						+ "					SELECT l2 "
						+ "					FROM Leilao l2 "
						+ "					JOIN l2.inscritos i "
						+ "					WHERE "
						+ "						l2.id = l.id "
						+ "					AND i.id = :anuncianteId) "),
						
		@NamedQuery(name = "Leilao.buscarTodosEmAndamentoOuAguardando",
				query = "select l "
						+ "from Leilao l "
						+ "left join fetch l.lances la "
						+ "where "
						+ "		l.estado in ('EM_ANDAMENTO', 'AGUARDANDO') "),
						
		@NamedQuery(name = "Leilao.buscarIdsLeiloesObservarPorUsuarioId",
				query = "select distinct l.id "
						+ "from Leilao l "
						+ "join l.inscritos i "
						+ "where "
						+ "		l.estado in ('EM_ANDAMENTO', 'AGUARDANDO') "
						+ "	AND (i.id = :usuarioId or i.gerenciado.id = :usuarioId)"),
						
		@NamedQuery(name = "Leilao.buscarPorInscritoId",
				query = "select distinct l "
						+ "from Leilao l "
						+ "join l.inscritos i "
						+ "where "
						+ "		i.id = :anuncianteId"),
						
		@NamedQuery(name = "Leilao.unikConstraintValida",
				query="select count(l) "
						+ " from Leilao l "
						+ " where "
						+ "		l.dataInicio = :dataInicio "
						+ "	AND l.dataFim = :dataFim "
						+ "	AND l.modalidadePagamento.id = :mpid "
						+ "	AND l.espacoPropaganda.id = :epid "),
						
		@NamedQuery(name = "Leilao.unikConstraintDiferenteId",
				query = "select case when (count(l) > 0) then true else false end "
						+ "from Leilao l "
						+ "where "
						+ "		("
						+ "			l.dataInicio = :dataInicio "
						+ "		and l.dataFim = :dataFim "
						+ "		and l.modalidadePagamento.id = :modalidadePagamento "
						+ "		and l.espacoPropaganda.id = :espacoPropaganda"
						+ "		) "
						+ "and l.id <> :id")
})
//@formatter:on
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "dataInicio", "dataFim", "modalidadePagamento_id",
		"espacoPropaganda_id" }))
public class Leilao extends Observable {
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
	
	private BigDecimal reserva;
	
	private BigDecimal inscricao;
	
	private Boolean ativo = Boolean.TRUE; //TODO oq quer dizer? Provavelmente pode ser visto pelo estado do leilao...
	
	@Enumerated(EnumType.STRING)
	private TipoEstadoLeilao estado = TipoEstadoLeilao.ESPERANDO;
	
	@ManyToOne
	@JoinColumn(name = "modalidadePagamento_id", nullable = false)
	@NotNull
	private ModalidadePagamento modalidadePagamento;
	
	@ManyToOne
	@JoinColumn(name = "espacoPropaganda_id", nullable = false)
	@NotNull
	private EspacoPropaganda espacoPropaganda;
	
	@OneToMany(mappedBy = "leilao")
	private List<LanceLeilao> lances = new ArrayList<LanceLeilao>();
	
	@ManyToMany
	@JoinTable(name = "Inscritos_Leilao",
			joinColumns = { @JoinColumn(name = "leilao_id") },
			inverseJoinColumns = { @JoinColumn(name = "anunciante_id") })
	private List<Anunciante> inscritos = new ArrayList<Anunciante>();
	
	// metodos----------------------------------------------------------------

    /**
     * Marca o leilao como alterado...
     */
	@Override
    public synchronized void setChanged() {
        super.setChanged();
    }
	
	/**
	 * Retorna a logica do estado atual.
	 * 
	 * @return EstadoLeilao implementa��o com a logica do estado atual.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public EstadoLeilao getEstadoAtualLogica() throws InstantiationException, IllegalAccessException {
		EstadoLeilao instance = estado.getInstance();
		instance.setLeilao(this);
		return instance;
	}
	
	/**
	 * Verifica se o estado autal do leilao � ESPERANDO.
	 * 
	 * @return boolean
	 */
	public boolean isEstadoEsperando() {
		return TipoEstadoLeilao.ESPERANDO.equals(getEstado());
	}
	
	/**
	 * Verifica se o estado autal do leilao � CANCELADO.
	 * 
	 * @return boolean
	 */
	public boolean isEstadoCancelado() {
		return TipoEstadoLeilao.CANCELADO.equals(getEstado());
	}
	
	/**
	 * Verifica se o estado autal do leilao � EM_ANDAMENTO.
	 * 
	 * @return boolean
	 */
	public boolean isEstadoEmAndamento() {
		return TipoEstadoLeilao.EM_ANDAMENTO.equals(getEstado());
	}
	
	/**
	 * Verifica se o estado autal do leilao � AGUARDANDO.
	 * 
	 * @return boolean
	 */
	public boolean isEstadoAguardando() {
		return TipoEstadoLeilao.AGUARDANDO.equals(getEstado());
	}
	
	/**
	 * Verifica se o estado autal do leilao � FINALIZADO.
	 * 
	 * @return boolean
	 */
	public boolean isEstadoFinalizado() {
		return TipoEstadoLeilao.FINALIZADO.equals(getEstado());
	}

	public LanceLeilao getUltimoLance() {
		LanceLeilao value = null;
		for (LanceLeilao lanceLeilao : lances) {
			if (value == null || value.getData().before(lanceLeilao.getData())) {
				value = lanceLeilao;
			}
		}
		return value;
	}
	
	// get-set-gerados-------------------------------------------------------
	
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
	
	public BigDecimal getReserva() {
		return reserva;
	}
	
	public void setReserva(BigDecimal reserva) {
		this.reserva = reserva;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public ModalidadePagamento getModalidadePagamento() {
		return modalidadePagamento;
	}
	
	public void setModalidadePagamento(ModalidadePagamento modalidadePagamento) {
		this.modalidadePagamento = modalidadePagamento;
	}
	
	public EspacoPropaganda getEspacoPropaganda() {
		return espacoPropaganda;
	}
	
	public void setEspacoPropaganda(EspacoPropaganda espacoPropaganda) {
		this.espacoPropaganda = espacoPropaganda;
	}
	
	public List<LanceLeilao> getLances() {
		return lances;
	}
	
	public void setLances(List<LanceLeilao> lances) {
		this.lances = lances;
	}
	
	public List<Anunciante> getInscritos() {
		return inscritos;
	}
	
	public void setInscritos(List<Anunciante> inscritos) {
		this.inscritos = inscritos;
	}
	
	public BigDecimal getInscricao() {
		return inscricao;
	}
	
	public void setInscricao(BigDecimal inscricao) {
		this.inscricao = inscricao;
	}
	
	public TipoEstadoLeilao getEstado() {
		return estado;
	}
	
	public void setEstado(TipoEstadoLeilao estado) {
		this.estado = estado;
	}
}
