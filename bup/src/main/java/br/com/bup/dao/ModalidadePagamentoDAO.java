package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.bup.domain.ContaBancaria;
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
	 * 
	 * @param manager
	 *            EntityManager
	 */
	@Inject
	public ModalidadePagamentoDAO(EntityManager manager) {
		super(manager, ModalidadePagamento.class);
	}

	/**
	 * Valida a unikConstraintValida anotada na classe...
	 * @UniqueConstraint(columnNames={"agencia","conta","banco"}).
	 * 
	 * @param contaBancaria
	 * @return Boolean
	 */
	public Boolean unikConstraintValida(ModalidadePagamento modalidadePagamento) {
		if (modalidadePagamento != null && modalidadePagamento.getMidia()!=null) {
			Query query = manager
					.createNamedQuery("ModalidadePagamento.unikConstraintValida");

			query.setParameter("maxParcela",
					modalidadePagamento.getMaxParcela());
			query.setParameter("entrada", modalidadePagamento.getEntrada());
			query.setParameter("primeiroPagamento",
					modalidadePagamento.getPrimeiroPagamento());
			query.setParameter("valorMinParcela",
					modalidadePagamento.getValorMinParcela());
			query.setParameter("midia", modalidadePagamento.getMidia()
					.getId());

			return (Long.valueOf(0)).equals((Long) query.getSingleResult());
		} else {
			return false;
		}
	}
}
