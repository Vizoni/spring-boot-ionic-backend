package com.vizoni.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vizoni.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	// não conta como transação do banco, deixando mais rápido (?)
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}
