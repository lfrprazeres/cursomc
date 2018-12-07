package com.lf.cursomc.domain.enums;

public enum EstadoPagamento {
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELAD(3, "Cancelado");
	
	private int codigo;
	private String descricao;
	
	/* Observação que constructor do enum é do tipo PRIVATE */
	private EstadoPagamento(int codigo, String descricao) {
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
	public static EstadoPagamento toEnum(Integer codigo) {
		if(codigo == null) { /* Forma para proteger e retornar se for nulo */
			return null;
		}
		/* Forma de escrever um for para varrer um array, indo até o final do array EstadoPagamento */
		for(EstadoPagamento x : EstadoPagamento.values()) {
			/* usando um if pra se o código que ele parou for igual ao que a gente deu ele retorna esse x, que é o que a gente quer */
			if(codigo.equals(x.getCodigo())) {
				return x;
			}
		}
		/* Joga uma exceção se ele não chegar até aqui, que significa que ele não retornou nada, logo não existe enum com esse ID */
		throw new IllegalArgumentException("Id invalido " + codigo);
	}
}
