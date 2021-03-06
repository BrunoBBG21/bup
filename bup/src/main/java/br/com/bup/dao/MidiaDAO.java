package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	 * 
	 * @param manager
	 *            EntityManager
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
	
	/**
	 * Valida a unikConstraintValida anotada na classe...
	 * 
	 * @UniqueConstraint(columnNames={"agencia","conta","banco" ).
	 * 
	 * @param contaBancaria
	 * @return Boolean
	 */
	public Boolean unikConstraintValida(Midia midia) {
		if (midia != null) {
			Query query = manager.createNamedQuery("Midia.unikConstraintValida");
			
			query.setParameter("tipo", midia.getTipo());
			
			return (Long.valueOf(0)).equals((Long) query.getSingleResult());
		} else {
			return false;
		}
	}
	/**
	 * Verifica se existe um PublicoAlvo com o nome,descricao passado e id diferente.
	 * @param id
	 * @param nome
	 * @param descricao
	 *            String
	 * @return Boolean
	 */
	public boolean unikConstraintDiferenteId(String tipo, Long id) {
		Query query = manager.createNamedQuery("Midia.unikConstraintDiferenteId");
		query.setParameter("tipo", tipo);
		query.setParameter("id", id);
		return (Boolean) query.getSingleResult();
	}
}
