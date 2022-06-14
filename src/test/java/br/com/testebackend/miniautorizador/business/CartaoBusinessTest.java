package br.com.testebackend.miniautorizador.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.testebackend.miniautorizador.enums.EStatusCartao;

public class CartaoBusinessTest {
	
	@Test
	public void criarCartaoTest() {
		CartaoBusiness business = new CartaoBusiness();
		EStatusCartao status = null;
		String numeroCartao = null,
				senha = null;
		
		numeroCartao = "1234567890123456";
		senha = "1234";
		status = business.criarCartao(numeroCartao, senha);
		Assertions.assertEquals(status, EStatusCartao.CRIADO);
		
		numeroCartao = "1234567890123456";
		senha = "1234";
		status = business.criarCartao(numeroCartao, senha);
		Assertions.assertEquals(status, EStatusCartao.CARTAO_EXISTE);
		
		numeroCartao = "123456789012345";
		senha = "1234";
		status = business.criarCartao(numeroCartao, senha);
		Assertions.assertEquals(status, EStatusCartao.NAO_PODE_SER_CRIADO);
		
		numeroCartao = "12345678901234567";
		senha = "1234";
		status = business.criarCartao(numeroCartao, senha);
		Assertions.assertEquals(status, EStatusCartao.NAO_PODE_SER_CRIADO);
		
		numeroCartao = "";
		senha = "1234";
		status = business.criarCartao(numeroCartao, senha);
		Assertions.assertEquals(status, EStatusCartao.NAO_PODE_SER_CRIADO);
	}
	
	@Test
	public void cartaoSaldoTest() {
		CartaoBusiness business = new CartaoBusiness();
		String numeroCartao = null;
		Double saldo = null;
		
		// recupera o saldo do cartão
		numeroCartao = "1234567890123456";
		saldo = business.recuperarSaldo(numeroCartao, null);
		Assertions.assertNotNull(saldo);
		
		// não recupera o saldo do cartao
		saldo = null;
		numeroCartao = "1234567890987654";
		saldo = business.recuperarSaldo(numeroCartao, null);
		Assertions.assertNull(saldo);
	}
}
