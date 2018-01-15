package com.vizoni.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vizoni.cursomc.domain.Categoria;
import com.vizoni.cursomc.repositories.CategoriaRepository;
import com.vizoni.cursomc.services.exceptions.DataIntegrityException;
import com.vizoni.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
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

	public Categoria update(Categoria obj) {
		find(obj.getId());
		/*chama o método FIND (lá em cima) pq se a categoria n existir, ele já retorna uma exceção*/
		return repo.save(obj);
		/* método SAVE do repository serve tanto para inserir quanto para atualizar
		 * a diferença é que se o ID estiver nulo, ele cria, se já estiver preenchido, atualiza*/
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos!");
		}
		
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}
	
	/* O page encapsula as informações e operações da paginação 
	 * parametros: numero da página, qntd de registros por página, ordernação e 'ascendente ou descendente' */
	public Page<Categoria> findPage(Integer pageNumber, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = new PageRequest(pageNumber, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
}
