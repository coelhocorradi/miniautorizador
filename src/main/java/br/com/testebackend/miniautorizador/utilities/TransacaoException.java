package br.com.testebackend.miniautorizador.utilities;

import br.com.testebackend.miniautorizador.enums.EStatusTransacao;

public class TransacaoException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -791824428451116366L;

	private EStatusTransacao status;

	public TransacaoException(String message, EStatusTransacao status) {
		super(message);
		this.status = status;
	}

	public EStatusTransacao getStatusTransacao() {
		return this.status;
	}
}
