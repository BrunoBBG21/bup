package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.bup.domain.Usuario;

@RequestScoped
public class UsuarioDAO extends BaseDAO<Usuario> {

	/**
	 * @deprecated CDI eyes only
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
	
	/**
	 * Verifica se existe um Usuario com o email passado.
	 * @param email String
	 * @return Boolean
	 */
	public boolean existeComEmail(String email) {
		Query query = manager.createNamedQuery("Usuario.existeComEmail");
		query.setParameter("email", email);
		
		return (Boolean) query.getSingleResult();
	}
}
