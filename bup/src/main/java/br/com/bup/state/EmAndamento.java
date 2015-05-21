package br.com.bup.state;

public class EmAndamento extends EstadoLeilaoAbstract {
	
	@Override
	public void processar() {
		if (!leilao.getLances().isEmpty()) {
			leilao.setEstado(TipoEstadoLeilao.AGUARDANDO);
		}
	}
	
	@Override
	public void cancelar() {
		leilao.setEstado(TipoEstadoLeilao.CANCELADO);
	}
}
