package com.carlosdv93.udemy.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlosdv93.udemy.cursomc.domain.Cliente;
import com.carlosdv93.udemy.cursomc.repositories.ClienteRepository;
import com.carlosdv93.udemy.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objecto não Encontrado: " + id + "\nTipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
}
