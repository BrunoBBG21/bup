package br.com.bup.state;

import org.jboss.weld.exceptions.IllegalStateException;

import br.com.bup.domain.Leilao;

public abstract class EstadoLeilaoAbstract implements EstadoLeilao {
	protected Leilao leilao;
	
	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}
	
	public void processar() {
		throw new IllegalStateException("Mudanca de estado invalida.");
	}
	
	public void cancelar() {
		throw new IllegalStateException("Mudanca de estado invalida.");
	}
}
