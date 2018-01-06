package com.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Endereco;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.services.exception.ObjectNotFoundException;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Endereco findOne(Integer id) {
		Endereco endereco = enderecoRepository.findOne(id);
		if(endereco == null) {
			throw new ObjectNotFoundException(id, Endereco.class, "Endereço não encontrado");
		}
		return endereco;
	}
}
