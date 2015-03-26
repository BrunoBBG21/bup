package br.com.bup.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bup.domain.Midia;

/**
 * Teste basico das funcoes basicas dos DAOs. Sera utilizado a entidade Midia por ser a mais simples. 
 *
 */
public class BaseDAOTest extends AbstractDAOTest {
	
	@Test
	public void salvarBuscarTest() {
		MidiaDAO dao = new MidiaDAO(entityManager);
		Long id = null;
		
		//criando massa
		Midia midia = new Midia();
		midia.setTipo("tipo 1");
		midia = dao.salvar(midia);
		Assert.assertNotNull(midia.getId());
		id = midia.getId();
		
		midia = new Midia();
		midia.setTipo("tipo 2");
		midia = dao.salvar(midia);
		Assert.assertNotNull(midia.getId());
		
		//busncando
		midia = null;
		midia = dao.buscarPorId(id);
		Assert.assertNotNull(midia);
		
		List<Midia> result = dao.buscarTodos();
		Assert.assertEquals(2, result.size());
	}
}
