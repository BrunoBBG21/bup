package br.com.bup.state;

import java.util.Date;

public class AguardandoLances extends EstadoLeilaoAbstract {
	
	@Override
	public void processar() {
		if (!leilao.getDataFim().after(new Date())) { //caso fim seja igual 
			leilao.setEstado(TipoEstadoLeilao.FINALIZADO);
		}
	}
}
