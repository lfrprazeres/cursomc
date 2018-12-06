package com.lf.cursomc.domain.enums;

/* UASNDO A NOTAÇÃO:
 * 
 * PESSOAFISICA,
 * PESSOAJURIDICA;
 * 
 * Mapeando no JPA, ou ele retorna os campos do tipo STRING, ou retorna Integer de 0 a N, o tipo STRING ocupa mais espaço */

public enum TipoCliente {
	/* Nesse caso PESSOAFISICA = 1 e PESSOAJURIDICA = 2, pois estou INSTANCIANDO o enum com constructor feito ali embaixo */
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	
	/* Observação que constructor do enum é do tipo PRIVATE */
	private TipoCliente(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	
	/* ele vai ser estático pra poder ser usada mesmo sem instanciar objetos, toEnum converte para ENUM */
	public static TipoCliente toEnum(Integer codigo) {
		if(codigo == null) { /* Forma para proteger e retornar se for nulo */
			return null;
		}
		/* Forma de escrever um for para varrer um array, indo até o final do array tipoCliente */
		for(TipoCliente x : TipoCliente.values()) {
			/* usando um if pra se o código que ele parou for igual ao que a gente deu ele retorna esse x, que é o que a gente quer */
			if(codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		/* Joga uma exceção se ele não chegar até aqui, que significa que ele não retornou nada, logo não existe enum com esse ID */
		throw new IllegalArgumentException("Id invalido " + codigo);
	}
	
}
