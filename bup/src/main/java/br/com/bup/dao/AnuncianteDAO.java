package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.Anunciante;

@RequestScoped
public class AnuncianteDAO extends BaseDAO<Anunciante> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected AnuncianteDAO() {
		super(null, Anunciante.class);
	}
	
	/**
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public AnuncianteDAO(EntityManager manager) {
		super(manager, Anunciante.class);
	}
}
