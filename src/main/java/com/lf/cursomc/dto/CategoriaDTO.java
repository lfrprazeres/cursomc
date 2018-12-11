package com.lf.cursomc.dto;

import java.io.Serializable;

import com.lf.cursomc.domain.Categoria;

/* DTO significa Data Transfer Object, como eu quero que o meu method GET findAll do categoria só retorne as categorias e não os produtos que ele tem,
 * temos que fazer um objeto que contenha apenas os atributos que queremos mostrar */

public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
		
	}

	/* Como ele vai ser um DTO, esse constructor vai ser responsável por convertar Categoria pra CategoriaDTO */
	public CategoriaDTO(Categoria obj) {
		
		id = obj.getId();
		nome = obj.getNome();
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
