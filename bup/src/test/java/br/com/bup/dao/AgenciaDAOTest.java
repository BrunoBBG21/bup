package br.com.bup.dao;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;

public class AgenciaDAOTest extends AbstractDAOTest {
	
	@Test
	public void buscaGerenciados() {
		UsuarioDAO usuarioDao = new UsuarioDAO(entityManager);
		AgenciaDAO dao = new AgenciaDAO(entityManager);
		
		//criando massa
		Anunciante anunciante = new Anunciante();
		anunciante.setCep("cep");
		anunciante.setCpf("cpf");
		anunciante.setEmail("anunciante@uhul.com");
		anunciante.setEndereco("endereco");
		anunciante.setNome("nome");
		anunciante.setPassword("password");
		anunciante.setTelefone("telefone");
		
		Agencia agencia = new Agencia();
		agencia.setCep("cep2");
		agencia.setCnpj("cnpj2");
		agencia.setEmail("agencia@wololo.com");
		agencia.setEndereco("endereco2");
		agencia.setNome("nome2");
		agencia.setPassword("password2");
		agencia.setTelefone("telefone2");
		
		anunciante.setGerenciado(agencia);
		agencia.getGerencia().add(anunciante);

		Assert.assertNull(agencia.getId());
		agencia = (Agencia) usuarioDao.salvar(agencia);
		Assert.assertNotNull(agencia.getId());

		Assert.assertNull(anunciante.getId());
		anunciante = (Anunciante) usuarioDao.salvar(anunciante);
		Assert.assertNotNull(anunciante.getId());
		
		//testando
		List<Map<String, Object>> gerenciados = dao.buscaGerenciados(agencia);
		
		Assert.assertNotNull(gerenciados);
		Assert.assertTrue(gerenciados.size() == 1);
		
		Map<String, Object> gerenciado = gerenciados.get(0);
		
		Assert.assertTrue(gerenciado.containsKey("id"));
		Assert.assertTrue(gerenciado.containsKey("nome"));
		Assert.assertTrue(gerenciado.containsValue("nome"));
	}

}
