package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.ContaBancaria;

@RequestScoped
public class ContaBancariaDAO extends BaseDAO<ContaBancaria> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected ContaBancariaDAO() {
		super(null, ContaBancaria.class);
	}

	/**
	 * @param manager EntityManager
	 */
	@Inject
	public ContaBancariaDAO(EntityManager manager) {
		super(manager, ContaBancaria.class);
	}

}
