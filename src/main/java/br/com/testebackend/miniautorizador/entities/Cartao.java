package br.com.testebackend.miniautorizador.entities;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cartao")
public class Cartao {

	@Id
	private UUID idCartao;

	private String numeroCartao;

	private String senha;

	private Double saldo;

	private boolean ativo;

	public Cartao() {
		super();
		this.idCartao = UUID.randomUUID();
	}

	public Cartao(String numeroCartao, String senha, Double saldo, boolean ativo) {
		super();
		this.idCartao = UUID.randomUUID();
		this.numeroCartao = numeroCartao;
		this.senha = senha;
		this.saldo = saldo;
		this.ativo = ativo;
	}

	public UUID getIdCartao() {
		return idCartao;
	}

	public void setIdCartao(UUID idCartao) {
		this.idCartao = idCartao;
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

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public static String validaNumeroCartao(String numeroCartao) throws Exception {
		if (numeroCartao == null)
			throw new Exception("Número do cartão não pode ser nulo");
		numeroCartao = numeroCartao.trim().replaceAll("\r|\t|\s|\n", "");
		if (numeroCartao.isEmpty())
			throw new Exception("Número do cartão não pode ser vazio");
		if (numeroCartao.length() != 16)
			throw new Exception("Número do cartão deve ser composto de 16 dígitos!");
		if (!numeroCartao.matches("[0-9]{16}"))
			throw new Exception("Número do cartão tem de ser formado apenas por dígitos!");
		return numeroCartao;
	}

	public static String validaSenha(String senha) throws Exception {
		if (senha == null)
			throw new Exception("A senha do cartão não pode ser nula!");
		senha = senha.trim().replaceAll("\r|\t|\s|\n", "");
		if (senha.isEmpty())
			throw new Exception("A senha do cartão não pode ser vazia!");
		if (senha.length() < 4 || senha.length() > 6)
			throw new Exception("A senha do cartão deve ter de 4 a 6 dígtos!");
		if (!senha.matches("[0-9]{4,6}"))
			throw new Exception("A senha do cartão tem de ser formada apenas por dígitos!");
		return senha;
	}
}
