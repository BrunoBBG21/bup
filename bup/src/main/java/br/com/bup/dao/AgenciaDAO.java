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
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public AgenciaDAO(EntityManager manager) {
		super(manager, Agencia.class);
	}
	
	/**
	 * Busca os nomes e ids dos Anunciantes que s�o gerenciados pela agencia.
	 * 
	 * @param agencia
	 * @return list de map com chaves id e nome dos anunciantes.
	 */
	public List<Map<String, Object>> buscaGerenciados(Agencia agencia) {
		List<Map<String, Object>> value = new ArrayList<Map<String, Object>>();
		
		if (agencia != null) {
			Query query = manager.createNamedQuery("Agencia.buscaGerenciados");
			query.setParameter("id", agencia.getId());
			
			List<Map<String, Object>> result = null;
			try {
				result = query.getResultList();
			} catch (NoResultException ex) {
			}
			
			for (Map<String, Object> map : result) {
				Map<String, Object> valueMap = new HashMap<String, Object>();
				valueMap.put("id", map.get("0"));
				valueMap.put("nome", map.get("1"));
				value.add(valueMap);
			}
		}
		
		return value;
	}
	
	public List<Agencia> buscaNaoGerenciados(Long idAnunciante) {
		
		Query query = manager.createNamedQuery("Agencia.buscaNaoGerenciados");
		query.setParameter("id", idAnunciante);
		
		List<Agencia> result = new ArrayList<Agencia>();
		try {
			result = query.getResultList();
		} catch (NoResultException ex) {
		}
		
		return result;
	}
	
}
