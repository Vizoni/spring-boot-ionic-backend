package com.vizoni.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.vizoni.cursomc.domain.Categoria;
import com.vizoni.cursomc.dto.CategoriaDTO;
import com.vizoni.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	/* 	essa função vai em /categorias/2 (traz a categoria de ID 2 por exemplo)
	 	a anotação @PathVariable é pra associar o ID de parametro com o ID do value do mapping
		a função retorna um ResponseEntity pq ele já é próprio pra requisições HTTP (traz o código, status etc)
		o ? quer dizer q pode retornar qlqer entity */
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		/* Retorna a resposta (obj) dentro do BODY do HTTP (se estiver ok) */
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		/* O fromCurrentRequest pega o endpoint (nesse caso /categorias)
		 * dps concatena com o ID recebido do objeto através do buildAndExpand e converte ele para URI */
		return ResponseEntity.created(uri).build();
		/* retorna o created baseado na URI montada*/
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		/* Passa por CADA objeto da lista através do método STREAM
		 * executa uma arrow function ( -> ) fazendo com q para objeto (obj) seja criado uma CategoriaDTO
		 * e depois adiciona esta nova CategoriaDTO na lista de collectors*/
		return ResponseEntity.ok().body(listDTO);
	}
	
	/* método de páginação */
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer pageNumber,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy) 
	{
		Page<Categoria> page = service.findPage(pageNumber, linesPerPage, direction, orderBy);
		Page<CategoriaDTO> pageDTO = page.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(pageDTO);
	}
	
	
}
