package com.carlosdv93.udemy.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.carlosdv93.udemy.cursomc.domain.Cidade;
import com.carlosdv93.udemy.cursomc.domain.Estado;
import com.carlosdv93.udemy.cursomc.dto.CidadeDTO;
import com.carlosdv93.udemy.cursomc.dto.EstadoDTO;
import com.carlosdv93.udemy.cursomc.services.CidadeService;
import com.carlosdv93.udemy.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> buscaEstados(){
		List<Estado> estados = service.findAll();
		List<EstadoDTO> estadosDTO = estados.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(estadosDTO);		
	}
	
	@RequestMapping(value="/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>>  buscaCidades(@PathVariable Integer estadoId){
		List<Cidade> cidade = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> cidades = cidade.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cidades);
	}
}
