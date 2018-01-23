package com.vizoni.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vizoni.cursomc.domain.Pedido;
import com.vizoni.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {		
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		/* Retorna a resposta (obj) dentro do BODY do HTTP (se estiver ok) */
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		/* O fromCurrentRequest pega o endpoint (nesse caso /categorias)
		 * dps concatena com o ID recebido do objeto através do buildAndExpand e converte ele para URI */
		return ResponseEntity.created(uri).build();
		/* retorna o created baseado na URI montada*/
	}
	
	
	
}
