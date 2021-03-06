package com.vizoni.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vizoni.cursomc.domain.Categoria;
import com.vizoni.cursomc.domain.Cliente;
import com.vizoni.cursomc.dto.CategoriaDTO;
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
		/* instancia o objeto a partir do banco para não sobrescrever uma categoria já existente */
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
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
	
	/* cria uma categoria apartir de um objeto DTO da categoriaDTO*/
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
	
}
