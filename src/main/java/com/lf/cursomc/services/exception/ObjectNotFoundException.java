package com.lf.cursomc.services.exception;

/* RuntimeException representa uma exception que pode ser prevenida, 
 * dando a resposta pro programador usuário que esse erro pode ser tratado */
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/* é enviado para a superClasse RuntimeException a mensagem */
	public ObjectNotFoundException(String msg) {
		super(msg);
		
	}
	
	/* Aqui é mandado a mensagem e a causa */
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
