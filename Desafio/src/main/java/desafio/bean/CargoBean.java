package desafio.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import desafio.dao.CargoDAO;
import desafio.entidade.Cargo;

public class CargoBean extends BeanGenerico {

	CargoDAO cargoDAO = new CargoDAO();
	Cargo cargo = new Cargo();
	private List<Object> cargoLista = new ArrayList<Object>();
	String msgValida = "";

	public CargoBean() {
	}

	// Preenche a lista de cargos
	public void listar() {
		if (getCargoLista().isEmpty())
			try {
				setCargoLista(cargoDAO.listar());
			} catch (Exception e) {
				setCargoLista(null);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "Ocorreu um erro no sistema ou banco de dados"));
				e.printStackTrace();
			}
	}

	// Recupera os dados do cargo
	public void carregaCargo() {
		try {
			setCargo((Cargo) cargoDAO.buscar(cargo.getId()));
		} catch (Exception e) {
			setCargo(null);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
					"Ocorreu um erro no sistema ou banco de dados"));
			e.printStackTrace();
		}
		if (getCargo() == null)
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Não foi possível recuperar os dados"));
	}

	// Valida e persiste os dados do cargo em alteracao
	public String atualizar() {
		Cargo cargoExiste = null;
		try {
			cargoExiste = (Cargo) cargoDAO.buscarPorNome(cargo.getNome());
		} catch (Exception e) {
			addMsgValida("Ocorreu um erro no sistema ou banco de dados");
			e.printStackTrace();
		}

		msgValida += cargo.validaNome();
		if (cargoExiste != null)
			addMsgValida("O nome do cargo já foi cadastrado");

		if (msgValida.equals("")) {
			try {
				cargoDAO.alterar(cargo);
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

	// Valida e insere os dados do novo cargo
	public String cadastrar() {
		Cargo cargoExiste = null;
		try {
			cargoExiste = (Cargo) cargoDAO.buscarPorNome(cargo.getNome());
		} catch (Exception e) {
			addMsgValida("Ocorreu um erro no sistema ou banco de dados");
		}

		msgValida += cargo.validaNome();
		if (cargoExiste != null)
			addMsgValida("O nome do cargo já foi cadastrado");

		if (msgValida.equals("")) {
			try {
				cargoDAO.cadastrar(cargo);
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
	
	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<Object> getCargoLista() {
		return cargoLista;
	}

	public void setCargoLista(List<Object> list) {
		this.cargoLista = list;
	}

}
