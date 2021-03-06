package com.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.services.exception.DataIntegrityException;
import com.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria find(Integer id) {
		Categoria categoria = categoriaRepository.findOne(id);
		if(categoria == null) {
			throw new ObjectNotFoundException(id, Categoria.class, "Categoria não encontrada");
		}
		return categoria;
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		Categoria categoriaDB = find(categoria.getId());
		updateData(categoriaDB, categoria);
		return categoriaRepository.save(categoriaDB);
	}

	public void delete(Integer id) {
		find(id);
		try {
			categoriaRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.", e);
		}
	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, size, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	
	private void updateData(Categoria categoriaDB, Categoria categoria) {
		categoriaDB.setNome(categoria.getNome());
	}
}
