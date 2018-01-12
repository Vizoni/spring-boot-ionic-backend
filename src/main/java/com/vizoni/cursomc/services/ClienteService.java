package com.vizoni.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vizoni.cursomc.domain.Cliente;
import com.vizoni.cursomc.repositories.ClienteRepository;
import com.vizoni.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar (Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
											  ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
}
