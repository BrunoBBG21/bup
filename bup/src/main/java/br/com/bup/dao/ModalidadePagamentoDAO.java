package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.ModalidadePagamento;

@RequestScoped
public class ModalidadePagamentoDAO extends BaseDAO<ModalidadePagamento> {
	/**
	 * @deprecated CDI eyes only
	 */
	protected ModalidadePagamentoDAO() {
		super(null, ModalidadePagamento.class);
	}
	
	/**
	 * Construtor usado nos testes unitarios.
	 * @param manager EntityManager
	 */
	@Inject
	public ModalidadePagamentoDAO(EntityManager manager) {
		super(manager, ModalidadePagamento.class);
	}
}
