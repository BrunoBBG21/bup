package br.com.bup.web;

import javax.inject.Inject;

import br.com.bup.dao.MidiaDAO;
import br.com.bup.domain.Midia;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;

@Controller
public class TesteController {
	@Inject
	private MidiaDAO dao;
	
	@Inject
    private Result result;
	
	@Inject
	private UsuarioSession usuarioSession;

	/**
	 * @deprecated CDI eyes only
	 */
	protected TesteController() {
		this(null);
	}

	@Inject
	public TesteController(MidiaDAO dao) {
		this.dao = dao;
	}

	public Midia uhul() {
		System.out.println("UHULLL");
		Midia midia = new Midia();
		midia.setTipo("TIPO");

		return midia;
	}
	
	public void adiciona(Midia midia) {
		System.out.println(midia);
		
		dao.salvar(midia);
		
		result.include("msg", "Midia incluida = " + midia.getId());
	}

	// métodos do controller
}
