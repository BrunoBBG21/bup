package br.com.bup.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.ApenasAnunciante;
import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.EspacoPropagandaDAO;
import br.com.bup.dao.MidiaDAO;
import br.com.bup.dao.PublicoAlvoDAO;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.FormatoEspacoPropaganda;
import br.com.bup.domain.PublicoAlvo;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class EspacoPropagandaController {
	private final static Logger LOGGER = LoggerFactory.getLogger(EspacoPropagandaController.class);
	
    private final Result result;
	private final Validator validator;
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	private final MidiaDAO midiaDAO;
	private final UsuarioSession usuarioSession;
	private final PublicoAlvoDAO publicoAlvoDAO;
	/**
     * @deprecated CDI eyes only
     */
	protected EspacoPropagandaController() {
		this(null, null, null, null, null,null);
	}
	
	@Inject
	public EspacoPropagandaController(Result result, Validator validator, MidiaDAO midiaDAO,
			EspacoPropagandaDAO espacoPropagandaDAO,PublicoAlvoDAO publicoAlvoDAO, UsuarioSession usuarioSession) {
		this.result = result;
		this.validator = validator;
		this.midiaDAO = midiaDAO;
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.publicoAlvoDAO=publicoAlvoDAO;
		this.usuarioSession = usuarioSession;
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public void formulario() {
		//simples formulario... futuramente receendo id para editar... ou nao...
		result.include("formatosEspaco", FormatoEspacoPropaganda.values());
		result.include("midias", midiaDAO.buscarTodos());
		result.include("publicosAlvos", publicoAlvoDAO.buscarTodos());
	}
	
	@OpenTransaction
	@ApenasAnunciante
	public void criar(@NotNull EspacoPropaganda espacoPropaganda,List<PublicoAlvo> alvos) {
		//espacoPropaganda.setPublicosAlvos(alvos);
		validator.onErrorRedirectTo(this).formulario(); //caso seja null...
		LOGGER.debug("criando espaco: " + espacoPropaganda);
		
		Usuario usuario = usuarioSession.getUsuario();
		
		if (!(usuario instanceof Anunciante)) {
			validator.add(new SimpleMessage("error", "Usuario deve ser do tipo Anunciante"));
			validator.onErrorRedirectTo(this).formulario();
			
		} else {
			espacoPropaganda.setPertence((Anunciante) usuario);
		}
		
		//validacoes...
		validarCriar(espacoPropaganda);
        validator.onErrorRedirectTo(this).formulario();		
		
        //salva
        espacoPropagandaDAO.salvar(espacoPropaganda);
		
		result.include("success", "Espaco criado com sucesso.");
		result.redirectTo(this).listar();
	}

	private void validarCriar(EspacoPropaganda espacoPropaganda) {
		validator.validate(espacoPropaganda);
		
		//TODO validar inclusao repetida
	}
	
	@OpenTransaction
	public List<EspacoPropaganda> listar() {
		LOGGER.debug("Listando os espacos ");  
		result.include("publicosAlvos", publicoAlvoDAO.buscarTodos());
		return espacoPropagandaDAO.buscarTodos();
	}
}
