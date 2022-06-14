package br.com.testebackend.miniautorizador.entities;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CartaoTest {

	@Test
	public void validaNumeroCartaoTest() {
		String numeroCartao = null, numeroGerado = null, numeroEsperado = "1234567890123456", mensagem = null,
				mensagemDefault = "Número cartão : ";

		try {
			// número do cartão válido
			numeroCartao = " 1234 5678 9012 3456  ";
			numeroGerado = Cartao.validaNumeroCartao(numeroCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			numeroGerado = numeroCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + numeroGerado);
		Assertions.assertEquals(numeroGerado, numeroEsperado);

		try {
			// número do cartão só pode conter digitios
			numeroGerado = null;
			numeroCartao = "123456789012as56";
			numeroGerado = Cartao.validaNumeroCartao(numeroCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			numeroGerado = numeroCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + numeroGerado);
		Assertions.assertNotEquals(numeroGerado, numeroEsperado);

		try {
			// número do cartão menor que o padrão
			numeroGerado = null;
			numeroCartao = "123456789012345";
			numeroGerado = Cartao.validaNumeroCartao(numeroCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			numeroGerado = numeroCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + numeroGerado);
		Assertions.assertNotEquals(numeroGerado, numeroEsperado);

		try {
			// número do cartão maior que o padrão
			numeroGerado = null;
			numeroCartao = "12345678901234567";
			numeroGerado = Cartao.validaNumeroCartao(numeroCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			numeroGerado = numeroCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			//e.printStackTrace();
		}
		System.out.println(mensagem + numeroGerado);
		Assertions.assertNotEquals(numeroGerado, numeroEsperado);

		try {
			// número do cartão não pode ser vazio
			numeroGerado = null;
			numeroCartao = "";
			numeroGerado = Cartao.validaNumeroCartao(numeroCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			numeroGerado = numeroCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + numeroGerado);
		Assertions.assertNotEquals(numeroGerado, numeroEsperado);

		try {
			// número do cartão não pode ser nulo
			numeroGerado = null;
			numeroCartao = null;
			numeroGerado = Cartao.validaNumeroCartao(numeroCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			numeroGerado = numeroCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + numeroGerado);
		Assertions.assertNull(numeroGerado);
	}

	@Test
	public void validaSenhaCartaoTest() {
		String senhaCartao = null, senhaGerada = null, senhaEsperada = null, mensagem = null,
				mensagemDefault = "Senha cartão : ";

		try {
			// senha correta, tamanho mínimo
			senhaEsperada = "1234";
			senhaCartao = " 1234 ";
			senhaGerada = Cartao.validaSenha(senhaCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			senhaGerada = senhaCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + senhaGerada);
		Assertions.assertEquals(senhaGerada, senhaEsperada);

		try {
			// senha correta, tamanho máximo
			senhaEsperada = "123456";
			senhaCartao = " 123456 ";
			senhaGerada = Cartao.validaSenha(senhaCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			senhaGerada = senhaCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + senhaGerada);
		Assertions.assertEquals(senhaGerada, senhaEsperada);

		try {
			// senha com caracteres inválidos
			senhaGerada = null;
			senhaCartao = "123df";
			senhaGerada = Cartao.validaSenha(senhaCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			senhaGerada = senhaCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + senhaGerada);
		Assertions.assertNotEquals(senhaGerada, senhaEsperada);

		try {
			// senha menor que o esperado
			senhaGerada = null;
			senhaCartao = "123";
			senhaGerada = Cartao.validaSenha(senhaCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			senhaGerada = senhaCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + senhaGerada);
		Assertions.assertNotEquals(senhaGerada, senhaEsperada);

		try {
			// senha maior que o esperado
			senhaGerada = null;
			senhaCartao = "1234567";
			senhaGerada = Cartao.validaSenha(senhaCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			senhaGerada = senhaCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + senhaGerada);
		Assertions.assertNotEquals(senhaGerada, senhaEsperada);

		try {
			// senha vazia
			senhaGerada = null;
			senhaCartao = "";
			senhaGerada = Cartao.validaSenha(senhaCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			senhaGerada = senhaCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + senhaGerada);
		Assertions.assertNotEquals(senhaGerada, senhaEsperada);

		try {
			// senha nula
			senhaGerada = null;
			senhaCartao = null;
			senhaGerada = Cartao.validaSenha(senhaCartao);
			mensagem = mensagemDefault;
		} catch (Exception e) {
			senhaGerada = senhaCartao;
			mensagem = e.getMessage() + ":" + mensagemDefault;
			// e.printStackTrace();
		}
		System.out.println(mensagem + senhaGerada);
		Assertions.assertNull(senhaGerada);
	}

	@BeforeAll
	public static void before() {
		System.out.println("Iniciando teste de validar cartão!");
	}

	@AfterAll
	public static void after() {
		System.out.println("Terminando teste de validar cartão!");
	}
}
