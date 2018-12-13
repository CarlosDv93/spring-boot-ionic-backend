package com.carlosdv93.udemy.cursomc.dto;

import java.io.Serializable;

import com.carlosdv93.udemy.cursomc.domain.Estado;

public class EstadoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	
	public EstadoDTO() {
		
	}
	
	public EstadoDTO(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public EstadoDTO(Estado estado) {
		this.id = estado.getId();
		this.nome = estado.getNome();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

}
