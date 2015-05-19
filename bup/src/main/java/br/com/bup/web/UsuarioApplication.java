package br.com.bup.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioApplication {
	public final static Long TEMPO_OCIOSO_MIN = 30L;
	private final static Set<UsuarioSession> USUARIOS_LOGADOS = new HashSet<UsuarioSession>();
	
	public void addUsuarioLogado(UsuarioSession session) {
		USUARIOS_LOGADOS.add(session);
	}
	
	public void removerUsuarioLogado(UsuarioSession session) {
		USUARIOS_LOGADOS.remove(session);
	}
	
	public List<UsuarioSession> getUsuariosLogados() {
		return new ArrayList<UsuarioSession>(USUARIOS_LOGADOS);
	}
	
	public void deslogarLogadosOciosos() {
		List<UsuarioSession> listAux = new ArrayList<UsuarioSession>(USUARIOS_LOGADOS);
		
		for (UsuarioSession usuarioSession : listAux) {
			Long tempoOcioso = usuarioSession.getTempoOcioso();
			
			if (!usuarioSession.isLogado() || tempoOcioso >= TEMPO_OCIOSO_MIN) {
				usuarioSession.deslogar();
				USUARIOS_LOGADOS.remove(usuarioSession);
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
