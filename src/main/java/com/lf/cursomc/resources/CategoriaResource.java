package com.lf.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lf.cursomc.domain.Categoria;
import com.lf.cursomc.services.CategoriaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/categorias")
/* Api é um mapeamento do swagger que você pode alterar a descrição final que será feita no http://localhost:8080/swagger-ui.html */
@Api(value = "Category Manager System",description = "Operations pertaining to category in category Manager")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
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
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/* Mesma coisa que @PostMapping */
	@RequestMapping(method = RequestMethod.POST)
	/* @RequestBody faz o json ser convertido para o objeto automáticamente */ 
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
