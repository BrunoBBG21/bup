package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.LanceLeilao;

@RequestScoped
public class LanceLeilaoDAO extends BaseDAO<LanceLeilao> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected LanceLeilaoDAO() {
		super(null, LanceLeilao.class);
	}

	/**
	 * @param manager EntityManager
	 */
	@Inject
	public LanceLeilaoDAO(EntityManager manager) {
		super(manager, LanceLeilao.class);
	}
}
