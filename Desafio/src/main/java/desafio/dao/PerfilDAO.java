package desafio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import desafio.entidade.Perfil;

public class PerfilDAO extends DAO<Object> {

	@SuppressWarnings("unchecked")
	public List<Object> listar() {
		Session session = getSession();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Perfil").list();
		session.close();
		return lista;
	}
	
	public void cadastrar(Object t) {
		Session session = getSession();
		Transaction trs = session.beginTransaction();
		session.save(t);
		trs.commit();
		session.flush();
		session.close();
	}

	public Object buscar(Integer id) {
		Session session = getSession();
		Perfil perfil = (Perfil) session.get(Perfil.class, id);
		session.close();
		return perfil;
	}

	public Object buscarPorNome(String nome) {
		Session session = getSession();
		Perfil perfil = (Perfil) session.createQuery("from Perfil where nome = :nome").setParameter("nome", nome).uniqueResult();
		session.close();
		return perfil;
	}

	public void alterar(Object t) {
		Session session = getSession();
		Transaction trs = session.beginTransaction();
		session.update(t);
		trs.commit();
		session.flush();
		session.close();
	}

	public void remover(Integer id) {
		Session session = getSession();
		Transaction trs = session.beginTransaction();
		session.delete(buscar(id));
		trs.commit();
		session.flush();
		session.close();
	}

}