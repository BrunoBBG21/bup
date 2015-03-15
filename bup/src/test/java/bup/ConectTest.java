package bup;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.bup.domain.Leilao;

public class ConectTest extends TestCase {
	private SessionFactory sessionFactory;

	@Override
	protected void setUp() throws Exception {
		// A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
	}

	@Override
	protected void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void testBasicUsage() {
		//criando massa
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Leilao leilao = new Leilao();
		leilao.setId(1L);
		leilao.setDescricao("descricao 1");
		session.save(leilao);
		
		leilao = new Leilao();
		leilao.setId(2L);
		leilao.setDescricao("descricao 2");
		session.save(leilao);
		
		session.getTransaction().commit();
		session.close();
		
		//busncando
		session = sessionFactory.openSession();
//        session.beginTransaction();
        List<Leilao> result = session.createQuery( "from Leilao" ).list();
		assertEquals(result.size(), 2);
//        session.getTransaction().commit();
        session.close();
	}
}
