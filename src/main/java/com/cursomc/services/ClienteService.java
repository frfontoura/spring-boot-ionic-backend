package com.cursomc.services;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.services.exception.DataIntegrityException;
import com.cursomc.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente findOne(Integer id) {
		Cliente cliente = clienteRepository.findOne(id);
		if (cliente == null) {
			throw new ObjectNotFoundException(id, Cliente.class, "Cliente não encontrado");
		}
		return cliente;
	}

	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		return clienteRepository.save(cliente);
	}

	public Cliente update(Cliente cliente) {
		Cliente clienteDB = findOne(cliente.getId());
		updateData(clienteDB, cliente);
		return clienteRepository.save(clienteDB);
	}

	public void delete(Integer id) {
		findOne(id);
		try {
			clienteRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível excluir o cliente.", e);
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, size, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO clienteDTO) {
		Cliente cliente = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj(),	TipoCliente.valueOf(clienteDTO.getTipo()));
		Endereco endereco = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(),	clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), cliente, new Cidade(clienteDTO.getCidadeId()));
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteDTO.getTelefone1());
		if (StringUtils.isNotBlank(clienteDTO.getTelefone2())) {
			cliente.getTelefones().add(clienteDTO.getTelefone2());
		}
		if (StringUtils.isNotBlank(clienteDTO.getTelefone3())) {
			cliente.getTelefones().add(clienteDTO.getTelefone3());
		}
		return cliente;
	}

	private void updateData(Cliente clienteDB, Cliente cliente) {
		clienteDB.setNome(cliente.getNome());
		clienteDB.setEmail(cliente.getEmail());
	}

}
