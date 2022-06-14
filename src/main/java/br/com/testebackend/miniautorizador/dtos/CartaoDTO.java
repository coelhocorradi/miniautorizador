package br.com.testebackend.miniautorizador.dtos;

public class CartaoDTO {

	private String numeroCartao;

	private String senha;

	public CartaoDTO(String numeroCartao, String senha) {
		super();
		this.numeroCartao = numeroCartao;
		this.senha = senha;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
