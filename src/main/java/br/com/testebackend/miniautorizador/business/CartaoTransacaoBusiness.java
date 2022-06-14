package br.com.testebackend.miniautorizador.business;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.testebackend.miniautorizador.entities.Cartao;
import br.com.testebackend.miniautorizador.entities.CartaoTransacao;
import br.com.testebackend.miniautorizador.enums.EStatusTransacao;
import br.com.testebackend.miniautorizador.repositories.CartaoRepository;
import br.com.testebackend.miniautorizador.repositories.CartaoTransacaoRepository;
import br.com.testebackend.miniautorizador.utilities.CartaoException;
import br.com.testebackend.miniautorizador.utilities.DateTimeUtils;
import br.com.testebackend.miniautorizador.utilities.TransacaoException;

@Component
public class CartaoTransacaoBusiness {

	@Autowired
	private CartaoTransacaoRepository cartaoTransacaoRepository;

	@Autowired
	private CartaoRepository cartaoRepository;

	public CartaoTransacao acharTransacaoPorIdTransacao(UUID idTransacao) {
		return cartaoTransacaoRepository.acharPorIdTransacao(idTransacao);
	}

	public List<CartaoTransacao> acharTransacoesPorNumeroCartao(String numeroCartao) {
		return cartaoTransacaoRepository.acharTransacoesPorCartao(numeroCartao);
	}

	public List<CartaoTransacao> acharTransacoesPorNumeroCartaoEDatas(String numeroCartao, Date deDataInicio,
			Date ateDataFim, Date minhaData) {
		final Date de = deDataInicio, ate = ateDataFim != null ? ateDataFim : new Date();
		return cartaoTransacaoRepository.acharTransacoesPorCartao(numeroCartao).stream()
				.filter(c -> DateTimeUtils.between(de, ate, minhaData)).toList();
	}

	public EStatusTransacao executarTransacao(String numeroCartao, String senha, Double valor) {
		EStatusTransacao result = EStatusTransacao.UNKNOW;
		try {
			numeroCartao = Cartao.validaNumeroCartao(numeroCartao);
			Cartao cartao = cartaoRepository.acharPeloNumeroCartao(numeroCartao);
			if (cartao == null)
				throw new TransacaoException("Cartão não encontrado!", EStatusTransacao.CANCELADO_CARTAO_INEXISTENTE);
			senha = Cartao.validaSenha(senha);
			if (!cartao.getSenha().equals(senha))
				throw new TransacaoException("Senha do cartão não confere!", EStatusTransacao.CANCELADO_SENHA_INVALIDA);
			if (cartao.getSaldo() < valor)
				throw new TransacaoException("Saldo insuficiente para executar a transacao!",
						EStatusTransacao.CANCELADO_SALDO_INSUFICIENTE);
			Double saldo = cartao.getSaldo();
			saldo -= valor;
			cartao.setSaldo(saldo);
			CartaoTransacao transacao = cartaoTransacaoRepository.save(new CartaoTransacao(UUID.randomUUID(),
					numeroCartao, new Date(), valor, EStatusTransacao.AUTORIZADO));
			if (transacao == null)
				throw new TransacaoException("Não foi possível salvar a transação!", EStatusTransacao.CANCELADO);
			cartaoRepository.save(cartao);
			result = EStatusTransacao.AUTORIZADO;
		} catch (CartaoException e) {
			e.printStackTrace();
			result = EStatusTransacao.CANCELADO;
		} catch (TransacaoException e) {
			e.printStackTrace();
			result = e.getStatusTransacao();
		} catch (Exception e) {
			e.printStackTrace();
			result = EStatusTransacao.UNKNOW;
		}
		return result;
	}
}
