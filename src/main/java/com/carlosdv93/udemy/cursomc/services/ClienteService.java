package com.carlosdv93.udemy.cursomc.services;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carlosdv93.udemy.cursomc.domain.Cidade;
import com.carlosdv93.udemy.cursomc.domain.Cliente;
import com.carlosdv93.udemy.cursomc.domain.Endereco;
import com.carlosdv93.udemy.cursomc.dto.ClienteDTO;
import com.carlosdv93.udemy.cursomc.dto.ClienteNewDTO;
import com.carlosdv93.udemy.cursomc.enums.Perfil;
import com.carlosdv93.udemy.cursomc.enums.TipoCliente;
import com.carlosdv93.udemy.cursomc.repositories.ClienteRepository;
import com.carlosdv93.udemy.cursomc.repositories.EnderecoRepository;
import com.carlosdv93.udemy.cursomc.security.UserSS;
import com.carlosdv93.udemy.cursomc.services.exceptions.AuthorizationException;
import com.carlosdv93.udemy.cursomc.services.exceptions.DataIntegrityException;
import com.carlosdv93.udemy.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Services s3Services;

	
	public Cliente find(Integer id) {
		UserSS user =  UserService.authenticated();
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objecto não Encontrado: " + id + "\nTipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		// TODO Auto-generated method stub
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSS user = UserService.authenticated();
		
		if(user == null ) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		URI uri = s3Services.uploadFile(multipartFile);
		Cliente cli = repo.findOne(user.getId());
		cli.setImageURL(uri.toString());
		repo.save(cli);
		
		return uri;
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repo.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO clienteNewDTO) {
		Cliente cli = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(), clienteNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNewDTO.getTipo()), pe.encode(clienteNewDTO.getSenha()));
		Cidade cid = new Cidade(clienteNewDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(), clienteNewDTO.getComplemente(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(clienteNewDTO.getTelefone1());
		if(clienteNewDTO.getTelefone2() != null) {
			cli.getTelefones().add(clienteNewDTO.getTelefone2());
		}
		if(clienteNewDTO.getTelefone3() != null) {
			cli.getTelefones().add(clienteNewDTO.getTelefone3());
		}
		return cli;
	}
}
