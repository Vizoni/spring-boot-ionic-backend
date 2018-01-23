package com.vizoni.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vizoni.cursomc.domain.Categoria;
import com.vizoni.cursomc.domain.Produto;
import com.vizoni.cursomc.repositories.CategoriaRepository;
import com.vizoni.cursomc.repositories.ProdutoRepository;
import com.vizoni.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Produto obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
											  ", Tipo: " + Produto.class.getName());
		}
		return obj;
	}
	
	public Page<Produto> search(String nome, List<Integer> ids,Integer pageNumber, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = new PageRequest(pageNumber, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids);
//		return repo.search(nome, categorias, pageRequest);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome,  categorias, pageRequest);
	}

	
}
