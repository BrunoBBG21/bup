package br.com.bup.dao;

import java.util.ArrayList;
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
}
