package br.com.testebackend.miniautorizador.utilities;

import java.util.Date;

public abstract class DateTimeUtils {

	public static boolean between(Date de, Date ate, Date data) {
		boolean result = false;
		try {
			if (de == null || ate == null || data == null)
				throw new Exception("As datas não podem ser nulas!");
			if (de.compareTo(ate) > 0)
				throw new Exception("A data início não pode ser maior que a data final!");
			result = de.compareTo(data) <= 0 && ate.compareTo(data) >= 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
