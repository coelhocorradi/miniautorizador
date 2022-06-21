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

	private UUID idCartao;

	private Date dataTransacao;

	private Double valorTransacao;

	private EStatusTransacao statusTransacao;

	private UUID idTransacaoCanceladoOuExternado;

	public CartaoTransacao() {
		super();
		this.idTransacao = UUID.randomUUID();
	}

	public CartaoTransacao(UUID idCartao, Date dataTransacao, Double valorTransacao,
			EStatusTransacao statusTransacao) {
		super();
		this.idTransacao = UUID.randomUUID();
		this.idCartao = idCartao;
		this.dataTransacao = dataTransacao;
		this.valorTransacao = valorTransacao;
		this.statusTransacao = statusTransacao;
		this.idTransacaoCanceladoOuExternado = null;
	}

	public CartaoTransacao(UUID idCartao, Date dataTransacao, Double valorTransacao,
			EStatusTransacao statusTransacao, UUID idTransacaoCanceladoOuExternado) {
		super();
		this.idTransacao = UUID.randomUUID();
		this.idCartao = idCartao;
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

	public UUID getIdCartao() {
		return idCartao;
	}

	public void setIdCartao(UUID idCartao) {
		this.idCartao = idCartao;
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
