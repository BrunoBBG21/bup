package br.com.bup.dao;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bup.domain.Agencia;

public class UsuarioDAOTest extends AbstractDAOTest {
	
	@Test
	public void existeComEmail() {
		UsuarioDAO dao = new UsuarioDAO(entityManager);
		String email = "uhul@wololo.com";
		
		//testando
		Assert.assertFalse(dao.existeComEmail(email));
		
		//criando massa
		Agencia agencia = new Agencia();
		agencia.setCep("cep");
		agencia.setCnpj("cnpj");
		agencia.setEmail(email);
		agencia.setEndereco("endereco");
		agencia.setNome("nome");
		agencia.setPassword("password");
		agencia.setTelefone("telefone");
		
		Assert.assertNull(agencia.getId());
		dao.salvar(agencia);
		Assert.assertNotNull(agencia.getId());
		
		//testando
		Assert.assertTrue(dao.existeComEmail(email));
	}

}
