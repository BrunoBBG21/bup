package br.com.bup.state;

public enum TipoEstadoLeilao {
	AGUARDANDO("Aguardando Lances", AguardandoLances.class),
	CANCELADO("Cancelado", Cancelado.class),
	EM_ANDAMENTO("Em Andamento", EmAndamento.class),
	ESPERANDO("Esperando Participantes", EsperandoParticipante.class),
	FINALIZADO("Finalizado", Finalizar.class);
	
	private String descricao;
	private Class<? extends EstadoLeilao> impl;

	private TipoEstadoLeilao(String descricao, Class<? extends EstadoLeilao> impl) {
		this.descricao = descricao;
		this.impl = impl;
	}
	
	/**
	 * Retorna uma instancia que contem a implementação da logica do estado.
	 * @return EstadoLeilao.
	 * @throws InstantiationException 
	 * @throws IllegalAccessException
	 */
	public EstadoLeilao getInstance() throws InstantiationException, IllegalAccessException {
		return impl.newInstance();
	}

	public String getDescricao() {
		return descricao;
	}
	public Class<? extends EstadoLeilao> getImpl() {
		return impl;
	}
}
