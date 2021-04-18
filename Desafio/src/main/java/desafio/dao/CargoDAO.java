package desafio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import desafio.entidade.Cargo;

public class CargoDAO extends DAO<Object> {

	@SuppressWarnings("unchecked")
	public List<Object> listar() {
		Session session = getSession();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Cargo").list();
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
		Cargo cargo = (Cargo) session.get(Cargo.class, id);
		session.close();
		return cargo;
	}

	public Object buscarPorNome(String nome) {
		Session session = getSession();
		Cargo cargo = (Cargo) session.createQuery("from Cargo where nome = :nome").setParameter("nome", nome).uniqueResult();
		session.close();
		return cargo;
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