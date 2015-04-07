package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.TransacaoBancaria;
import br.com.bup.domain.Usuario;

@RequestScoped
public class TransacaoBancariaDAO extends BaseDAO<TransacaoBancaria> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected TransacaoBancariaDAO() {
		super(null, TransacaoBancaria.class);
	}

	/**
	 * Construtor usado nos testes unitarios.
	 * 
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public TransacaoBancariaDAO(EntityManager manager) {
		super(manager, TransacaoBancaria.class);
	}
}
