package br.com.bup.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

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
import br.com.bup.domain.TipoUsuario;
import br.com.bup.domain.Usuario;
import br.com.bup.util.BaseWeb;
import br.com.bup.util.NotNullBeanUtilsBean;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class UsuarioController extends BaseWeb {
	private final static Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
	
	private final UsuarioDAO usuarioDAO;
	
	/**
	 * @deprecated CDI eyes only
	 */
	protected UsuarioController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public UsuarioController(Result result, Validator validator, UsuarioDAO usuarioDAO, UsuarioSession usuarioSession,
			ResourceBundle i18n) {
		super(result, validator, usuarioSession, i18n);
		LOGGER.debug("Criando controller UsuarioController...");
		this.usuarioDAO = usuarioDAO;
	}
	
	@Public
	public void formulario() {
		// pagina com o formulario...
		result.include("tipos", TipoUsuario.values());
	}
	
	@Public
	@OpenTransaction
	public void criar(TipoUsuario tipoUsuario, @NotEmpty @EmailDisponivel String email, String password, String nome,
			String endereco, String cep, @Telefone String telefone, String cpfCnpj) {
		LOGGER.debug("criar usuario com email: " + email);
		validator.onErrorRedirectTo(this).formulario();
		
		// criando o tipo certo...
		Usuario usuario = montarUsuario(tipoUsuario, email, password, nome, endereco, cep, telefone, cpfCnpj);
		
		// validacoes...
		validar(usuario);
		validator.onErrorRedirectTo(this).formulario();
		
		// salva
		usuario = usuarioDAO.salvar(usuario);
		
		usuarioSession.logar(usuario);
		result.include("success", "Usuario incluido com sucesso.");
		result.redirectTo(IndexController.class).index();
	}
	
	private Usuario montarUsuario(TipoUsuario tipoUsuario, String email, String password, String nome, String endereco,
			String cep, String telefone, String cpfCnpj) {
		Usuario usuario;
		// criando o tipo certo...
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
		
		// populando o objeto....
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
	 * 
	 * @param usuario
	 *            Usuario
	 */
	private void validar(Usuario usuario) {
		if (usuario == null) {
			addErrorMsg("msg.error.apagar");
			return;
		}
		if (usuario.getId() != null && usuarioDAO.existeComEmailDiferenteId(usuario.getEmail(), usuario.getId())) {
			addErrorMsg("email", "email.ja.existe");
		}
		if (usuario.getId() != null && usuarioDAO.existeComCpfCnpjDiferenteId(usuario.getCpfCnpj(), usuario.getId())) {
			if (TipoUsuario.AGENCIA.equals(usuario.getTipoUsuario())) {
				addErrorMsg("cnpj", "cnpj.ja.existe");
			} else {
				addErrorMsg("cpf", "cpf.ja.existe");
			}
		}
		validator.validate(usuario);
	}
	
	@OpenTransaction
	public Usuario listar() {
		LOGGER.debug("Listando os usuários. ");
		
		return usuarioDAO.buscarPorId(usuarioSession.getUsuarioLogado().getId());
	}
	
	@Path("/usuario/apagar/{id}")
	@OpenTransaction
	public void apagar(Long id) {
		usuarioDAO.apagarLogado(id, usuarioSession.getUsuarioLogado().getId());
		usuarioSession.deslogar();
		
		result.include("success", i18n.getString("msg.success.apagar"));
		result.redirectTo(this).listar();
	}
	
	@OpenTransaction
	public void atualizar(Long id, TipoUsuario tipoUsuario, String email, String password, String nome, String endereco,
			String cep, String telefone, String cpfCnpj) {
		validator.onErrorRedirectTo(this).formulario(); // caso seja null...
		
		LOGGER.debug("atualizando usuario: " + email);
		Usuario usuario = montarUsuario(tipoUsuario, email, password, nome, endereco, cep, telefone, cpfCnpj);
		usuario.setId(id);
		
		// validacoes...
		validar(usuario);
		validator.onErrorRedirectTo(this).formulario();
		
		// recupera os dados q nao estao no formulario
		usuario = atualizarEntidadeDoFormulario(usuario);
		
		// atualiza
		usuario = usuarioDAO.salvar(usuario);
		
		addSuccessMsg("msg.success.conta_bancaria.atualizar");
		result.redirectTo(this).listar();
	}
	
	/**
	 * Retorna uma entidade atualizada com o banco e a passada pro metodo, mantendo os atributos do formulario da entidade
	 * passada.
	 * 
	 * @param modalidadePagamento
	 * @return Entidade atualizada.
	 */
	private Usuario atualizarEntidadeDoFormulario(Usuario usuario) {
		Usuario usuarioAtualizado = usuarioDAO.buscarPorId(usuario.getId());
		
		try {
			NotNullBeanUtilsBean.getInstance().copyProperties(usuarioAtualizado, usuario);
		} catch (IllegalAccessException | InvocationTargetException e) {
			addErrorMsg("msg.error.editar");
		}
		
		return usuarioAtualizado;
	}
	
	@OpenTransaction
	@Path("/usuario/editar/{id}")
	public void editar(Long id) {
		LOGGER.debug("carregando formulario de usuario com id: " + id);
		Usuario usuario = usuarioDAO.buscarPorId(id);
		
		result.include("id", usuario.getId());
		result.include("tipoUsuario", usuario.getTipoUsuario());
		result.include("email", usuario.getEmail());
		result.include("password", usuario.getPassword());
		result.include("nome", usuario.getNome());
		result.include("endereco", usuario.getEndereco());
		result.include("cep", usuario.getCep());
		result.include("telefone", usuario.getTelefone());
		if (TipoUsuario.AGENCIA.equals(usuario.getTipoUsuario())) {
			result.include("cpfCnpj", ((Agencia) usuario).getCnpj());
		} else {
			result.include("cpfCnpj", ((Anunciante) usuario).getCpf());
		}
		
		formulario();
	}
}
