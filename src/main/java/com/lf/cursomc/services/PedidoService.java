package com.lf.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lf.cursomc.domain.Pedido;
import com.lf.cursomc.repositories.PedidoRepository;
import com.lf.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repositorio;

	/*
	 * usa o recurso GET setado no CategoriaResources para buscar pedido pelo ID,
	 * orElseThrow é usado como padrão no .findById, retornando algo caso não
	 * consiga achar o ID, nesse caso é retornado um ObjectNotFoundException (feito
	 * manualmente) com a mensagem de erro
	 */
	public Pedido find(Integer id) {
		Pedido obj = repositorio.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		return obj;
	}

}
