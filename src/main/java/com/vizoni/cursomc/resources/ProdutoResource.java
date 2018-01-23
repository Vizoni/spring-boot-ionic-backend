package com.vizoni.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vizoni.cursomc.domain.Categoria;
import com.vizoni.cursomc.domain.Produto;
import com.vizoni.cursomc.dto.CategoriaDTO;
import com.vizoni.cursomc.dto.ProdutoDTO;
import com.vizoni.cursomc.resources.utils.URL;
import com.vizoni.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {		
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	/* método de páginação */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer pageNumber,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy) 
	{
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<Produto> page = service.search(nomeDecoded, ids, pageNumber, linesPerPage, direction, orderBy);
		Page<ProdutoDTO> pageDTO = page.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(pageDTO);
	}
	
}
