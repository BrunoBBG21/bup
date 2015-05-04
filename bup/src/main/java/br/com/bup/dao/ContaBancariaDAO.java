package br.com.bup.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public ContaBancariaDAO(EntityManager manager) {
		super(manager, ContaBancaria.class);
	}

	public List<ContaBancaria> buscarPorUsuarioId(Long id) {
		List<ContaBancaria> value = new ArrayList<ContaBancaria>();

		Query query = manager
				.createNamedQuery("ContaBancaria.buscarPorUsuarioId");
		query.setParameter("id", id);

		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}

		return value;
	}
	/**
	 * para garantir que o usuario apague as suas proprias contas.
	 * @param id
	 * @param usuario
	 */
	public void apagarLogado(Long id,Long usuario){
		ContaBancaria c = this.buscarPorId(id);
		if(c!=null&&c.getUsuario().getId().equals(usuario)){
			manager.remove(c);
		}
	}
}
