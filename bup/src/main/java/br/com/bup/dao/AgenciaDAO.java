package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.Agencia;

@RequestScoped
public class AgenciaDAO extends BaseDAO<Agencia> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected AgenciaDAO() {
		super(null, Agencia.class);
	}

	/**
	 * @param manager EntityManager
	 */
	@Inject
	public AgenciaDAO(EntityManager manager) {
		super(manager, Agencia.class);
	}
}
