package br.com.testebackend.miniautorizador.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.testebackend.miniautorizador.entities.CartaoTransacao;

public interface CartaoTransacaoRepository extends MongoRepository<CartaoTransacao, UUID> {

	@Query(value = "{idTransacao:'?0'}")
	CartaoTransacao acharPorIdTransacao(UUID idTransacao);

	@Query(value = "{numeroCartao:'?0'}")
	List<CartaoTransacao> acharTransacoesPorCartao(String numeroCartao);
}
