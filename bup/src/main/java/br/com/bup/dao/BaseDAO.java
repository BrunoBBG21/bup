package br.com.bup.dao;

import java.util.List;

import javax.persistence.EntityManager;

public abstract class BaseDAO<T> {
	protected EntityManager manager;
	private final Class<T> classT;// = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
//	/**
//	 * Construtor usado pelo CDI...
//	 */
//	protected BaseDAO() {
//	}
	
	/**
	 * Construtor usado nos testes unitarios.
	 * @param manager EntityManager
	 */
	public BaseDAO(EntityManager manager, Class<T> classT) {
		this.classT = classT;
		this.manager = manager;
	}
	
	/**
	 * Salvar padrao.
	 * @param entidade entidade que será salva
	 * @return entidade salva.
	 */
	public T salvar(T entidade) {
		manager.persist(entidade);
		return entidade;
	}
	
	/**
	 * Buscar pro id padrao.
	 * @param id id da entidade.
	 * @return entidade
	 */
	public T buscarPorId(Long id) {
		return manager.find(classT, id);
	}
	
	/**
	 * Buscar Todos padrao.
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
