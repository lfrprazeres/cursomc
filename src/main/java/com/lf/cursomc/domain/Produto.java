package com.lf.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel
public class Produto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "the products' Id, it's auto increment")
	private Integer id;
	@ApiModelProperty(notes = "the products' name")
	private String nome;
	@ApiModelProperty(notes = "the products' price")
	private Double preco;
	
	@JsonBackReference
	@ManyToMany(fetch=FetchType.EAGER)
	/* como é muitos para muitos deve-se criar uma tabela entre a relação das 2 tabelas com o id de cada entidade relacionada */
	@JoinTable(name= /* name representa o nome da tabela que vai ter esse id */"PRODUTO_CATEGORIA",
		joinColumns = /* seta o nome da primeira chave estrangeira */ @JoinColumn(name = "produto_id"),
		inverseJoinColumns = /* seta a segunda chave estrangeira*/ @JoinColumn(name = "categoria_id")
	)
	/* Como eu quero que apareça somente do oturo lado, ele omite a lista de categorias pois já está buscando do outro lado */
	
	private List<Categoria> categorias = new ArrayList<>();
	
	
	/* Diz para o json nem reparar que esse List exista, logo não vai ser serializado */
	@JsonIgnore
	/* Como a tabela ItemPedido está intermediando pedido e produto, 
	 * deve-se criar um conjunto com os itens para que o produto saiba quem são os itens desse produto */
	@OneToMany(mappedBy = "id.produto")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	@JsonIgnore
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();
		for(ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		return lista;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
