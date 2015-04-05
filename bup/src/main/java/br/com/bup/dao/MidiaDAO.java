package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.Midia;

@RequestScoped
public class MidiaDAO extends BaseDAO<Midia> {
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected MidiaDAO() {
		super(null, Midia.class);
	}
	
	/**
	 * Construtor usado nos testes unitarios.
	 * @param manager EntityManager
	 */
	@Inject
	public MidiaDAO(EntityManager manager) {
		super(manager, Midia.class);
	}
	
	@Deprecated
	public void teste() {
		System.out.println("MidiaDAO");
		System.out.println(manager);
	}
}
