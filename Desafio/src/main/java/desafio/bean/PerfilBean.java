package desafio.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import desafio.dao.PerfilDAO;
import desafio.entidade.Perfil;

public class PerfilBean extends BeanGenerico {

	PerfilDAO perfilDAO = new PerfilDAO();
	Perfil perfil = new Perfil();
	private List<Object> perfilLista = new ArrayList<Object>();
	String msgValida = "";

	public PerfilBean() {
	}

	public String excluir(int idPerfil) {
		Perfil perfilDelete = (Perfil) perfilDAO.buscar(idPerfil);
		if (perfilDelete.getUsuarios().size() == 0) {
			perfilDAO.remover(idPerfil);
			perfilLista.clear();
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Não é possível excluir, ainda existem usuários usando o perfil"));
		}
		return "";
	}
	
	public void listar() {
		if (getPerfilLista().isEmpty())
			try {
				setPerfilLista(perfilDAO.listar());
			} catch (Exception e) {
				setPerfilLista(null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Ocorreu um erro no sistema ou banco de dados"));
				e.printStackTrace();
			}
	}

	public void carregaPerfil() {
		try {
			setPerfil((Perfil) perfilDAO.buscar(perfil.getId()));
		} catch (Exception e) {
			setPerfil(null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Ocorreu um erro no sistema ou banco de dados"));
			e.printStackTrace();
		}
		if (getPerfil() == null)
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Não foi possível recuperar os dados"));
	}

	public String atualizar() {
		Perfil perfilExiste = null;
		try {
			perfilExiste = (Perfil) perfilDAO.buscarPorNome(perfil.getNome());
		} catch (Exception e) {
			addMsgValida("Ocorreu um erro no sistema ou banco de dados");
			e.printStackTrace();
		}

		addMsgValida(perfil.validaNome());
		if (perfilExiste != null)
			addMsgValida("O nome do perfil já foi cadastrado");

		if (msgValida.equals("")) {
			try {
				perfilDAO.alterar(perfil);
			} catch (Exception e) {
				addMsgValida("Ocorreu um erro no sistema ou banco de dados");
				e.printStackTrace();
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Alteração feita com sucesso"));
			return "inicio";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msgValida));
			msgValida = "";
			return "";
		}
	}

	public String cadastrar() {
		Perfil perfilExiste = null;
		try {
			perfilExiste = (Perfil) perfilDAO.buscarPorNome(perfil.getNome());
		} catch (Exception e) {
			addMsgValida("Ocorreu um erro no sistema ou banco de dados");
		}

		addMsgValida(perfil.validaNome());
		if (perfilExiste != null)
			addMsgValida("O nome do perfil já foi cadastrado");

		if (msgValida.equals("")) {
			try {
				perfilDAO.cadastrar(perfil);
			} catch (Exception e) {
				addMsgValida("Ocorreu um erro no sistema ou banco de dados");
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro feito com sucesso"));
			return "inicio";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msgValida));
			msgValida = "";
			return "";
		}

	}

	// Monta a mensagem de erros no formulario em uma unica String
	public void addMsgValida(String msg) {
		if (msgValida.equals("")) msgValida = msg;
		else if (!msg.equals("")) msgValida += ", " + msg;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Object> getPerfilLista() {
		return perfilLista;
	}

	public void setPerfilLista(List<Object> list) {
		this.perfilLista = list;
	}

}
