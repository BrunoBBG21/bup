package br.com.bup.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.annotation.Public;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.TipoUsuario;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class UsuarioController {
	private final static Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
	
    private final Result result;
	private final Validator validator;
	private UsuarioDAO usuarioDAO;
	private UsuarioSession usuarioSession;
	
	/**
     * @deprecated CDI eyes only
     */
	protected UsuarioController() {
		this(null, null, null, null);
	}
	
	@Inject
	public UsuarioController(Result result, Validator validator,
			UsuarioDAO usuarioDAO, UsuarioSession usuarioSession) {
		LOGGER.debug("Criando controller UsuarioController...");
		this.result = result;
		this.validator = validator;
		this.usuarioDAO = usuarioDAO;
		this.usuarioSession = usuarioSession;
	}
	
	@Public
	public void formulario() {
		//pagina com o formulario...
	}
	
	@Public
	@OpenTransaction
	public void criar(TipoUsuario tipoUsuario, String email, String password, String nome, 
			String endereco, String cep, String telefone, String cpfCnpj) {
		LOGGER.debug("criarUsuario com email: " + email);
		
		//criando o tipo certo...
        Usuario usuario = montarUsuario(tipoUsuario, email, password, nome, endereco, cep, telefone, cpfCnpj);

		//validacoes...
		validarInclusaoUsuario(usuario);
		// em caso de erros irá redirecionar para a página de formulário
        validator.onErrorRedirectTo(this).formulario();
		
		//salva
		usuarioDAO.salvar(usuario);
		
		result.include("success", "Usuario incluido com sucesso.");
	}
	
	private Usuario montarUsuario(TipoUsuario tipoUsuario, String email,
				String password, String nome, String endereco, String cep,
				String telefone, String cpfCnpj) {
		Usuario usuario;
		//criando o tipo certo...
		if (TipoUsuario.AGENCIA.equals(tipoUsuario)) {
			Agencia agencia = new Agencia();
			agencia.setCnpj(cpfCnpj);
			usuario = agencia;
			
		} else if (TipoUsuario.ANUNCIANTE.equals(tipoUsuario)) {
			Anunciante anunciante = new Anunciante();
			anunciante.setCpf(cpfCnpj);
			usuario = anunciante;
			
		} else {
			return null;
		}
		LOGGER.debug("usuario do tipo " + tipoUsuario);
		
		//populando o objeto....
		usuario.setEmail(email);
		usuario.setPassword(password);
		usuario.setNome(nome);
		usuario.setEndereco(endereco);
		usuario.setCep(cep);
		usuario.setTelefone(telefone);
		return usuario;
	}
	
	/**
	 * Valida se o usuario pode ser criado.
	 * @param usuario Usuario
	 */
	private void validarInclusaoUsuario(Usuario usuario) {
		if (usuario == null) {
			validator.add(new SimpleMessage("Erro", "Usuario nulo."));
			return;
		}
		
		validator.validate(usuario);

		if (usuario.getEmail() != null && usuarioDAO.existeComEmail(usuario.getEmail())) { //TODO: passar essa validaçao para uma anotation...
			validator.add(new SimpleMessage("email", "Email já cadastrado."));
		}
	}
}
