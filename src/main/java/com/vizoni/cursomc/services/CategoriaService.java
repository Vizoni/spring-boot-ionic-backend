package com.vizoni.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vizoni.cursomc.domain.Categoria;
import com.vizoni.cursomc.repositories.CategoriaRepository;
import com.vizoni.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
											  ", Tipo: " + Categoria.class.getName());
		}
		return obj;
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		/* seta o Id como nulo para garantir que é um objeto novo
		 * se o ID viesse preenchido, ele ATUALIZARIA com o método save, ao invés de CRIAR um novo*/
		return repo.save(obj);
	}
	
}
