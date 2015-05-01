package br.com.bup.dao;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;

public class ProdutorEntityManager {
	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("bup");
	static{
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Agencia a = new Agencia();
		a.setCep("1");
		a.setCnpj("2");
		a.setEmail("a@a.a");
		a.setEndereco("e");
		a.setNome("Agencia do BUP");
		a.setPassword("a");
		a.setTelefone("21");
		
		Anunciante bup = new Anunciante();
		bup.setCep("2");
		bup.setCpf("1");
		bup.setEmail("b@b.b");
		bup.setEndereco("n");
		bup.setNome("Anunciante do BUP");
		bup.setPassword("b");
		bup.setTelefone("21");
		bup.setGerenciado(a);
		
		UsuarioDAO u = new UsuarioDAO(em);
		u.salvar(a);
		u.salvar(bup);
		em.getTransaction().commit();
		em.close();
	}
	@Produces
	@RequestScoped
	public EntityManager criaEntityManager() {
		return factory.createEntityManager();
	}

	public void finaliza(@Disposes EntityManager manager) {
		manager.close();
	}
}
