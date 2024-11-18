package com.bytelabs.bigbang.controllers;

import org.springframework.web.bind.annotation.*;

import com.bytelabs.bigbang.models.Cliente;
import com.bytelabs.bigbang.models.Pedido;
import com.bytelabs.bigbang.services.ClienteService;
import com.bytelabs.bigbang.services.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    private final PedidoService pedidoService;

    public ClienteController(ClienteService clienteService, PedidoService pedidoService) {
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.getClientes();
    }

    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return clienteService.salvarCliente(cliente);
    }

    @GetMapping("/{id}/pedidos")
    public List<Pedido> listarPedidosPorCliente(@PathVariable("id") Long clienteId) {
        return pedidoService.getPedidosPorCliente(clienteId);
    }

    @PostMapping("/{id}/pedidos")
    public Pedido criarPedido(@PathVariable("id") Long clienteId, @RequestBody Pedido pedido) {
        pedido.setClienteId(clienteId);
        return pedidoService.salvarPedido(pedido);
    }
}
