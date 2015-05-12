package br.com.bup.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioApplication {
	public static final Long TEMPO_OCIOSO_MIN = 30L; 
	private Set<UsuarioSession> usuariosLogados = new HashSet<UsuarioSession>();
	
	public void addUsuarioLogado(UsuarioSession session) {
		usuariosLogados.add(session);
	}
	
	public void removerUsuarioLogado(UsuarioSession session) {
		usuariosLogados.remove(session);
	}
	
	public List<UsuarioSession> getUsuariosLogados() {
		return new ArrayList<UsuarioSession>(usuariosLogados);
	}

	public void deslogarLogadosOciosos() {
		List<UsuarioSession> listAux = new ArrayList<UsuarioSession>(usuariosLogados);
		
		for (UsuarioSession usuarioSession : listAux) {
			Long tempoOcioso = usuarioSession.getTempoOcioso();
			
			if (!usuarioSession.isLogado() || tempoOcioso >= TEMPO_OCIOSO_MIN) {
				usuarioSession.deslogar();
				usuariosLogados.remove(usuarioSession);
			}
		}
	}
}
