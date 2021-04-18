package desafio.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public abstract class DAO<T> {

	private static SessionFactory sf;
	// inicializacao estatica da Session Factory
	static {
		sf = new AnnotationConfiguration().configure().buildSessionFactory();
	}

	// recuperacao de uma Session Hibernate a partir de uma Factory
	public static Session getSession() {

		if (sf.isClosed()) {
			sf = new Configuration().configure().buildSessionFactory();
		}

		try {
			return sf.getCurrentSession();
		} catch (HibernateException n) {
			return sf.openSession();
		}
	}

	public abstract void cadastrar(T t);
	
	public abstract T buscar(Integer id);

	public abstract List<T> listar();

	public abstract void alterar(T t);

	public abstract void remover(Integer id);

}