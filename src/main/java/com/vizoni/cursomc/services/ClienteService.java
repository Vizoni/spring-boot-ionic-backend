package com.vizoni.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.vizoni.cursomc.domain.Cliente;
import com.vizoni.cursomc.domain.Cliente;
import com.vizoni.cursomc.dto.ClienteDTO;
import com.vizoni.cursomc.repositories.ClienteRepository;
import com.vizoni.cursomc.services.exceptions.DataIntegrityException;
import com.vizoni.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id +
											  ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		/* instancia o objeto a partir do banco para não sobrescrever um cliente já existente */
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente pois há entidades relacionadas!");
		}
		
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	/* O page encapsula as informações e operações da paginação 
	 * parametros: numero da página, qntd de registros por página, ordernação e 'ascendente ou descendente' */
	public Page<Cliente> findPage(Integer pageNumber, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = new PageRequest(pageNumber, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	/* cria um cliente apartir de um objeto DTO da clienteDTO. Como o DTO não tem cpf/cnpj nem tipoCliente, passa NULL*/
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
