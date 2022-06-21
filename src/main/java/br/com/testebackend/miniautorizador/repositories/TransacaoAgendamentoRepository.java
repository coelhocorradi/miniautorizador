package br.com.testebackend.miniautorizador.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.testebackend.miniautorizador.entities.TransacaoAgendamento;
import br.com.testebackend.miniautorizador.enums.EStatusTransacaoAgendamento;

@Repository
public interface TransacaoAgendamentoRepository extends MongoRepository<TransacaoAgendamento, UUID> {

	@Query(value = "{idTransacao:'?0'}")
	TransacaoAgendamento acharPorIdTransacao(UUID idTransacao);
	
	@Query(value = "{status:'?0'}")
	List<TransacaoAgendamento> acharPorStatusTransacaoAgendamento(EStatusTransacaoAgendamento status);
}
