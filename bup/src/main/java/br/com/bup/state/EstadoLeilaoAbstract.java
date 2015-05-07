package br.com.bup.state;

import br.com.bup.domain.Leilao;

public abstract class EstadoLeilaoAbstract implements EstadoLeilao {
	protected Leilao leilao;
	
	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public EstadoLeilao processar() {
		return null;
	}

	public EstadoLeilao cancelar() {
		return null;
	}
}
