package com.lf.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lf.cursomc.domain.Categoria;
import com.lf.cursomc.repositories.CategoriaRepository;
import com.lf.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repositorio;

	/* usa o recurso GET setado no CategoriaResources para buscar categoria pelo ID,
	 * orElseThrow é usado como padrão no .findById, retornando algo caso não consiga achar o ID,
	 * nesse caso é retornado um ObjectNotFoundException (feito manualmente) com a mensagem de erro */
	public Categoria buscar(Integer id) {
		Categoria obj = repositorio.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		return obj;
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repositorio.save(obj);
	}
	
	
	
}
