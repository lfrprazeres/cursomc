package com.lf.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	
}