package com.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria findOne(Integer id) {
		Categoria categoria = categoriaRepository.findOne(id);
		if(categoria == null) {
			throw new ObjectNotFoundException(id, Categoria.class, "Categoria não encontrada");
		}
		return categoria;
	}
}
