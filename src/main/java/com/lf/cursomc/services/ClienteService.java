package com.lf.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lf.cursomc.domain.Cliente;
import com.lf.cursomc.repositories.ClienteRepository;
import com.lf.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repositorio;

	/*
	 * usa o recurso GET setado no ClienteResources para buscar categoria pelo ID,
	 * orElseThrow é usado como padrão no .findById, retornando algo caso não
	 * consiga achar o ID, nesse caso é retornado um ObjectNotFoundException (feito
	 * manualmente) com a mensagem de erro
	 */
	public Cliente find(Integer id) {
		Cliente obj = repositorio.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		return obj;
	}

}
