package br.com.testebackend.miniautorizador.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.testebackend.miniautorizador.business.CartaoBusiness;
import br.com.testebackend.miniautorizador.dtos.CartaoDTO;
import br.com.testebackend.miniautorizador.entities.Cartao;
import br.com.testebackend.miniautorizador.enums.EStatusCartao;

@RestController
@RequestMapping("")
public class CartaoController {

	@Autowired
	private CartaoBusiness cartaoBusiness;

	@RequestMapping(value = { "/cartoes" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CartaoDTO> criarCartao(@RequestBody CartaoDTO dto) {
		ResponseEntity<CartaoDTO> response = null;
		try {
			String numeroCartao = dto.getNumeroCartao();
			String senha = dto.getSenha();
			EStatusCartao result = cartaoBusiness.criarCartao(numeroCartao, senha);
			switch (result) {
				case CRIADO: {
					response = new ResponseEntity<CartaoDTO>(new CartaoDTO(numeroCartao, senha), HttpStatus.CREATED);
				}
					break;
				case CARTAO_EXISTE: {
					response = new ResponseEntity<CartaoDTO>(new CartaoDTO(numeroCartao, senha),
							HttpStatus.UNPROCESSABLE_ENTITY);
				}
					break;
				default: {
					response = new ResponseEntity<CartaoDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
					break;
			}
		} catch (Exception e) {
			response = new ResponseEntity<CartaoDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(value = { "/cartoes/{numeroCartao}" }, method = RequestMethod.GET, produces = {
			MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<Double> saldoDoCartao(@PathVariable("numeroCartao") String numeroCartao) {
		ResponseEntity<Double> result = null;
		try {
			Cartao cartao = cartaoBusiness.acharPorNumeroCartao(numeroCartao);
			if (cartao != null) {
				result = new ResponseEntity<Double>(cartao.getSaldo(), HttpStatus.OK);
			} else {
				result = new ResponseEntity<Double>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			result = new ResponseEntity<Double>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
}
