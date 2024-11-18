package com.bytelabs.bigbang.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytelabs.bigbang.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
}