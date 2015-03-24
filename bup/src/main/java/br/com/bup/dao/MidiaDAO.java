package br.com.bup.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class MidiaDAO {
	@Inject
	private EntityManager manager;
	
	public void teste() {
		System.out.println("MidiaDAO");
		System.out.println(manager);
	}
}
