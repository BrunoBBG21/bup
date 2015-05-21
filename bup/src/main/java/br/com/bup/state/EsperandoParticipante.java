package br.com.bup.state;

import java.util.Date;

public class EsperandoParticipante extends EstadoLeilaoAbstract {
	
	@Override
	public void cancelar() {
		if (leilao.getInscritos().isEmpty()) {
			leilao.setEstado(TipoEstadoLeilao.CANCELADO);
		}
	}
	
	@Override
	public void processar() {
		if (!leilao.getDataInicio().after(new Date())) {
			leilao.setEstado(TipoEstadoLeilao.EM_ANDAMENTO);
		}
	}
}
