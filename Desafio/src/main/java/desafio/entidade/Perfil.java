package desafio.entidade;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DESAFIO_PERFIL", schema = "desafio")
@SequenceGenerator(name = "SQ", sequenceName = "SEQ_PERFIL", allocationSize = 1)
public class Perfil implements Comparable<Perfil> {

    @ManyToMany(mappedBy="perfis" , fetch=FetchType.EAGER)
    private List<Usuario> usuarios;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ")
	private int id;

	@Column(name = "nome", nullable = false, unique = true)
	private String nome;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public String validaNome() {
		if (nome == null || nome.equals("")) return "Nome inválido";
		if (nome.length() >= 255) return "O nome ultrapassou 255 caracteres";
		return "";
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Perfil other = (Perfil) obj;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Perfil [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public int compareTo(Perfil o) {
		return this.nome.compareTo(o.nome);
	}

}