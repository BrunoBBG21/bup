package br.com.bup.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.bup.domain.EspacoPropaganda;

@RequestScoped
public class EspacoPropagandaDAO extends BaseDAO<EspacoPropaganda> {
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected EspacoPropagandaDAO() {
		super(null, EspacoPropaganda.class);
	}
	
	/**
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public EspacoPropagandaDAO(EntityManager manager) {
		super(manager, EspacoPropaganda.class);
	}
	
	/**
	 * para garantir que o usuario apague os seus espacos de propaganda.
	 * 
	 * @param id
	 * @param usuario
	 */
	public void apagarLogado(Long id, Long usuario) {
		EspacoPropaganda e = this.buscarPorId(id);
		if (e != null && e.getPertence().getId().equals(usuario)) {
			manager.remove(e);
		}
	}
	
	public List<EspacoPropaganda> buscarPorAnuncianteId(Long id) {
		List<EspacoPropaganda> value = new ArrayList<EspacoPropaganda>();
		
		Query query = manager.createNamedQuery("EspacoPropaganda.buscarPorAnuncianteId");
		query.setParameter("id", id);
		
		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return value;
	}
	
	public List<EspacoPropaganda> buscarLivresPorAnuncianteId(Long id) {
		List<EspacoPropaganda> value = new ArrayList<EspacoPropaganda>();
		
		Query query = manager.createNamedQuery("EspacoPropaganda.buscarLivresPorAnuncianteId");
		query.setParameter("id", id);
		
		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return value;
	}
	
	/**
	 * Valida a unikConstraintValida anotada na classe...
	 * 
	 * @UniqueConstraint(columnNames={"agencia","conta","banco" ).
	 * 
	 * @param contaBancaria
	 * @return Boolean
	 */
	public Boolean unikConstraintValida(EspacoPropaganda espacoPropaganda) {
		if (espacoPropaganda != null && espacoPropaganda.getMidia() != null) {
			Query query = manager.createNamedQuery("EspacoPropaganda.unikConstraintValida");
			query.setParameter("url", espacoPropaganda.getUrl());
			query.setParameter("posicaoTela", espacoPropaganda.getPosicaoTela());
			query.setParameter("largura", espacoPropaganda.getLargura());
			query.setParameter("altura", espacoPropaganda.getAltura());
			query.setParameter("midia", espacoPropaganda.getMidia().getId());
			
			return (Long.valueOf(0)).equals((Long) query.getSingleResult());
		} else {
			return false;
		}
	}
	/**
	 * Verifica se existe um PublicoAlvo com o nome,descricao passado e id diferente.
	 * @param id
	 * @param nome
	 * @param descricao
	 *            String
	 * @return Boolean
	 */
	public boolean unikConstraintDiferenteId(String url, String posicaoTela, Double largura, Double altura, Long midia, Long id) {
		Query query = manager.createNamedQuery("EspacoPropaganda.unikConstraintDiferenteId");
		query.setParameter("url", url);
		query.setParameter("posicaoTela", posicaoTela);
		query.setParameter("largura", largura);
		query.setParameter("altura", altura);
		query.setParameter("midia", midia);
		query.setParameter("id", id);
		return (Boolean) query.getSingleResult();
	}
}
