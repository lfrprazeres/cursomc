package com.lf.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

/* Classe criada  */
public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;
	
	/* Um array para guardar os erros */
	private List<FieldMessage> errors = new ArrayList<>();
	
	/* Constructor herdado de StandardError */
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	/* retornando o array com os erros*/
	public List<FieldMessage> getErrors() {
		return errors;
	}

	/* Adicionando um erro ao array */
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName,message));
	}

}
