package br.com.testebackend.miniautorizador.business;

import static org.mockito.ArgumentMatchers.nullable;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.testebackend.miniautorizador.enums.EStatusTransacao;

public class CartaoTransacaoTest {

	@Test
	public void executarTransacaoTest() {
		CartaoTransacaoBusiness business = new CartaoTransacaoBusiness();
		String numeroCartao = null,
				senha = null;
		Double valor = null;
		EStatusTransacao status = EStatusTransacao.UNKNOW;
		
		numeroCartao = "1234567890123456";
		senha = "1234";
		valor = 50.00;
		status = business.executarTransacao(numeroCartao, senha, valor);
		Assertions.assertEquals(status, EStatusTransacao.AUTORIZADO);
		
		numeroCartao = "1234560001223456";
		senha = "1234";
		valor = 50.00;
		status = business.executarTransacao(numeroCartao, senha, valor);
		Assertions.assertEquals(status, EStatusTransacao.CANCELADO_CARTAO_INEXISTENTE);
		
		numeroCartao = "1234567890123456";
		senha = "123456";
		valor = 50.00;
		status = business.executarTransacao(numeroCartao, senha, valor);
		Assertions.assertEquals(status, EStatusTransacao.CANCELADO_SENHA_INVALIDA);
		
		numeroCartao = "1234567890123456";
		senha = "1234";
		valor = 500.00;
		status = business.executarTransacao(numeroCartao, senha, valor);
		Assertions.assertEquals(status, EStatusTransacao.CANCELADO_SALDO_INSUFICIENTE);
	}
}
