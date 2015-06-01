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
	/**
	 * para garantir que o usuario apague as suas proprias contas.
	 * 
	 * @param id
	 * @param usuario
	 */
	public void apagarLogado(Long id, Long usuario) {
		TransacaoBancaria transacaoBancaria = this.buscarPorId(id);
		if (transacaoBancaria != null && transacaoBancaria.getConta().getUsuario().getId().equals(usuario)) {
			manager.remove(transacaoBancaria);
		}
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
	public List<TransacaoBancaria> buscarParaLiberar() {
		List<TransacaoBancaria> value = new ArrayList<TransacaoBancaria>();
		
		Query query = manager.createNamedQuery("TransacaoBancaria.buscarParaLiberar");
		
		try {
			value = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return value;
	}
	public List<TransacaoBancaria> buscarParaAprovar() {
		List<TransacaoBancaria> value = new ArrayList<TransacaoBancaria>();
		
		Query query = manager.createNamedQuery("TransacaoBancaria.buscarParaAprovar");
		
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
