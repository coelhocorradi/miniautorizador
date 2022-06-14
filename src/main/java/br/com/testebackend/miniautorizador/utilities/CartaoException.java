package br.com.testebackend.miniautorizador.utilities;

import br.com.testebackend.miniautorizador.enums.EStatusCartao;

public class CartaoException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -791824428451116366L;

	private EStatusCartao status;

	public CartaoException(String message, EStatusCartao status) {
		super(message);
		this.status = status;
	}

	public EStatusCartao getStatusCartao() {
		return this.status;
	}
}
