package com.bytelabs.bigbang.services;

import org.springframework.stereotype.Service;

import com.bytelabs.bigbang.models.Pedido;

import java.util.List;

@Service
public class FaturamentoService {
    public double calcularFaturamento(List<Pedido> pedidos) {
        return pedidos.stream()
                .mapToDouble(Pedido::getValor)
                .sum();
    }
}