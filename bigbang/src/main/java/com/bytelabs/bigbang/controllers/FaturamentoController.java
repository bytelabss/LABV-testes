package com.bytelabs.bigbang.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytelabs.bigbang.services.FaturamentoService;
import com.bytelabs.bigbang.services.PedidoService;

@RestController
@RequestMapping("/faturamento")
public class FaturamentoController {
    private final PedidoService pedidoService;
    private final FaturamentoService faturamentoService;

    public FaturamentoController(PedidoService pedidoService, FaturamentoService faturamentoService) {
        this.pedidoService = pedidoService;
        this.faturamentoService = faturamentoService;
    }

    @GetMapping
    public double calcularFaturamento() {
        return faturamentoService.calcularFaturamento(pedidoService.getPedidos());
    }
}
