package br.com.testebackend.miniautorizador.business;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.testebackend.miniautorizador.entities.Cartao;
import br.com.testebackend.miniautorizador.entities.CartaoTransacao;
import br.com.testebackend.miniautorizador.enums.EStatusTransacao;
import br.com.testebackend.miniautorizador.enums.ETipoTransacao;
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
	
	/**
	 * executa uma transacao no cartão. verifica número do cartão e senha se são válidos e valor da transação se pode ser executada.
	 * usado para transações de compra com o cartão
	 * @param numeroCartao
	 * @param senha
	 * @param valor
	 * @return
	 */
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
			CartaoTransacao transacao = cartaoTransacaoRepository.save(new CartaoTransacao(
					cartao.getIdCartao(), new Date(), valor, EStatusTransacao.AUTORIZADO));
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

	public EStatusTransacao executarCancelamentoOuExtorno(String numeroCartao, String senha, String idTransacao) {
		EStatusTransacao result = null;
		try {
			numeroCartao = Cartao.validaNumeroCartao(numeroCartao);
			Cartao cartao = cartaoRepository.acharPeloNumeroCartao(numeroCartao);
			if (cartao == null)
				throw new TransacaoException("Cartão não encontrado!", EStatusTransacao.CANCELADO_CARTAO_INEXISTENTE);
			senha = Cartao.validaSenha(senha);
			if (!cartao.getSenha().equals(senha))
				throw new TransacaoException("Senha inválida!", EStatusTransacao.CANCELADO_SENHA_INVALIDA);
			CartaoTransacao transacao = cartaoTransacaoRepository.acharPorIdTransacao(UUID.fromString(idTransacao));
			if (transacao == null)
				throw new TransacaoException("Transação não encontrada!",EStatusTransacao.INEXISTENTE);
			if (transacao.getIdCartao().compareTo(cartao.getIdCartao())==0)
				throw new TransacaoException("Número do cartão na transação e número do cartão informado não conferem!", EStatusTransacao.CANCELADO_TRANSACAO_INVALIDA);
			switch (transacao.getStatusTransacao()) {
				// autorizado faz referência a compras e portanto o valor deve ser extornado.
				case AUTORIZADO: {		
					CartaoTransacao extorno = new CartaoTransacao();
					extorno.setIdCartao(transacao.getIdCartao());
					extorno.setIdTransacaoCanceladoOuExternado(transacao.getIdTransacao());
					extorno.setStatusTransacao(EStatusTransacao.EXTORNADO);
					extorno.setValorTransacao(transacao.getValorTransacao());
					extorno.setDataTransacao(new Date());					
					extorno = cartaoTransacaoRepository.save(extorno);
					
					Double saldo = cartao.getSaldo();
					saldo += extorno.getValorTransacao();
					cartao.setSaldo(saldo);
					cartaoRepository.save(cartao);
					
					result = EStatusTransacao.EXTORNADO;
				} break;
				// um depósito efetuado de forma incorreta por um operador pode ser desfeito, e esse deve ser marcado como cancelado.
				// observação: se o saldo não for suficiente para fazer o cancelamento, então este deverá ser prorrogado para uma próxima transação de depósito ou recarga.
				// o cancelamento de um depósito ou recarga deve ser comunicado ao cliente antes de efetuado, para que ele tenha ciência do acontecido e do motivo do cancelamento.
				case DEPOSITADO: {
					CartaoTransacao cancelado = new CartaoTransacao();
					cancelado.setIdCartao(transacao.getIdCartao());
					cancelado.setIdTransacaoCanceladoOuExternado(transacao.getIdTransacao());
					cancelado.setStatusTransacao(EStatusTransacao.EXTORNADO);
					cancelado.setValorTransacao(transacao.getValorTransacao());
					cancelado.setDataTransacao(new Date());					
					cancelado = cartaoTransacaoRepository.save(cancelado);
					
					//Double saldo = cartao.getSaldo();
					//saldo -= cancelado.getValorTransacao();
					//cartao.setSaldo(saldo);
					//cartaoRepository.save(cartao);
					
					result = EStatusTransacao.CANCELADO;
				} break;
				default: {
					
				} break;
			}
		}catch (Exception e) {
			e.printStackTrace();
			result = EStatusTransacao.UNKNOW;
		}
		return result;
	}
	
	/**
	 * executa uma transacao no cartao. verifica se número do cartão e senha são validos antes da operação. verifica se tipo da transação e valor são válidos antes da operação. poderá ser usado para mais de um teipo de transação.
	 * @param numeroCartao
	 * @param senhaCartao
	 * @param valor
	 * @param tipo
	 * @return
	 */
	public EStatusTransacao executarTransacao(String numeroCartao, String senha, Double valor, ETipoTransacao tipo) {
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
			CartaoTransacao transacao = cartaoTransacaoRepository.save(new CartaoTransacao(
					cartao.getIdCartao(), new Date(), valor, EStatusTransacao.AUTORIZADO));
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
