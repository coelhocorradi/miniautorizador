package br.com.testebackend.miniautorizador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.testebackend.miniautorizador.business.CartaoTransacaoBusiness;
import br.com.testebackend.miniautorizador.dtos.TransacaoDTO;
import br.com.testebackend.miniautorizador.enums.EStatusTransacao;

@RestController
@RequestMapping("")
public class CartaoTransacaoController {

	@Autowired
	public CartaoTransacaoBusiness cartaoTransacaoBusiness;

	@RequestMapping(value = { "/transacoes" }, method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> executarTransacao(@RequestBody TransacaoDTO dto) {
		ResponseEntity<String> result = null;
		try {
			String numeroCartao = dto.getNumeroCartao();
			String senha = dto.getSenhaCartao();
			Double valor = dto.getValor();
			EStatusTransacao status = cartaoTransacaoBusiness.executarTransacao(numeroCartao, senha, valor);
			switch (status) {
				case AUTORIZADO: {
					result = new ResponseEntity<String>("OK", HttpStatus.CREATED);
				}
					break;
				case CANCELADO_CARTAO_INEXISTENTE:
				case CANCELADO_SALDO_INSUFICIENTE:
				case CANCELADO_SENHA_INVALIDA: {
					result = new ResponseEntity<String>(status.getStatusCartao().name(),
							HttpStatus.UNPROCESSABLE_ENTITY);
				}
					break;
				default: {
					result = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
					break;
			}
		} catch (Exception e) {
			result = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
}
