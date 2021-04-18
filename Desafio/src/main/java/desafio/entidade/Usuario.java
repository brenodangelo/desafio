package desafio.entidade;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DESAFIO_USUARIO", schema = "desafio")
@SequenceGenerator(name = "SQ", sequenceName = "SEQ_USUARIO", allocationSize = 1)
public class Usuario extends Pessoa implements Comparable<Usuario>  {

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="DESAFIO_USUARIO_PERFIL", schema = "desafio", joinColumns=
    {@JoinColumn(name="idusuario")}, inverseJoinColumns=
      {@JoinColumn(name="idperfil")})
    private List<Perfil> perfis;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name = "idCargo")
	private Cargo cargo;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ")
	private int id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cpf", unique = true, nullable = false)
	private String cpf;

	@Column(name = "dataNascimento")
	private Date dataNascimento;

	@Column(name = "sexo")
	private String sexo;

	@Column(name = "dataCadastro")
	private Date dataCadastro;



	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo.toUpperCase();
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public String getSexo() {
		return sexo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public String validaNome() {
		if (nome == null || nome.equals("")) return "Nome inválido";
		if (nome.length() >+ 255) return "O nome ultrapassou 255 caracteres";
		return "";
	}

	public String validaCpf() {
		// Informacoes da validacao:
		// https://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097
		// Considera-se erro cpfs formados por uma sequencia de numeros iguais
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11))
			return "CPF inválido";

		char dig10, dig11;
		int sm, i, r, num, peso;
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica
			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return "";
			else
				return "CPF inválido";
		} catch (InputMismatchException erro) {
			return "CPF inválido";
		}
	}

	public String validaDataNascimento() {
		Date agora = new Date();
		if (dataNascimento != null && agora.compareTo(dataNascimento) <= 0)
			return "Data de nascimento inválida";
		return "";
	}

	public String validaDataCadastro() {
		Date agora = new Date();
		if (dataCadastro != null && agora.compareTo(dataCadastro) <= 0)
			return "Data de cadastro inválida";
		return "";
	}

	public String validaSexo() {
		if (sexo == null || sexo.equals("") || sexo.equals("M") || sexo.equals("F"))
			return "";
		return "Sexo inválido";
	}

	public String validaCargo() {
		if (cargo == null)
			return "Cargo inválido";
		return "";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((perfis == null) ? 0 : perfis.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (perfis == null) {
			if (other.perfis != null)
				return false;
		} else if (!perfis.equals(other.perfis))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		return true;
	}

	@Override
	public int compareTo(Usuario o) {
		return this.nome.compareTo(o.nome);
	}

}