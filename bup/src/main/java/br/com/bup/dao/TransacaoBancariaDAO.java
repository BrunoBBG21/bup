package br.com.bup.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.bup.domain.ContaBancaria;
import br.com.bup.domain.Midia;
import br.com.bup.domain.TransacaoBancaria;

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
	
	public List<TransacaoBancaria> buscarSemTransferenciaUsuario() {
		List<TransacaoBancaria> value = new ArrayList<TransacaoBancaria>();
		
		Query query = manager.createNamedQuery("TransacaoBancaria.buscarSemTransferenciaUsuario");
		
		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return value;
	}
	public List<TransacaoBancaria> buscarSemTransferenciaUsuario(Long id) {
		List<TransacaoBancaria> value = new ArrayList<TransacaoBancaria>();
		
		Query query = manager.createNamedQuery("TransacaoBancaria.buscarSemTransferenciaUsuarioPorId");
		query.setParameter("id", id);
		
		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return value;
	}
}
