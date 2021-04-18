package desafio.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import desafio.dao.CargoDAO;
import desafio.dao.PerfilDAO;
import desafio.dao.UsuarioDAO;
import desafio.entidade.Cargo;
import desafio.entidade.Perfil;
import desafio.entidade.Usuario;

public class UsuarioBean extends BeanGenerico {

	UsuarioDAO usuarioDAO = new UsuarioDAO();
	CargoDAO cargoDAO = new CargoDAO();
	PerfilDAO perfilDAO = new PerfilDAO();
	Usuario usuario = new Usuario();
	private List<Object> usuarioLista = new ArrayList<Object>();
	private List<Object> listaCargos;
	private List<Object> listaPerfis;
	String msgValida = "";
	int cargoSelecionado;
	List<String> perfisSelecionados;

	public UsuarioBean() {
		setListaCargos(cargoDAO.listar());
		setListaPerfis(perfilDAO.listar());
	}

	public void listar() {
		if (getUsuarioLista().isEmpty())
			try {
				setUsuarioLista(usuarioDAO.listar());
			} catch (Exception e) {
				setUsuarioLista(null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Ocorreu um erro no sistema ou banco de dados"));
				e.printStackTrace();
			}
	}

	public void carregaUsuario() {
		try {
			setUsuario((Usuario) usuarioDAO.buscar(usuario.getId()));
		} catch (Exception e) {
			setUsuario(null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Ocorreu um erro no sistema ou banco de dados"));
			e.printStackTrace();
		}
		if (getUsuario() != null) {
			setCargoSelecionado(getUsuario().getCargo().getId());
			List<String> listaIdPerfil = new ArrayList<String>();
			for (Perfil perfil : getUsuario().getPerfis())
				listaIdPerfil.add(""+perfil.getId()) ;
			setPerfisSelecionados(listaIdPerfil);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Não foi possível recuperar os dados"));
		}
	}

	public String atualizar() {
		Usuario usuarioExiste = null;
		usuario.setCargo((Cargo) cargoDAO.buscar(cargoSelecionado));
		try {
			usuarioExiste = (Usuario) usuarioDAO.buscarPorCpf(usuario.getCpf());
		} catch (Exception e) {
			addMsgValida("Ocorreu um erro no sistema ou banco de dados");
			e.printStackTrace();
		}

		addMsgValida(usuario.validaNome());
		addMsgValida(usuario.validaCpf());
		addMsgValida(usuario.validaDataNascimento());
		addMsgValida(usuario.validaSexo());
		addMsgValida(usuario.validaCargo());

		if (usuarioExiste != null && usuarioExiste.getId() != usuario.getId())
			addMsgValida("Já existe um usuário com o CPF informado");

		if (msgValida.equals("")) {
			List<Perfil> listaPerfis = new ArrayList<Perfil>();
			for (String idPerfil : getPerfisSelecionados())
				listaPerfis.add((Perfil) perfilDAO.buscar(Integer.parseInt(idPerfil)));
			usuario.setPerfis(listaPerfis);

			try {
				usuarioDAO.alterar(usuario);
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
		Usuario usuarioExiste = null;
		usuario.setCargo((Cargo) cargoDAO.buscar(cargoSelecionado));

		try {
			usuarioExiste = (Usuario) usuarioDAO.buscarPorCpf(usuario.getCpf());
		} catch (Exception e) {
			addMsgValida("Ocorreu um erro no sistema ou banco de dados");
		}

		addMsgValida(usuario.validaNome());
		addMsgValida(usuario.validaCpf());
		addMsgValida(usuario.validaDataNascimento());
		addMsgValida(usuario.validaSexo());
		addMsgValida(usuario.validaCargo());

		if (usuarioExiste != null)
			addMsgValida("Já existe um usuário com o CPF informado");

		if (msgValida.equals("")) {
			List<Perfil> listaPerfis = new ArrayList<Perfil>();
			for (String idPerfil : getPerfisSelecionados())
				listaPerfis.add((Perfil) perfilDAO.buscar(Integer.parseInt(idPerfil)));
			usuario.setPerfis(listaPerfis);
			try {
				usuario.setDataCadastro(new Date());
				usuarioDAO.cadastrar(usuario);
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
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Object> getUsuarioLista() {
		return usuarioLista;
	}

	public void setUsuarioLista(List<Object> list) {
		this.usuarioLista = list;
	}

	public List<Object> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<Object> list) {
		this.listaCargos = list;
	}

	public int getCargoSelecionado() {
		return cargoSelecionado;
	}

	public void setCargoSelecionado(int cargoSelecionado) {
		this.cargoSelecionado = cargoSelecionado;
	}

	public List<Object> getListaPerfis() {
		return listaPerfis;
	}

	public void setListaPerfis(List<Object> listaPerfis) {
		this.listaPerfis = listaPerfis;
	}

	public List<String> getPerfisSelecionados() {
		return perfisSelecionados;
	}

	public void setPerfisSelecionados(List<String> perfisSelecionados) {
		this.perfisSelecionados = perfisSelecionados;
	}

	int[] toIntArray(List<Integer> list) {
		int[] ret = new int[list.size()];
		int i = 0;
		for (Integer e : list)
			ret[i++] = e.intValue();
		return ret;
	}

}
