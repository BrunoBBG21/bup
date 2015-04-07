package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.LancesLeilao;

@RequestScoped
public class LancesLeilaoDAO extends BaseDAO<LancesLeilao> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected LancesLeilaoDAO() {
		super(null, LancesLeilao.class);
	}

	/**
	 * @param manager EntityManager
	 */
	@Inject
	public LancesLeilaoDAO(EntityManager manager) {
		super(manager, LancesLeilao.class);
	}
}
