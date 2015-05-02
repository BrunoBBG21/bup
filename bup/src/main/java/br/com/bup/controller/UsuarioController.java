package br.com.bup.controller;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.EmailDisponivel;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.annotation.Public;
import br.com.bup.annotation.Telefone;
import br.com.bup.dao.UsuarioDAO;
import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.TipoUsuario;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.I18nMessage;
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
		result.include("tipos", TipoUsuario.values());
	}
	
	@Public
	@OpenTransaction
	public void criar(TipoUsuario tipoUsuario, @NotEmpty @EmailDisponivel String email, String password, String nome, 
			String endereco, String cep, @Telefone String telefone, String cpfCnpj) {
		LOGGER.debug("criar usuario com email: " + email);
		validator.onErrorRedirectTo(this).formulario();
		
		//criando o tipo certo...
        Usuario usuario = montarUsuario(tipoUsuario, email, password, nome, endereco, cep, telefone, cpfCnpj);

		//validacoes...
		validarInclusaoUsuario(usuario);
        validator.onErrorRedirectTo(this).formulario();
		
		//salva
		usuarioDAO.salvar(usuario);
		
		usuarioSession.logar(usuario);
		result.include("success", "Usuario incluido com sucesso.");
		result.redirectTo(IndexController.class).index();
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
			validator.add(new I18nMessage("tipoUsuario", "usuario.formulario.tipo.invalido"));
			return;
		}
		
		validator.validate(usuario);
	}
	@OpenTransaction
	public List<Usuario> listar() {
		LOGGER.debug("Listando os usuários. ");
		
		return usuarioDAO.buscarTodos();
	}
}
