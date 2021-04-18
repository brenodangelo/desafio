package desafio.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import desafio.entidade.Usuario;

public class UsuarioDAO extends DAO<Object> {

	@SuppressWarnings("unchecked")
	public List<Object> listar() {
		Session session = getSession();
		@SuppressWarnings("rawtypes")
		List lista = session.createQuery("from Usuario").list();
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
		Usuario usuario = (Usuario) session.get(Usuario.class, id);
		session.close();
		return usuario;
	}

	public Object buscarPorCpf(String cpf) {
		Session session = getSession();
		Usuario usuario = (Usuario) session.createQuery("from Usuario where cpf = :cpf").setParameter("cpf", cpf).uniqueResult();
		session.close();
		return usuario;
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