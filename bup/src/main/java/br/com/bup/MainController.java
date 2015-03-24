package br.com.bup;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.bup.dao.MidiaDAO;

@Named
@ViewScoped
public class MainController implements Serializable {

	private static final long serialVersionUID = 3973801993975443027L;

	private String name;
	private String primeName;
	private String richName;
	
	@Inject
	private MidiaDAO midiaDAO;

	@PostConstruct
	public void init() {
		System.out.println(" Bean executado! ");
		midiaDAO.teste();
	}

	public String getName() {
		midiaDAO.teste();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimeName() {
		return primeName;
	}

	public void setPrimeName(String primeName) {
		this.primeName = primeName;
	}

	public String getRichName() {
		return richName;
	}

	public void setRichName(String richName) {
		this.richName = richName;
	}
}