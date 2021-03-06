package com.lf.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lf.cursomc.domain.Pedido;
import com.lf.cursomc.services.PedidoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/pedidos")
/* Api é um mapeamento do swagger que você pode alterar a descrição final que será feita no http://localhost:8080/swagger-ui.html */
@Api(value = "Order Manager System",description = "Operations pertaining to order in order Manager")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	/* mesma coisa que @GetMapping("/{id}")*/
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	/* ApiResponses é um mapeamento do swagger que, dentro dele,
	 *  contém x ApiResponse com um status http e uma mensagem para cada um */
	@ApiResponses(value = {
			@ApiResponse(code = 200,message = "sucessfully"),
			@ApiResponse(code = 400,message = "Bad Request"),
			@ApiResponse(code = 401,message = "request unauthorized")
	})
	/* ResponseEntity<?> define que poderá retornar um ResponseEntity de qualquer tipo,
	 * @PathVariable é uma notação que mapeia o id do requestMapping ("/{id}) pro parâmetro id que vai estar na função */
	/* Aqui poderia ser ResponseEntity<?>*/
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
}
