package br.com.bup.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.bup.domain.Leilao;
import br.com.bup.domain.Midia;
import br.com.bup.domain.ModalidadePagamento;
import br.com.bup.domain.PublicoAlvo;
import br.com.bup.state.TipoEstadoLeilao;

public class ProdutorEntityManager {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("bup");
	static{
		inicio();
	}
	public static void inicio() {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		
		MidiaDAO mDAO = new MidiaDAO(em);
		UsuarioDAO u = new UsuarioDAO(em);
		LeilaoDAO lDAO = new LeilaoDAO(em);
		PublicoAlvoDAO paDAO = new PublicoAlvoDAO(em);
		ContaBancariaDAO contaDAO = new ContaBancariaDAO(em);
		EspacoPropagandaDAO epDAO = new EspacoPropagandaDAO(em);
		ModalidadePagamentoDAO pDAO = new ModalidadePagamentoDAO(em);
		
		Agencia admin = new Agencia();
		admin.setCep("0");
		admin.setCnpj("0");
		admin.setEmail("admin@a.a");
		admin.setEndereco("bup");
		admin.setNome("Admin do BUP");
		admin.setPassword("141");
		admin.setTelefone("21");
		
		admin = (Agencia) u.salvar(admin);
		
		Agencia a = new Agencia();
		a.setCep("1");
		a.setCnpj("2");
		a.setEmail("a@a.a");
		a.setEndereco("e");
		a.setNome("Agencia do BUP");
		a.setPassword("a");
		a.setTelefone("21");
		
		a = (Agencia) u.salvar(a);
		
		Anunciante bup = new Anunciante();
		bup.setCep("2");
		bup.setCpf("1");
		bup.setEmail("b@b.b");
		bup.setEndereco("n");
		bup.setNome("Anunciante do BUP");
		bup.setPassword("b");
		bup.setTelefone("21");
		bup.setGerenciado(a);
		
		bup = (Anunciante) u.salvar(bup);
		
		Anunciante bup2 = new Anunciante();
		bup2.setCep("22");
		bup2.setCpf("12");
		bup2.setEmail("b2@b.b");
		bup2.setEndereco("n2");
		bup2.setNome("Anunciante do BUP2");
		bup2.setPassword("b");
		bup2.setTelefone("21");
		bup2.setGerenciado(a);
		
		bup2 = (Anunciante) u.salvar(bup2);
		
		ContaBancaria contaA = new ContaBancaria();
		contaA.setAgencia("1");
		contaA.setAtiva(Boolean.TRUE);
		contaA.setBanco("1");
		contaA.setConta("1");
		contaA.setUsuario(a);
		
		contaA = contaDAO.salvar(contaA);
		
		ContaBancaria contaB = new ContaBancaria();
		contaB.setAgencia("2");
		contaB.setAtiva(Boolean.TRUE);
		contaB.setBanco("2");
		contaB.setConta("2");
		contaB.setUsuario(bup);
		
		contaB = contaDAO.salvar(contaB);
		
		ContaBancaria contaC = new ContaBancaria();
		contaC.setAgencia("3");
		contaC.setAtiva(Boolean.FALSE);
		contaC.setBanco("3");
		contaC.setConta("3");
		contaC.setUsuario(a);
		
		contaC = contaDAO.salvar(contaC);
		
		ContaBancaria contaD = new ContaBancaria();
		contaD.setAgencia("4");
		contaD.setAtiva(Boolean.FALSE);
		contaD.setBanco("4");
		contaD.setConta("4");
		contaD.setUsuario(bup);
		
		contaD = contaDAO.salvar(contaD);
		
		ContaBancaria contaE = new ContaBancaria();
		contaE.setAgencia("5");
		contaE.setAtiva(Boolean.TRUE);
		contaE.setBanco("5");
		contaE.setConta("5");
		contaE.setUsuario(bup2);
		
		contaE = contaDAO.salvar(contaE);
		
		Midia m = new Midia();
		m.setTipo("Banner");
		
		m = mDAO.salvar(m);
		
		ModalidadePagamento p = new ModalidadePagamento();
		p.setTipo("A vista");
		p.setValorMinParcela(BigDecimal.ZERO);
		p.setMaxParcela(0);
		p.setMidia(m);
		
		p = pDAO.salvar(p);
		
		ModalidadePagamento p1 = new ModalidadePagamento();
		p1.setTipo("3x");
		p1.setValorMinParcela(BigDecimal.ONE);
		p1.setMaxParcela(3);
		p1.setMidia(m);
		
		p1 = pDAO.salvar(p1);
		
		ModalidadePagamento p2 = new ModalidadePagamento();
		p2.setTipo("0+3x");
		p2.setValorMinParcela(BigDecimal.ZERO);
		p2.setMaxParcela(3);
		p2.setMidia(m);
		
		p2 = pDAO.salvar(p2);
		
		PublicoAlvo ca = new PublicoAlvo();
		ca.setNome("Classe A");
		ca.setDescricao("Classficação na faixa economica");
		
		ca = paDAO.salvar(ca);
		
		PublicoAlvo cb = new PublicoAlvo();
		cb.setNome("Classe B");
		cb.setDescricao("Classficação na faixa economica");
		
		cb = paDAO.salvar(cb);
		
		PublicoAlvo cc = new PublicoAlvo();
		cc.setNome("Classe C");
		cc.setDescricao("Classficação na faixa economica");
		
		cc = paDAO.salvar(cc);
		
		PublicoAlvo cd = new PublicoAlvo();
		cd.setNome("Classe D");
		cd.setDescricao("Classficação na faixa economica");
		
		cd = paDAO.salvar(cd);
		
		PublicoAlvo es = new PublicoAlvo();
		es.setNome("Ensino Superior");
		es.setDescricao("Classficação na faixa de escolariedade");
		
		es = paDAO.salvar(es);
		
		PublicoAlvo eme = new PublicoAlvo();
		eme.setNome("Ensino Médio");
		eme.setDescricao("Classficação na faixa de escolariedade");
		
		eme = paDAO.salvar(eme);
		
		PublicoAlvo ef = new PublicoAlvo();
		ef.setNome("Ensino Fundamental");
		ef.setDescricao("Classficação na faixa de escolariedade");
		
		ef = paDAO.salvar(ef);
		
		PublicoAlvo infantil = new PublicoAlvo();
		infantil.setNome("Infantil");
		infantil.setDescricao("Classficação etária");
		
		infantil = paDAO.salvar(infantil);
		
		PublicoAlvo adolescente = new PublicoAlvo();
		adolescente.setNome("Adolescente");
		adolescente.setDescricao("Classficação etária");
		
		adolescente = paDAO.salvar(adolescente);
		
		PublicoAlvo adulto = new PublicoAlvo();
		adulto.setNome("Adulto");
		adulto.setDescricao("Classficação etária");
		
		adulto = paDAO.salvar(adulto);
		
		PublicoAlvo aposentado = new PublicoAlvo();
		aposentado.setNome("Aposentado");
		aposentado.setDescricao("Classficação etária");
		
		aposentado = paDAO.salvar(aposentado);
		
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
		
		ep = epDAO.salvar(ep);
		
		EspacoPropaganda ep2 = new EspacoPropaganda();
		ep2.setUrl("http://b2.up");
		ep2.setAltura(40.00);
		ep2.setLargura(30.00);
		ep2.setDescricao("site de leilao da propaganda");
		ep2.setFormatoEspacoPropaganda(FormatoEspacoPropaganda.IMAGEM);
		ep2.setMidia(m);
		ep2.setPeriodo(1);
		ep2.setPageViews(1000000l);
		ep2.setPosicaoTela("Topo");
		ep2.setPesoMaximo(1000);
		ep2.setPertence(bup);
		alvos = new ArrayList<PublicoAlvo>();
		alvos.add(adolescente);
		alvos.add(aposentado);
		alvos.add(es);
		alvos.add(ca);
		ep2.setPublicosAlvos(alvos);
		
		ep2 = epDAO.salvar(ep2);
		
		EspacoPropaganda ep3 = new EspacoPropaganda();
		ep3.setUrl("http://b3.up");
		ep3.setAltura(40.00);
		ep3.setLargura(30.00);
		ep3.setDescricao("site b3 do b2");
		ep3.setFormatoEspacoPropaganda(FormatoEspacoPropaganda.IMAGEM);
		ep3.setMidia(m);
		ep3.setPeriodo(1);
		ep3.setPageViews(1000000l);
		ep3.setPosicaoTela("Topo");
		ep3.setPesoMaximo(1000);
		ep3.setPertence(bup2);
		alvos = new ArrayList<PublicoAlvo>();
		alvos.add(adolescente);
		alvos.add(aposentado);
		alvos.add(adulto);
		ep3.setPublicosAlvos(alvos);
		
		ep3 = epDAO.salvar(ep3);
		
		Leilao leilao = new Leilao();
		leilao.setAtivo(false);
		leilao.setDataFim(parseDate("10/10/2010"));
		leilao.setDataInicio(parseDate("01/10/2010"));
		leilao.setEspacoPropaganda(ep);
		leilao.setEstado(TipoEstadoLeilao.CANCELADO);
		leilao.setInscricao(BigDecimal.ZERO);
		leilao.setModalidadePagamento(p2);
		leilao.setReserva(BigDecimal.ZERO);
		
		leilao = lDAO.salvar(leilao);
		
		Leilao leilao2 = new Leilao();
		leilao2.setAtivo(true);
		leilao2.setDataFim(parseDate("10/10/2020"));
		leilao2.setDataInicio(parseDate("01/10/2010"));
		leilao2.setEspacoPropaganda(ep2);
		leilao2.setEstado(TipoEstadoLeilao.EM_ANDAMENTO);
		leilao2.setInscricao(BigDecimal.ZERO);
		leilao2.setModalidadePagamento(p2);
		leilao2.setReserva(BigDecimal.ZERO);
		
		leilao2 = lDAO.salvar(leilao2);
		
		Leilao leilao3 = new Leilao();
		leilao3.setAtivo(true);
		leilao3.setDataFim(parseDate("10/10/2020"));
		leilao3.setDataInicio(parseDate("01/10/2015"));
		leilao3.setEspacoPropaganda(ep3);
		leilao3.setEstado(TipoEstadoLeilao.ESPERANDO);
		leilao3.setInscricao(BigDecimal.ZERO);
		leilao3.setModalidadePagamento(p2);
		leilao3.setReserva(BigDecimal.ZERO);
		
		leilao3 = lDAO.salvar(leilao3);
		
		em.getTransaction().commit();
		em.close();
	}

	private static Date parseDate(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date parse = null;
		try {
			parse = sdf.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
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
