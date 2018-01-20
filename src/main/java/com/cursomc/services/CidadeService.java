package com.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Estado;
import com.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository clienteRepository;

	public List<Cidade> findByEstado(Estado estado) {
		return clienteRepository.findAllByEstadoOrderByNome(estado);
	}
}