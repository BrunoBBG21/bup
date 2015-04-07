package br.com.bup.controller;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.bup.annotation.OpenTransaction;
import br.com.bup.dao.EspacoPropagandaDAO;
import br.com.bup.dao.MidiaDAO;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.FormatoEspacoPropaganda;
import br.com.bup.domain.Usuario;
import br.com.bup.web.UsuarioSession;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
public class EspacoController {
	private final static Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
	
    private final Result result;
	private final Validator validator;
	private final EspacoPropagandaDAO espacoPropagandaDAO;
	private final MidiaDAO midiaDAO;
	private final UsuarioSession usuarioSession;
	
	/**
     * @deprecated CDI eyes only
     */
	protected EspacoController() {
		this(null, null, null, null, null);
	}
	
	@Inject
	public EspacoController(Result result, Validator validator, MidiaDAO midiaDAO,
			EspacoPropagandaDAO espacoPropagandaDAO, UsuarioSession usuarioSession) {
		this.result = result;
		this.validator = validator;
		this.midiaDAO = midiaDAO;
		this.espacoPropagandaDAO = espacoPropagandaDAO;
		this.usuarioSession = usuarioSession;
	}
	
	@OpenTransaction
	public void formulario() {
		//simples formulario... futuramente receendo id para editar... ou nao...
		result.include("formatosEspaco", FormatoEspacoPropaganda.values());
		result.include("midias", midiaDAO.buscarTodos());
	}
	
	@OpenTransaction
	public void criar(@NotNull EspacoPropaganda espacoPropaganda) {
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
	}

	private void validarCriar(EspacoPropaganda espacoPropaganda) {
		validator.validate(espacoPropaganda);
		
		//TODO validar inclusao repetida
	}
}
