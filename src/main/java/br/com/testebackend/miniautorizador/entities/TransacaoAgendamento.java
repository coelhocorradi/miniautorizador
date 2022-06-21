package br.com.testebackend.miniautorizador.entities;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.testebackend.miniautorizador.enums.EStatusTransacaoAgendamento;

@Document("transacao_agendamento")
public class TransacaoAgendamento {

	@Id
	private UUID idTransacaoAgendamento;

	private UUID idTransacao;

	private Date dataAgendada;

	private Date dataConcluido;

	private String motivo;

	private EStatusTransacaoAgendamento status;

	public TransacaoAgendamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransacaoAgendamento(UUID idTransacaoAgendamento, UUID idTransacao, Date dataAgendada, String motivo) {
		super();
		this.idTransacaoAgendamento = idTransacaoAgendamento;
		this.idTransacao = idTransacao;
		this.dataAgendada = dataAgendada;
		this.motivo = motivo;
	}

	public TransacaoAgendamento(UUID idTransacaoAgendamento, UUID idTransacao, Date dataAgendada, Date dataConcluido,
			String motivo, EStatusTransacaoAgendamento status) {
		super();
		this.idTransacaoAgendamento = idTransacaoAgendamento;
		this.idTransacao = idTransacao;
		this.dataAgendada = dataAgendada;
		this.dataConcluido = dataConcluido;
		this.motivo = motivo;
		this.status = status;
	}

	public UUID getIdTransacaoAgendamento() {
		return idTransacaoAgendamento;
	}

	public void setIdTransacaoAgendamento(UUID idTransacaoAgendamento) {
		this.idTransacaoAgendamento = idTransacaoAgendamento;
	}

	public UUID getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(UUID idTransacao) {
		this.idTransacao = idTransacao;
	}

	public Date getDataAgendada() {
		return dataAgendada;
	}

	public void setDataAgendada(Date dataAgendada) {
		this.dataAgendada = dataAgendada;
	}

	public Date getDataConcluido() {
		return dataConcluido;
	}

	public void setDataConcluido(Date dataConcluido) {
		this.dataConcluido = dataConcluido;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public EStatusTransacaoAgendamento getStatus() {
		return status;
	}

	public void setStatus(EStatusTransacaoAgendamento status) {
		this.status = status;
	}
}
