package br.com.bup.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
	/**
	 * para garantir que o usuario apague os seus espacos de propaganda.
	 * @param id
	 * @param usuario
	 */
	public void apagarLogado(Long id,Long usuario) {
		EspacoPropaganda e = this.buscarPorId(id);
		if(e!=null&&e.getPertence().getId().equals(usuario)){
			manager.remove(e);
		}
	}
	public List<EspacoPropaganda> buscarPorAnuncianteId(Long id) {
		List<EspacoPropaganda> value = new ArrayList<EspacoPropaganda>();

		Query query = manager
				.createNamedQuery("EspacoPropaganda.buscarPorAnuncianteId");
		query.setParameter("id", id);

		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}

		return value;
	}
}
