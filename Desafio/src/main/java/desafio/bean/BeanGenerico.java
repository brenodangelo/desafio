package desafio.bean;

import desafio.entidade.Usuario;

public abstract class BeanGenerico {

	// Dados do usuario do sistema que ficarao no escopo da sessao
	Usuario usuarioLogado = new Usuario();
	Long horaLogin = null;
	boolean logado = false;

	public Long getHoraLogin() {
		return horaLogin;
	}

	public void setHoraLogin(Long horaLogin) {
		this.horaLogin = horaLogin;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

}