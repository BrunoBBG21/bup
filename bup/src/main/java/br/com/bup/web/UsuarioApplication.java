package br.com.bup.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UsuarioApplication {
	private final static Logger LOGGER = LoggerFactory.getLogger(UsuarioApplication.class);
	private final static Set<UsuarioSession> USUARIOS_LOGADOS = new HashSet<UsuarioSession>();
	public final static Long TEMPO_OCIOSO_MIN = 30L;
	
	public void addUsuarioLogado(UsuarioSession session) {
		if (session != null) {
			LOGGER.debug("Adicionando UsuarioSession; " + "logadoId: "
					+ (session.isLogado() ? session.getUsuarioLogado().getId() : null) + ", session: " + session);
			USUARIOS_LOGADOS.add(session);
		}
	}
	
	public void removerUsuarioLogado(UsuarioSession session) {
		if (session != null) {
			LOGGER.debug("Removendo UsuarioSession; " + "logadoId: "
					+ (session.isLogado() ? session.getUsuarioLogado().getId() : null) + ", session: " + session);
			USUARIOS_LOGADOS.remove(session);
		}
	}
	
	public List<UsuarioSession> getUsuariosLogados() {
		return new ArrayList<UsuarioSession>(USUARIOS_LOGADOS);
	}
	
	public void deslogarLogadosOciosos() {
		List<UsuarioSession> listAux = new ArrayList<UsuarioSession>(USUARIOS_LOGADOS);
		
		for (UsuarioSession usuarioSession : listAux) {
			Long tempoOcioso = usuarioSession.getTempoOcioso();
			
			if (!usuarioSession.isLogado() || tempoOcioso >= TEMPO_OCIOSO_MIN) {
				removerUsuarioLogado(usuarioSession);
				usuarioSession.deslogar();
			}
		}
	}
	
	public UsuarioSession getUsuarioPorId(Long usuarioId) {
		UsuarioSession value = null;
		
		for (UsuarioSession usuarioSession : USUARIOS_LOGADOS) {
			if (usuarioSession.isLogado() && usuarioSession.getUsuario().getId().equals(usuarioId)) {
				value = usuarioSession;
				break;
			}
		}
		
		return value;
	}
	
	/**
	 * Busca a agencia logada que esta gerenciando o usuario id nesse momento.
	 * 
	 * @param usuarioId
	 * @return
	 */
	public UsuarioSession getAgenciaGerenciandoUsuarioId(Long usuarioId) {
		UsuarioSession value = null;
		
		for (UsuarioSession usuarioSession : USUARIOS_LOGADOS) {
			if (usuarioSession.isGerenciando() && usuarioSession.getIdGerenciado().equals(usuarioId)) {
				value = usuarioSession;
				break;
			}
		}
		
		return value;
	}
}
