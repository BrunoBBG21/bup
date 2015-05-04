package br.com.bup.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.controller.IndexController;
import br.com.bup.controller.MidiaController;

public abstract class BaseDAO<T> {
	private final static Logger LOGGER = LoggerFactory.getLogger(BaseDAO.class);
	protected EntityManager manager;
	private final Class<T> classT;// = (Class<T>) ((ParameterizedType)
									// getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	/**
	 * Construtor usado nos testes unitarios.
	 * 
	 * @param manager
	 *            EntityManager
	 */
	public BaseDAO(EntityManager manager, Class<T> classT) {
		this.classT = classT;
		this.manager = manager;
	}

	/**
	 * Salvar padrao.
	 * 
	 * @param entidade
	 *            entidade que ser� salva
	 * @return entidade salva.
	 */
	public T salvar(T entidade) {
		manager.persist(entidade);
		return entidade;
	}

	/**
	 * apaga por id
	 * 
	 * @param id
	 */
	public void apagarPorId(Long id) throws Exception{
		Object o = manager.find(classT, id);
		if (o != null){
			manager.remove(o);
			manager.flush();
		}
	}

	/**
	 * Buscar pro id padrao.
	 * 
	 * @param id
	 *            id da entidade.
	 * @return entidade
	 */
	public T buscarPorId(Long id) {
		return manager.find(classT, id);
	}

	/**
	 * Buscar Todos padrao.
	 * 
	 * @return List de T
	 */
	public List<T> buscarTodos() {
		return manager.createQuery("from " + classT.getName()).getResultList();
	}

	/**
	 * EntityManager.flush().
	 */
	public void flush() {
		manager.flush();
	}
}
