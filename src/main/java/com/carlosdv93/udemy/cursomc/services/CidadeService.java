package com.carlosdv93.udemy.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.carlosdv93.udemy.cursomc.domain.Cidade;
import com.carlosdv93.udemy.cursomc.repositories.CidadeRepository;

public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findByEstado(Integer estadoId){
		return repo.findCidades(estadoId);
	}

}
