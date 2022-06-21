package br.com.testebackend.miniautorizador.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.testebackend.miniautorizador.entities.Cartao;
import br.com.testebackend.miniautorizador.entities.CartaoTransacao;
import br.com.testebackend.miniautorizador.enums.EStatusCartao;
import br.com.testebackend.miniautorizador.enums.EStatusTransacao;
import br.com.testebackend.miniautorizador.repositories.CartaoRepository;
import br.com.testebackend.miniautorizador.repositories.CartaoTransacaoRepository;
import br.com.testebackend.miniautorizador.utilities.CartaoException;

@Component
public class CartaoBusiness {

	@Autowired
	private CartaoRepository repository;

	@Autowired
	private CartaoTransacaoRepository CartaoTransacaoRepository;

	public Cartao acharPorNumeroCartao(String numeroCartao) {
		return repository.acharPeloNumeroCartao(numeroCartao);
	}

	public Boolean verificarSeAtivo(String numeroCartao) {
		Cartao cartao = repository.acharPeloNumeroCartao(numeroCartao);
		return cartao != null && cartao.isAtivo();
	}

	/**
	 * criar um cartao, verifica número do cartão e senha se válidos antes de salvar
	 * 
	 * @param numeroCartao
	 * @param senha
	 * @return
	 */
	public EStatusCartao criarCartao(String numeroCartao, String senha) {
		EStatusCartao result = EStatusCartao.UNKNOW;
		try {
			numeroCartao = Cartao.validaNumeroCartao(numeroCartao);
			Cartao cartao = repository.acharPeloNumeroCartao(numeroCartao);
			if (cartao != null)
				throw new CartaoException("Um cartão com esse número já existe!", EStatusCartao.CARTAO_EXISTE);
			senha = Cartao.validaSenha(senha);
			cartao = repository.save(new Cartao(numeroCartao, senha, null, true));
			if (cartao == null)
				throw new CartaoException("Cartão não pode ser criado!", EStatusCartao.NAO_PODE_SER_CRIADO);
			CartaoTransacao transacao = CartaoTransacaoRepository.save(new CartaoTransacao(cartao.getIdCartao(), new Date(), 500.00, EStatusTransacao.CRIADO));
			if (transacao == null) {
				repository.delete(cartao);
				throw new CartaoException("Erro na criação do cartão!", EStatusCartao.NAO_PODE_SER_CRIADO);
			}
			result = EStatusCartao.CRIADO;
		} catch (CartaoException e) {
			e.printStackTrace();
			result = e.getStatusCartao();
		} catch (Exception e) {
			e.printStackTrace();
			result = EStatusCartao.UNKNOW;
		}
		return result;
	}

	/**
	 * trocar senha do cartao, obrigatório senha antiga e nova senha para
	 * conferência. verifica número do cartão, senha antiga e senha nova se válidos
	 * antes de salvar.
	 * 
	 * @param numeroCartao
	 * @param senhaAntiga
	 * @param senhaNova
	 * @return
	 */
	public EStatusCartao trocarSenhaCartao(String numeroCartao, String senhaAntiga, String senhaNova) {
		EStatusCartao result = EStatusCartao.UNKNOW;
		try {
			numeroCartao = Cartao.validaNumeroCartao(numeroCartao);
			Cartao cartao = repository.acharPeloNumeroCartao(numeroCartao);
			senhaAntiga = Cartao.validaSenha(senhaAntiga);
			if (!cartao.getSenha().equals(senhaAntiga))
				throw new CartaoException("A senha informada não coincide com a registrada!",
						EStatusCartao.SENHA_INVALIDA);
			senhaNova = Cartao.validaSenha(senhaNova);
			if (!cartao.getSenha().equals(senhaNova))
				throw new CartaoException("A nova senha informada é a mesma senha anterior!",
						EStatusCartao.SENHA_NAO_ALTERADA);
			cartao.setSenha(senhaNova);
			cartao = repository.save(cartao);
			if (cartao == null)
				throw new CartaoException("A senha do cartão não pode ser alterada!", EStatusCartao.SENHA_INVALIDA);
			result = EStatusCartao.SENHA_ALTERADA;
		} catch (CartaoException e) {
			e.printStackTrace();
			result = e.getStatusCartao();
		} catch (Exception e) {
			e.printStackTrace();
			result = EStatusCartao.UNKNOW;
		}
		return result;
	}

	/**
	 * Recupera saldo do cartão sem uso de senha. verifica o número do cartão se
	 * válido antes de recuperar o saldo.
	 * 
	 * @param numeroCartao
	 * @return
	 */
	public Double recuperarSaldo(String numeroCartao) {
		Double result = null;
		try {
			numeroCartao = Cartao.validaNumeroCartao(numeroCartao);
			Cartao cartao = repository.acharPeloNumeroCartao(numeroCartao);
			if (cartao == null)
				throw new CartaoException("O cartao informado não foi encontrado!", EStatusCartao.CARTAO_INEXISTENTE);
			result = cartao.getSaldo();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	/**
	 * Recupera saldo do cartão, senha é obrigatória verifca número e senha do
	 * cartão se válido antes de recuperar o saldo.
	 * 
	 * @param numeroCartao
	 * @param senha
	 * @return
	 */
	public Double recuperarSaldo(String numeroCartao, String senha) {
		Double result = null;
		try {
			numeroCartao = Cartao.validaNumeroCartao(numeroCartao);
			Cartao cartao = repository.acharPeloNumeroCartao(numeroCartao);
			if (cartao == null)
				throw new CartaoException("O cartao informado não foi encontrado!", EStatusCartao.CARTAO_INEXISTENTE);
			senha = Cartao.validaSenha(senha);
			if (!cartao.getSenha().equals(senha))
				throw new CartaoException("A senha informada não coincide com a registrada!",
						EStatusCartao.SENHA_INVALIDA);
			result = cartao.getSaldo();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

}
