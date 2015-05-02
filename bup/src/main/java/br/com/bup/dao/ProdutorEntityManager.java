package br.com.bup.dao;

import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.bup.domain.Agencia;
import br.com.bup.domain.Anunciante;
import br.com.bup.domain.EspacoPropaganda;
import br.com.bup.domain.FormatoEspacoPropaganda;
import br.com.bup.domain.Midia;
import br.com.bup.domain.ModalidadePagamento;
import br.com.bup.domain.Usuario;

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
