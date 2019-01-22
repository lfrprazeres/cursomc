package com.lf.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lf.cursomc.domain.Cliente;
import com.lf.cursomc.dto.ClienteDTO;
import com.lf.cursomc.repositories.ClienteRepository;
import com.lf.cursomc.services.exception.DataIntegrityException;
import com.lf.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repositorio;

	/*
	 * usa o recurso GET setado no ClienteResources para buscar Cliente pelo ID,
	 * orElseThrow é usado como padrão no .findById, retornando algo caso não
	 * consiga achar o ID, nesse caso é retornado um ObjectNotFoundException (feito
	 * manualmente) com a mensagem de erro
	 */
	public Cliente find(Integer id) {
		Cliente obj = repositorio.findById(id).orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		return obj;
	}

	public Cliente update(Cliente obj) {
		/*
		 * Aproveitando o código de cima para verificar se esse id existe, caso ele
		 * exista lançar uma exceção
		 * 
		 * Diferente de Categoria, o Cliente é complexo e tem várias outras classes contidas nela
		 * por isso temos que fazer um objeto intermediário para manter as informações que já existiam
		 * e mudar somente o que queremos que seja mudado
		 */
		Cliente newObj = find(obj.getId());
		updateData(newObj,obj);
		return repositorio.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repositorio.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return repositorio.findAll();
	}

	/*
	 * Page é uma classe padrão do spring para paginação Integer page para se
	 * referir a página 1,2 etc lines per page significa linhas por página que eu
	 * quero que apareça orderBy é para dizer por quem eu quero ordenar (id, nome
	 * etc) direction é para saber qual direção eu quero ordenar, ascendente ou
	 * descendente
	 */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		/*
		 * para fazer uma consulta para retornar uma página, existe um objeto chamado
		 * PageRequest esse direction vem em forma de String, para funcionar deve-se
		 * fazer uma converão para um tipo chamado Direction
		 */

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repositorio.findAll(pageRequest);
	}

	/*
	 * Transforma um ClienteDTO em um Cliente para ser usado no
	 * ClienteResource
	 */
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(), null, null);
	}
	
	/* Um método para alterar o que o ClienteDTO aceita */
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	

}
