package com.lf.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lf.cursomc.domain.Categoria;
import com.lf.cursomc.dto.CategoriaDTO;
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
	 * @PathVariable é uma notação que mapeia o id do requestMapping ("/{id}") pro parâmetro id que vai estar na função */
	/* Aqui poderia ser ResponseEntity<?>*/
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/* Mesma coisa que @PostMapping */
	@RequestMapping(method = RequestMethod.POST)
	/* @RequestBody faz o json ser convertido para o objeto automáticamente */ 
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto,@PathVariable Integer id){
		/* Caso o parâmetro seja um objeto, pode ser usado obj.setId(id) para confirmar se o obj a ser atualizado é esse */
		Categoria obj = service.fromDTO(objDto);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	/* É do tipo void pois quando deletar vai retornar algo vazio */
	/* No caso do DELETE com classes associada dará status 500 para manter a integridade referencial,
	 * para acabar com esse erro terá 2 opções:
	 * 1: apaga junto as classes que estão sendo referenciadas
	 * 2: aborta a deleção, criando uma exceção personalizada para esse erro */
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		
		/* Explicando a linha abaixo, cria-se essa lista para poder percorrer a lista de cima e transformar ela de Categoria para CategoriaDTO,
		 * instanciando uma CategoriaDTO para cada categoria correspondente
		 * 1º Passo: list.stream() é um recurso para percorrrer uma lista
		 * 2º Passo: stream().map() faz uma operação para cada elemento da lista 
		 * 3º Passo: dentro do map ele faz a operação Arrow function dando como parâmetro obj (que foi o item que foi percorrido)
		 * 4º Passo: essa função anônima instancia um novo CategoriaDTO colocando a Categoria obj como parâmetro
		 * 5º Passo: instanciando esse CategoriaDTO, usa-se .collect(Collectors.toList()) para coletar esse objeto para a lista */
		
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	/* esse método findPage deve ter os mesmos parâmetros que no CategoriaService */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			/* RequestParam é um mapeamento dizendo que esses parâmetros vão ser feitos como argumentos na URL e serão opcionais 
			 * Value nesse caso significa o nome que tem que ser escrito para acessar esse argumento
			 * defaultValue é o valor padrão caso não seja chamado
			 */
			@RequestParam(value="page",defaultValue="0") Integer page, 
			/* o defaultValue é usado 24 pois ele é múltiplo de 1, 2, 3 e 4, ficando fácil de organizar de forma responsiva */
			@RequestParam(value="linesPerPage",defaultValue="24") Integer linesPerPage, 
			/* o defaultValue de orderBy pode ser ordenar por qualquer propriedade que queira */
			@RequestParam(value="orderBy",defaultValue="nome") String orderBy, 
			/* o defaultValue de direction pode ser ASC(ascendente) ou DESC(descendente) */
			@RequestParam(value="direction",defaultValue="ASC") String direction) {
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		/* o Page já sendo do java 8, ele não precisa nem de .stream nem de .collect */
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}
