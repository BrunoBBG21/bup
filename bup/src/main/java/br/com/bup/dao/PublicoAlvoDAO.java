package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.PublicoAlvo;

@RequestScoped
public class PublicoAlvoDAO extends BaseDAO<PublicoAlvo> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected PublicoAlvoDAO() {
		super(null, PublicoAlvo.class);
	}
	
	/**
	 * Construtor usado nos testes unitarios.
	 * @param manager EntityManager
	 */
	@Inject
	public PublicoAlvoDAO(EntityManager manager) {
		super(manager, PublicoAlvo.class);
	}
}
