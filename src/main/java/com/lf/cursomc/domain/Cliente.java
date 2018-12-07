package com.lf.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lf.cursomc.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	private String cpfOuCnpj;
	/* Esse tipo era pra ser tipoCliente, mas como ele será ordenado por ID como foi feito no enum, 
	 * ele será usado como inteiro aqui*/
	private Integer tipo;
	
	@OneToMany(mappedBy="cliente")
	/* @JsonBackReference seria usado aqui, mas foi substituido por @JsonIgnore*/
	@JsonIgnore
	private List<Pedido> pedidos = new ArrayList<>();
	
	/* A parte OneToMany (como o cliente é sempre um que possui mais endereços, essa será a OneToMany)
	 * tem que ter o mappedBy para dizer por quem foi mapeado, no caso o campo cliente*/
	/* JsonManagedReference abilita a deserialização do array para evitar loops infinitos de loop */
	/* @JsonManagedReference seria usado aqui, porém foi substituido por json ignore na parte de JsonBackReference */
	/* fetch = FetchType.EAGER evita o erro LazyInitializationException, pois ele mantém a busca dos itens "lazy", ou seja, em uma busca só */
	@OneToMany(mappedBy="cliente",fetch = FetchType.EAGER)
	private List<Endereco> enderecos = new ArrayList<>();
	
	/* Como telefone só vai existir um string com o número,
	 * será criado um Set (que se comporta como um conjunto, logo não aceita repetições)
	 * para guardar os números de telefone */
	
	/* Como não existe uma relação OneToMany ou ManyToOne, deve-se usar essas duas anotações para esse tipo de implementação
	 * mais sobre isso em http://www.lovemesomecoding.com/2015/07/08/multiple-fetches-with-eager-type-in-hibernate-with-jpa/ */
	@Fetch(FetchMode.SELECT)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>();
	
	public Cliente() {
		
	}

	/* Não esquecer que COLEÇÕES(Arrays) NÃO SE COLOCAM EM CONSTRUCTOR */
	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		/* Como o tipo é Integer, daria erro aqui pois está sendo do tipo TipoCliente e não Integer, 
		 * logo usaremos o getter para pegar*/
		this.tipo = tipo.getCodigo();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		/* essa função será usada pra retornar o enum de TipoCliente que queremos retornar */ 
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) {
		/* usado o getter igualmente para retornar um tipo Inteiro*/
		this.tipo = tipo.getCodigo();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
