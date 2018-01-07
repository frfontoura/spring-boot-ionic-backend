package com.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Pedido;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido findOne(Integer id) {
		Pedido pedido = pedidoRepository.findOne(id);
		if(pedido == null) {
			throw new ObjectNotFoundException(id, Pedido.class, "Pedido não encontrado");
		}
		return pedido;
	}
}
