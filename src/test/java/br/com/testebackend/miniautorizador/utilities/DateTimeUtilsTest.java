package br.com.testebackend.miniautorizador.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class DateTimeUtilsTest {

	@Test
	public void betweenTest() {
		System.out.println("between");
		Date de = null, ate = null, data = null;
		Boolean result = null;

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// verificar se está entre os valores
			data = format.parse("2022-06-13 10:00:00");
			de = format.parse("2022-06-13 09:00:00");
			ate = format.parse("2022-06-13 11:00:00");
			result = DateTimeUtils.between(de, ate, data);
			Assertions.assertTrue(result);

			// verificar se está entre os valoes ou igual a limit superior
			data = format.parse("2022-06-13 11:00:00");
			result = DateTimeUtils.between(de, ate, data);
			Assertions.assertTrue(result);

			// verificar se está entre os valores ou igual a limit inferior
			data = format.parse("2022-06-13 09:00:00");
			result = DateTimeUtils.between(de, ate, data);
			Assertions.assertTrue(result);

			// verificar se fora dos valores
			data = format.parse("2022-06-13 08:00:00");
			result = DateTimeUtils.between(de, ate, data);
			Assertions.assertFalse(result);

			data = format.parse("2022-06-13 12:00:00");
			result = DateTimeUtils.between(de, ate, data);
			Assertions.assertFalse(result);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@BeforeAll
	public static void before() {
		System.out.println("iniciando o teste de DateTimeUtils");
	}

	@AfterAll
	public static void after() {
		System.out.println("finalizando o teste de DateTimeUtils");
	}
}
