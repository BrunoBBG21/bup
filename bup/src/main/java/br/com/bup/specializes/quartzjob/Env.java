package br.com.bup.specializes.quartzjob;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.servlet.ServletContext;

import br.com.caelum.vraptor.environment.Environment;

@ApplicationScoped
@Specializes
public class Env extends br.com.caelum.vraptor.quartzjob.Env {
	
	@Deprecated
	// CDI eyes only
	public Env() {
	}
	
	@Inject
	public Env(Environment env, ServletContext context) {
		super(env, context);
	}
	
	@Override
	public String getHostAndContext() {
		return host();
	}
}
