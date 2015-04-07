package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
	 * @param manager EntityManager
	 */
	@Inject
	public LeilaoDAO(EntityManager manager) {
		super(manager, Leilao.class);
	}
}
