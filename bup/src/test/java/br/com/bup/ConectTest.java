package br.com.bup;

import java.util.List;

import javax.persistence.Query;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bup.domain.Leilao;
import br.com.bup.domain.Midia;

public class ConectTest extends AbstractTest {
	
	@Test()
	@SuppressWarnings({ "unchecked" })
	public void testBasicUsage() {
		//criando massa
		Midia midia = new Midia();
		midia.setTipo("tipo 1");
		entityManager.persist(midia);
		
		midia = new Midia();
		midia.setTipo("tipo 2");
		entityManager.persist(midia);
		
//		entityManager.getTransaction().commit();
		
		//busncando
		Query query = entityManager.createQuery("from Midia");
		List<Leilao> result = query.getResultList();
		Assert.assertEquals(2, result.size());
	}

	@Test
	@SuppressWarnings({ "unchecked" })
	public void testBasicUsage2() {
		//criando massa
		Midia midia = new Midia();
		midia.setTipo("tipo 3");
		entityManager.persist(midia);
		
		midia = new Midia();
		midia.setTipo("tipo 4");
		entityManager.persist(midia);
		
//		entityManager.getTransaction().commit();
		
		//busncando
		Query query = entityManager.createQuery("from Midia");
		List<Leilao> result = query.getResultList();
		Assert.assertEquals(2, result.size());
	}
}
