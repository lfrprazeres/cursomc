package com.lf.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lf.cursomc.services.exception.DataIntegrityException;
import com.lf.cursomc.services.exception.ObjectNotFoundException;

/* Essa notação é usada para Handlers, que são classes que intercepta um erro e trata ele fora da classe,
 * para que seja mais fácil de ler o código e não ficar algo muito grande */
@ControllerAdvice
public class ResourceExceptionHandler {
	
	/* exceptionHandler referencia qual excessão será tratada*/
	@ExceptionHandler(ObjectNotFoundException.class)
	/* Aqui foi criado uma função para o erro 404 (not found) do tipo StandardError que foi criado como auxilio para o Handler */
	/* esses parâmetros são uma assinatura padrão para um exception */
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		/* instancia um erro do tipo padrão criado, dando como atributos:
		 * status NOT_FOUND.value: (404)
		 * e.getMessage:           foi dada no orElseThrow do CategoriaService
		 * System.currentTimeMillis pega o tempo do computador no momento */
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(),e.getMessage(),System.currentTimeMillis());
		/* Aqui retorna o status NOT_FOUND com o erro instanciado acima no corpo do erro */
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}


	/* Esse exceptionHandler trata o erro DataIntegrity error, que é causada quando tenta-se deletar uma classe que contém integridade referenciada.
	 * Ela retornaria erro 500, porém não queremos que retorne erro de servidor, e sim 400(bad request), que nos convém mais, por isso ela deverá
	 * ser tratada. */
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage(),System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação" ,System.currentTimeMillis());
		
		/* Loop para percorrer todos os erros encontrados e adicionar eles no array */
		for( FieldError x : e.getBindingResult().getFieldErrors() ) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	
}
