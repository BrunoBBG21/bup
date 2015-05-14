package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
	 * 
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public PublicoAlvoDAO(EntityManager manager) {
		super(manager, PublicoAlvo.class);
	}
	
	/**
	 * Valida a unikConstraintValida anotada na classe...
	 * 
	 * @UniqueConstraint(columnNames={"agencia","conta","banco" ).
	 * 
	 * @param contaBancaria
	 * @return Boolean
	 */
	public Boolean unikConstraintValida(PublicoAlvo publicoAlvo) {
		if (publicoAlvo != null) {
			Query query = manager.createNamedQuery("PublicoAlvo.unikConstraintValida");
			query.setParameter("nome", publicoAlvo.getNome());
			query.setParameter("descricao", publicoAlvo.getDescricao());
			
			return (Long.valueOf(0)).equals((Long) query.getSingleResult());
		} else {
			return false;
		}
	}
}
