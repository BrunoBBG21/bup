package br.com.bup.controller;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.TipoUsuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.validator.ValidationException;

public class UsuarioControllerTest extends AbstractControllerTest {
	private MockResult result;
    private MeuMockValidator validator;
    private UsuarioDAO usuarioDAO;
    private UsuarioSession usuarioSession;
    private UsuarioController controller;
    
    @Before
    public void setUp() {
        result = new MockResult();
        validator = new MeuMockValidator();
        usuarioDAO = new UsuarioDAO(entityManager);
        usuarioSession = new UsuarioSession();
        controller = new UsuarioController(result, validator, usuarioDAO, usuarioSession);
    }
    
    @Test
    public void criarUsuario() {
    	//dados
    	TipoUsuario tipoUsuario = TipoUsuario.AGENCIA;
		String email = "email@email.com";
		String password = "password";
		String nome = "nome";
		String endereco = "endereco";
		String cep = "cep";
		String telefone = "telefone";
		String cpfCnpj = "cpfCnpj";
		
		//testando
		Assert.assertFalse(usuarioDAO.existeComEmail(email));
		Assert.assertFalse(result.included().containsKey("success"));
		
		controller.criar(tipoUsuario, email, password, nome, endereco, cep, telefone, cpfCnpj);
		
		Assert.assertTrue(usuarioDAO.existeComEmail(email));
		Assert.assertTrue(result.included().containsKey("success"));
    }
    
    @Test
    public void criarUsuarioFail() {
    	//dados
    	TipoUsuario tipoUsuario = TipoUsuario.AGENCIA;
		String email = null;
		String password = null;
		String nome = null;
		String endereco = null;
		String cep = null;
		String telefone = null;
		String cpfCnpj = null;
		
		//testando
		try {
			controller.criar(tipoUsuario, email, password, nome, endereco, cep, telefone, cpfCnpj);
		} catch (ValidationException e) {}
		
		Assert.assertFalse(result.included().containsKey("success"));
		Assert.assertTrue(validator.hasErrors());
    }
}
