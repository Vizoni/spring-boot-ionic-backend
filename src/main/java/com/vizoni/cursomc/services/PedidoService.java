package com.vizoni.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vizoni.cursomc.domain.Pedido;
import com.vizoni.cursomc.repositories.PedidoRepository;
import com.vizoni.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar (Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
											  ", Tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
}
