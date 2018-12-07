package com.lf.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cidade implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	/* GeneratedValue seta como será atribuido esse ID,
	 * colocando como estratégia IDENTITY, que é um recurso aceito no MYSQL, H2 entre outros*/
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	/* Como a relação nesse lado da entidade é ManyToOne,
	 * aqui que deverá ter a chave estrangeira representando qual entidade ela pertence */
	/* fetch = FetchType.EAGER evita o erro LazyInitializationException, pois ele mantém a busca dos itens "lazy", ou seja, em uma busca só */
	/* @JsonManagedReference seria usado aqui, porém foi substituido por json ignore na parte de JsonBackReference */
	@ManyToOne(fetch = FetchType.EAGER)
	/* Aqui define qual será o nome da chave estrangeira no banco de dados */
	@JoinColumn(name = "estado_id")
	private Estado estado;
	
	public Cidade() {
		
	}

	public Cidade(Integer id, String nome, Estado estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
