package br.com.testebackend.miniautorizador.entities;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.testebackend.miniautorizador.enums.EStatusTransacao;

@Document("cartao_transacao")
public class CartaoTransacao {

	@Id
	private UUID idTransacao;

	private String numeroCartao;

	private Date dataTransacao;

	private Double valorTransacao;

	private EStatusTransacao statusTransacao;

	private UUID idTransacaoCanceladoOuExternado;

	public CartaoTransacao() {
		super();
	}

	public CartaoTransacao(UUID idTransacao, String numeroCartao, Date dataTransacao, Double valorTransacao,
			EStatusTransacao statusTransacao) {
		super();
		this.idTransacao = idTransacao;
		this.numeroCartao = numeroCartao;
		this.dataTransacao = dataTransacao;
		this.valorTransacao = valorTransacao;
		this.statusTransacao = statusTransacao;
		this.idTransacaoCanceladoOuExternado = null;
	}

	public CartaoTransacao(UUID idTransacao, String numeroCartao, Date dataTransacao, Double valorTransacao,
			EStatusTransacao statusTransacao, UUID idTransacaoCanceladoOuExternado) {
		super();
		this.idTransacao = idTransacao;
		this.numeroCartao = numeroCartao;
		this.dataTransacao = dataTransacao;
		this.valorTransacao = valorTransacao;
		this.statusTransacao = statusTransacao;
		this.idTransacaoCanceladoOuExternado = idTransacaoCanceladoOuExternado;
	}

	public UUID getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(UUID idTransacao) {
		this.idTransacao = idTransacao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public Double getValorTransacao() {
		return valorTransacao;
	}

	public void setValorTransacao(Double valorTransacao) {
		this.valorTransacao = valorTransacao;
	}

	public EStatusTransacao getStatusTransacao() {
		return statusTransacao;
	}

	public void setStatusTransacao(EStatusTransacao statusTransacao) {
		this.statusTransacao = statusTransacao;
	}

	public UUID getIdTransacaoCanceladoOuExternado() {
		return idTransacaoCanceladoOuExternado;
	}

	public void setIdTransacaoCanceladoOuExternado(UUID idTransacaoCanceladoOuExternado) {
		this.idTransacaoCanceladoOuExternado = idTransacaoCanceladoOuExternado;
	}

}
