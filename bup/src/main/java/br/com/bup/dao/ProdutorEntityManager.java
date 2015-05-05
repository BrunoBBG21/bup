package br.com.bup.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.ContaBancaria;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.FormatoEspacoPropaganda;
import br.com.bup.domain.Midia;
import br.com.bup.domain.ModalidadePagamento;
import br.com.bup.domain.PublicoAlvo;
import br.com.caelum.vraptor.validator.I18nMessage;

public class ProdutorEntityManager {
	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("bup");
	static{
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Agencia admin = new Agencia();
		admin.setCep("0");
		admin.setCnpj("0");
		admin.setEmail("admin@a.a");
		admin.setEndereco("bup");
		admin.setNome("Admin do BUP");
		admin.setPassword("141");
		admin.setTelefone("21");
		
		Agencia a = new Agencia();
		a.setCep("1");
		a.setCnpj("2");
		a.setEmail("a@a.a");
		a.setEndereco("e");
		a.setNome("Agencia do BUP");
		a.setPassword("a");
		a.setTelefone("21");
		
		ContaBancaria contaA= new ContaBancaria();
		contaA.setAgencia("1");
		contaA.setAtiva(Boolean.TRUE);
		contaA.setBanco("1");
		contaA.setConta("1");
		contaA.setUsuario(a);
		
		ContaBancaria contaC= new ContaBancaria();
		contaC.setAgencia("3");
		contaC.setAtiva(Boolean.FALSE);
		contaC.setBanco("3");
		contaC.setConta("3");
		contaC.setUsuario(a);
		
		Anunciante bup = new Anunciante();
		bup.setCep("2");
		bup.setCpf("1");
		bup.setEmail("b@b.b");
		bup.setEndereco("n");
		bup.setNome("Anunciante do BUP");
		bup.setPassword("b");
		bup.setTelefone("21");
		bup.setGerenciado(a);
	
		UsuarioDAO u = new UsuarioDAO(em);
		u.salvar(admin);
		u.salvar(a);
		u.salvar(bup);
		
		ContaBancaria contaB= new ContaBancaria();
		contaB.setAgencia("2");
		contaB.setAtiva(Boolean.TRUE);
		contaB.setBanco("2");
		contaB.setConta("2");
		contaB.setUsuario(bup);
		
		ContaBancaria contaD= new ContaBancaria();
		contaD.setAgencia("4");
		contaD.setAtiva(Boolean.FALSE);
		contaD.setBanco("4");
		contaD.setConta("4");
		contaD.setUsuario(bup);
		
		ContaBancariaDAO contaDAO = new ContaBancariaDAO(em);
		contaDAO.salvar(contaA);
		contaDAO.salvar(contaB);
		contaDAO.salvar(contaC);
		contaDAO.salvar(contaD);
		
		Midia m = new Midia();
		m.setTipo("Banner");
		MidiaDAO mDAO = new MidiaDAO(em);
		mDAO.salvar(m);
		
		ModalidadePagamento p = new ModalidadePagamento();
		p.setTipo("A vista");
		p.setValorMinParcela(BigDecimal.ZERO);
		p.setMaxParcela(0);
		p.setMidia(m);
		
		ModalidadePagamento p1 = new ModalidadePagamento();
		p1.setTipo("3x");
		p1.setValorMinParcela(BigDecimal.ONE);
		p1.setMaxParcela(3);
		p1.setMidia(m);
		
		ModalidadePagamento p2 = new ModalidadePagamento();
		p2.setTipo("0+3x");
		p2.setValorMinParcela(BigDecimal.ZERO);
		p2.setMaxParcela(3);
		p2.setMidia(m);
		
		ModalidadePagamentoDAO pDAO = new ModalidadePagamentoDAO(em);
		pDAO.salvar(p);
		pDAO.salvar(p1);
		pDAO.salvar(p2);
		
		PublicoAlvo ca = new PublicoAlvo();
		ca.setNome("Classe A");
		ca.setDescricao("Classficação na faixa economica");
		PublicoAlvo cb = new PublicoAlvo();
		
		cb.setNome("Classe B");
		cb.setDescricao("Classficação na faixa economica");
		PublicoAlvo cc = new PublicoAlvo();
		
		cc.setNome("Classe C");
		cc.setDescricao("Classficação na faixa economica");
		PublicoAlvo cd = new PublicoAlvo();
		
		cd.setNome("Classe D");
		cd.setDescricao("Classficação na faixa economica");
		
		PublicoAlvo es = new PublicoAlvo();
		es.setNome("Ensino Superior");
		es.setDescricao("Classficação na faixa de escolariedade");
		PublicoAlvo eme = new PublicoAlvo();
		eme.setNome("Ensino Médio");
		eme.setDescricao("Classficação na faixa de escolariedade");
		PublicoAlvo ef = new PublicoAlvo();
		ef.setNome("Ensino Fundamental");
		ef.setDescricao("Classficação na faixa de escolariedade");
		
		PublicoAlvo infantil = new PublicoAlvo();
		infantil.setNome("Infantil");
		infantil.setDescricao("Classficação etária");
		PublicoAlvo adolescente = new PublicoAlvo();
		adolescente.setNome("Adolescente");
		adolescente.setDescricao("Classficação etária");
		PublicoAlvo adulto = new PublicoAlvo();
		adulto.setNome("Adulto");
		adulto.setDescricao("Classficação etária");
		PublicoAlvo aposentado = new PublicoAlvo();
		aposentado.setNome("Aposentado");
		aposentado.setDescricao("Classficação etária");
		
		PublicoAlvoDAO paDAO = new PublicoAlvoDAO(em);
		paDAO.salvar(ca);
		paDAO.salvar(cb);
		paDAO.salvar(cc);
		paDAO.salvar(cd);
		
		paDAO.salvar(es);
		paDAO.salvar(eme);
		paDAO.salvar(ef);
		
		paDAO.salvar(infantil);
		paDAO.salvar(adolescente);
		paDAO.salvar(adulto);
		paDAO.salvar(aposentado);
		
		EspacoPropaganda ep = new EspacoPropaganda();
		ep.setUrl("http://b.up");
		ep.setAltura(40.00);
		ep.setLargura(30.00);
		ep.setDescricao("site de leilao da propaganda");
		ep.setFormatoEspacoPropaganda(FormatoEspacoPropaganda.IMAGEM);
		ep.setMidia(m);
		ep.setPeriodo(1);
		ep.setPageViews(1000000l);
		ep.setPosicaoTela("Topo");
		ep.setPesoMaximo(1000);
		ep.setPertence(bup);
		List<PublicoAlvo> alvos = new ArrayList<PublicoAlvo>();
		alvos.add(adolescente);
		alvos.add(aposentado);
		alvos.add(es);
		alvos.add(ca);
		ep.setPublicosAlvos(alvos);
		EspacoPropagandaDAO epDAO = new EspacoPropagandaDAO(em); 
		epDAO.salvar(ep);
		em.getTransaction().commit();
		em.close();
	}
	
	@Produces
	@RequestScoped
	public EntityManager criaEntityManager() {
		return factory.createEntityManager();
	}

	public void finaliza(@Disposes EntityManager manager) {
		manager.close();
	}
}
