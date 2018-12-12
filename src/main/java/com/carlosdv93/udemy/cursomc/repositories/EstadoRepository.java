package com.carlosdv93.udemy.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carlosdv93.udemy.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{

	Page<Estado> findAllByOrderByNome(String order, String nome, Pageable pageRequest);
	
}
