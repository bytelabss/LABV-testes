package com.bytelabs.bigbang.services;

import org.springframework.stereotype.Service;

import com.bytelabs.bigbang.models.Pedido;
import com.bytelabs.bigbang.repositories.PedidoRepository;

import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> getPedidos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> getPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public Pedido salvarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}