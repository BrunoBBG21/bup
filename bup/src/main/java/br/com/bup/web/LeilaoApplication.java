package br.com.bup.web;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.bup.domain.Leilao;

/**
 * Componente que mantem os leiloes em amdamento em memoria afim de facilitar o acesso.
 */
@ApplicationScoped
public class LeilaoApplication {
	private final static List<Leilao> LEILOES_EM_ANDAMENTO = new ArrayList<Leilao>();
	
	@Inject
	private UsuarioApplication usuarioApplication;
	
	public void addLeilao(Leilao leilao) {
		LEILOES_EM_ANDAMENTO.add(leilao);
	}
	
	public List<Leilao> getLeiloesEmAndamento() {
		return new ArrayList<Leilao>(LEILOES_EM_ANDAMENTO);
	}
	
	public Leilao getLeilaoPorId(Long leilaoId) {
		Leilao value = null;
		
		for (Leilao leilao : LEILOES_EM_ANDAMENTO) {
			if (leilao.getId().equals(leilaoId)) {
				value = leilao;
				break;
			}
		}
		
		return value;
	}
	
	public List<Leilao> removerLeiloesFinalizados() {
		List<Leilao> listAux = new ArrayList<Leilao>(LEILOES_EM_ANDAMENTO);
		List<Leilao> value = new ArrayList<Leilao>();
		
		for (Leilao leilao : listAux) {
			if (!leilao.isEstadoEmAndamento()) {
				value.add(leilao);
				LEILOES_EM_ANDAMENTO.remove(leilao);
			}
		}
		
		return value;
	}
	
	public void notificarObservers() {
		for (Leilao leilao : LEILOES_EM_ANDAMENTO) {
			leilao.notifyObservers();
		}
	}
	
	public void atualizarLeiloes(List<Leilao> leiloes) {
		List<Leilao> leiloesNovos = getNovosLeiloes(leiloes);
		
		for (Leilao leilao : leiloesNovos) {
			for (UsuarioSession session : usuarioApplication.getUsuariosLogados()) {
				if (session.getIdsLeiloesObserver().contains(leilao.getId())) {
					leilao.addObserver(session);
				}
			}
			addLeilao(leilao);
		}
	}
	
	private List<Leilao> getNovosLeiloes(List<Leilao> leiloes) {
		List<Leilao> value = new ArrayList<Leilao>();
		
		for (Leilao leilao : leiloes) {
			if (getLeilaoPorId(leilao.getId()) == null) {
				value.add(leilao);
			}
		}
		
		return value;
	}
	
	public void atualizarObserver(UsuarioSession usuarioSession) {
		for (Leilao leilao : LEILOES_EM_ANDAMENTO) {
			if (usuarioSession.getIdsLeiloesObserver().contains(leilao.getId())) {
				leilao.addObserver(usuarioSession);
				
			} else {
				leilao.deleteObserver(usuarioSession);
			}
		}
	}
}
