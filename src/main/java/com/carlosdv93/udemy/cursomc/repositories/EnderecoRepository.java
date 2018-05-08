package com.carlosdv93.udemy.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlosdv93.udemy.cursomc.domain.Categoria;

@Repository
public interface EnderecoRepository extends JpaRepository<Categoria, Integer>{

	
	
}
