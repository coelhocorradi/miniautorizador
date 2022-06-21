package br.com.testebackend.miniautorizador.enums;

public enum EStatusTransacao {

	CRIADO(null),

	DEPOSITADO(null),

	AUTORIZADO(null),

	CANCELADO(null),

	CANCELADO_SENHA_INVALIDA(EStatusCartao.SENHA_INVALIDA),

	CANCELADO_SALDO_INSUFICIENTE(EStatusCartao.SALDO_INSUFICIENTE),

	CANCELADO_CARTAO_INEXISTENTE(EStatusCartao.CARTAO_INEXISTENTE),
	
	CANCELADO_TRANSACAO_INVALIDA(null),

	EXTORNADO(null),
	
	INEXISTENTE(null),

	UNKNOW(null);

	private EStatusCartao status;

	private EStatusTransacao(EStatusCartao status) {
		this.status = status;
	}

	public EStatusCartao getStatusCartao() {
		return this.status;
	}
}
