package com.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursomc.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
