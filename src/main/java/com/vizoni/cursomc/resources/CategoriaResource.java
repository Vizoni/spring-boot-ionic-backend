package com.vizoni.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vizoni.cursomc.domain.Categoria;
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
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
		/* Retorna a resposta (obj) dentro do BODY do HTTP (se estiver ok) */
	}
	
}
