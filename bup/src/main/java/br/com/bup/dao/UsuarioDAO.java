package br.com.bup.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.bup.domain.Usuario;

public class UsuarioDAO extends BaseDAO<Usuario> {

	/**
	 * Construtor usado pelo CDI...
	 */
	protected UsuarioDAO() {
		super(null, Usuario.class);
	}

	/**
	 * Construtor usado nos testes unitarios.
	 * 
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public UsuarioDAO(EntityManager manager) {
		super(manager, Usuario.class);
	}

	public Usuario buscarPorEmailSenha(String email, String password) {
		Query query = manager.createNamedQuery("Usuario.buscarPorEmailSenha");
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		Object value = null;
		try {
			value = query.getSingleResult();
		} catch(NoResultException ex){}
		
		return (Usuario) value;
	}
}
