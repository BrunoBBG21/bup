package br.com.bup.state;

import br.com.bup.domain.Leilao;

public interface EstadoLeilao {
	void setLeilao(Leilao leilao);
	EstadoLeilao processar();
	EstadoLeilao cancelar();
}
