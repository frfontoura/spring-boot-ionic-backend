package com.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursomc.domain.Categoria;
import com.cursomc.dto.CategoriaDTO;
import com.cursomc.services.CategoriaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;
	
	@ApiOperation(value="Busca por id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> findOne(@PathVariable Integer id){
		return ResponseEntity.ok(categoriaService.find(id));
	}
	
	@ApiOperation(value="Retorna todas categorias")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> categorias = categoriaService.findAll();
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(c -> new CategoriaDTO(c)).collect(Collectors.toList());
		return ResponseEntity.ok(categoriasDTO);
	}
	
	@ApiOperation(value="Retorna todas categorias com paginação")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="size", defaultValue = "24") Integer size,
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value="direction", defaultValue = "ASC") String direction){
		Page<Categoria> categorias = categoriaService.findPage(page, size, orderBy, direction);
		Page<CategoriaDTO> categoriasDTO = categorias.map(c -> new CategoriaDTO(c));
		return ResponseEntity.ok(categoriasDTO);
	}
	
	@ApiOperation(value="Insere categoria")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoria = categoriaService.insert(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value="Atualiza categoria")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoria.setId(id);
		categoria = categoriaService.update(categoria);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value="Remove categoria")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir uma categoria que possui produtos"),
			@ApiResponse(code = 404, message = "Código inexistente") })
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
