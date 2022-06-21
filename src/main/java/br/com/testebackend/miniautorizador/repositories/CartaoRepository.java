package br.com.testebackend.miniautorizador.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.testebackend.miniautorizador.entities.Cartao;

@Repository
public interface CartaoRepository extends MongoRepository<Cartao, String> {

	@Query("{numeroCartao:'?0'}")
	Cartao acharPeloNumeroCartao(String numeroCartao);

	@Query("{ativo:true}")
	List<Cartao> listarAtivos();
}
