package desafio.entidade;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DESAFIO_CARGO", schema = "desafio")
@SequenceGenerator(name = "SQ", sequenceName = "SEQ_CARGO", allocationSize = 1)
public class Cargo implements Comparable<Cargo> {

	@OneToMany(cascade=CascadeType.ALL, mappedBy="cargo", fetch=FetchType.LAZY)
	private Set<Usuario> usuarios;

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

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
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
		Cargo other = (Cargo) obj;
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
		return "Cargo [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public int compareTo(Cargo o) {
		return this.nome.compareTo(o.nome);
	}

}