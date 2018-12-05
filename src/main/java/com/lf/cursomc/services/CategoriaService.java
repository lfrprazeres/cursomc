package com.lf.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lf.cursomc.domain.Categoria;
import com.lf.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repositorio;
	
	public Categoria buscar(Integer id) {
		Categoria obj = repositorio.findById(id).orElse(null);
		return obj;
	}
	
}
