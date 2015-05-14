package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.HistoricoAluguelEspaco;

@RequestScoped
public class HistoricoAluguelEspacoDAO extends BaseDAO<HistoricoAluguelEspaco> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected HistoricoAluguelEspacoDAO() {
		super(null, HistoricoAluguelEspaco.class);
	}
	
	/**
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public HistoricoAluguelEspacoDAO(EntityManager manager) {
		super(manager, HistoricoAluguelEspaco.class);
	}
}
