package desafio.bean;

import java.util.Date;

public class Login extends BeanGenerico {

	private Date agora = new Date();

	public void logar() {
		if (!super.isLogado()) {
			super.usuarioLogado.setId(0);
			super.usuarioLogado.setNome("Usuário genérico para testes");
			super.setHoraLogin(agora.getTime());
			super.setLogado(true);
		}

	}

	public Date getHoraLoginDate() {
		return new Date(super.getHoraLogin());
	}

}