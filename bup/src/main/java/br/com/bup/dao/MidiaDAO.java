package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.bup.domain.Midia;

@RequestScoped
public class MidiaDAO {
	@Inject
	private EntityManager manager;
	
	public void teste() {
		System.out.println("MidiaDAO");
		System.out.println(manager);
	}
	
	public Midia salvar(Midia midia) {
		manager.persist(midia);
		return midia;
	}
}
