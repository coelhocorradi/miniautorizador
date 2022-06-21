package br.com.testebackend.miniautorizador.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.testebackend.miniautorizador.enums.EStatusTransacao;

public class CartaoTransacaoTest {

	static CartaoTransacaoBusiness business = null;
	
	@BeforeAll
	static void beforeAll() {
		business = new CartaoTransacaoBusiness();
	}
	
	static void afterAll() {
		business = null;
	}
	
	@Test
	public void executarTransacaoTest() {		
		String numeroCartao = null,
				senha = null;
		Double valor = null;
		EStatusTransacao status = EStatusTransacao.UNKNOW;
		
		//cartao e senha ok, valor pode ser deduzido
		numeroCartao = "1234567890123456";
		senha = "1234";
		valor = 50.00;
		status = business.executarTransacao(numeroCartao, senha, valor);
		Assertions.assertEquals(status, EStatusTransacao.AUTORIZADO);
		
		//cartao não existe
		numeroCartao = "1234560001223456";
		senha = "1234";
		valor = 50.00;
		status = business.executarTransacao(numeroCartao, senha, valor);
		Assertions.assertEquals(status, EStatusTransacao.CANCELADO_CARTAO_INEXISTENTE);
		
		//senha inválida
		numeroCartao = "1234567890123456";
		senha = "123456";
		valor = 50.00;
		status = business.executarTransacao(numeroCartao, senha, valor);
		Assertions.assertEquals(status, EStatusTransacao.CANCELADO_SENHA_INVALIDA);
		
		//saldo insuficiente
		numeroCartao = "1234567890123456";
		senha = "1234";
		valor = 500.00;
		status = business.executarTransacao(numeroCartao, senha, valor);
		Assertions.assertEquals(status, EStatusTransacao.CANCELADO_SALDO_INSUFICIENTE);
	}
}
