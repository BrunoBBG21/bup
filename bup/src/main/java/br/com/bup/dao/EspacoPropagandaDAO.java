package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
	 * @param manager EntityManager
	 */
	@Inject
	public EspacoPropagandaDAO(EntityManager manager) {
		super(manager, EspacoPropaganda.class);
	}
}
