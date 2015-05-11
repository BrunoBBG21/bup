package br.com.bup.state;

/**
 * Fazes do leilao.
 * 
 * 	ESPERANDO- estado inicial para o tempo de inscrição - pode ir para CANCELADO ou EM_ANDAMENTO
 * 	CANCELADO- estado final
 * 	EM_ANDAMENTO- aguardando o primeiro lance valido - pode ir para CANCELADO ou AGUARDANDO
 * 	AGUARDANDO- aguardando outros lances validos ate o termino do leilao - pode ir apenas para FINALIZADO
 * 	FINALIZADO- estado final
 * 
 */
public enum TipoEstadoLeilao {
	ESPERANDO("Esperando Participantes", EsperandoParticipante.class),
	CANCELADO("Cancelado", Cancelado.class),
	EM_ANDAMENTO("Em Andamento", EmAndamento.class),
	AGUARDANDO("Aguardando Lances", AguardandoLances.class),
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
