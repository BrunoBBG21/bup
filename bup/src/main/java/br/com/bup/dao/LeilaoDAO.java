package br.com.bup.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.bup.domain.Leilao;

@RequestScoped
public class LeilaoDAO extends BaseDAO<Leilao> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected LeilaoDAO() {
		super(null, Leilao.class);
	}
	
	/**
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public LeilaoDAO(EntityManager manager) {
		super(manager, Leilao.class);
	}
	
	public List<Leilao> buscarPorAnuncianteId(Long anuncianteId) {
		List<Leilao> value = new ArrayList<Leilao>();
		
		Query query = manager.createNamedQuery("Leilao.buscarPorAnuncianteId");
		query.setParameter("anuncianteId", anuncianteId);
		
		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return value;
	}
	
	/**
	 * Busca todos os leiloes com estado ESPERANDO menos os leiloes que pertencem ou em que o Anunciante participa.
	 * 
	 * @param anuncianteId
	 * @return
	 */
	public List<Leilao> buscarTodosEsperandoMenosAnuncianteId(Long anuncianteId) {
		List<Leilao> value = new ArrayList<Leilao>();
		
		Query query = manager.createNamedQuery("Leilao.buscarTodosEsperandoMenosAnuncianteId");
		query.setParameter("anuncianteId", anuncianteId);
		
		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return value;
	}
	
	/**
	 * Adiciona um novo registro na tabela de inscritos em leilao.
	 * 
	 * @param anuncianteId
	 * @param leilaoId
	 */
	public void addInscritoNoLeilao(Long anuncianteId, Long leilaoId) {
		Query query = manager
				.createNativeQuery("insert into Inscritos_Leilao (leilao_id, anunciante_id) values (:leilaoId, :anuncianteId)");
		query.setParameter("anuncianteId", anuncianteId);
		query.setParameter("leilaoId", leilaoId);
		
		query.executeUpdate();
	}
	
	/**
	 * Busca todos os leiloes em que o anuncianteId passado esteja inscrito.
	 * 
	 * @param anuncianteId
	 * @return
	 */
	public List<Leilao> buscarPorInscritoId(Long anuncianteId) {
		List<Leilao> value = new ArrayList<Leilao>();
		Query query = manager.createNamedQuery("Leilao.buscarPorInscritoId");
		query.setParameter("anuncianteId", anuncianteId);
		
		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return value;
	}
	/**
	 * Verifica se existe um PublicoAlvo com o nome,descricao passado e id diferente.
	 * @param id
	 * @param nome
	 * @param descricao
	 *            String
	 * @return Boolean
	 */
	public boolean unikConstraintDiferenteId(Date dataInicio, Date dataFim, Long modalidadePagamento, Long espacoPropaganda) {
		Query query = manager.createNamedQuery("Leilao.unikConstraintDiferenteId");
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		query.setParameter("modalidadePagamento", modalidadePagamento);
		query.setParameter("espacoPropaganda", espacoPropaganda);
		return (Boolean) query.getSingleResult();
	}
	
}
