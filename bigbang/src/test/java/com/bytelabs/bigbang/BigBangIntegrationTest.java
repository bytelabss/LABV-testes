package com.bytelabs.bigbang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bytelabs.bigbang.models.Cliente;
import com.bytelabs.bigbang.models.Pedido;
import com.bytelabs.bigbang.repositories.ClienteRepository;
import com.bytelabs.bigbang.repositories.PedidoRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BigBangIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @BeforeEach
    public void setup() {
        // Ensure the repositories are clean before each test
        clienteRepository.deleteAll();
        pedidoRepository.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    public void testFullIntegration() {
        // 1. Criar clientes
        Cliente cliente1 = criarCliente("João");
        Cliente cliente2 = criarCliente("Maria");

        // 2. Verificar se os clientes foram criados
        ResponseEntity<Cliente[]> clientesResponse = restTemplate.getForEntity("/clientes", Cliente[].class);
        assertThat(clientesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(clientesResponse.getBody()).hasSize(2);

        // 3. Criar dois pedidos para o cliente 1
        criarPedido(cliente1.getId(), 100.0);
        criarPedido(cliente1.getId(), 150.0);

        // 4. Criar um pedido para o cliente 2
        criarPedido(cliente2.getId(), 200.0);

        // 5. Verificar pedidos do cliente 1
        ResponseEntity<Pedido[]> pedidosResponseCliente1 = restTemplate.getForEntity(
                "/clientes/" + cliente1.getId() + "/pedidos", Pedido[].class);
        assertThat(pedidosResponseCliente1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(pedidosResponseCliente1.getBody()).hasSize(2);
        assertThat(pedidosResponseCliente1.getBody()[0].getValor()).isEqualTo(100.0);
        assertThat(pedidosResponseCliente1.getBody()[1].getValor()).isEqualTo(150.0);

        // 6. Verificar pedidos do cliente 2
        ResponseEntity<Pedido[]> pedidosResponseCliente2 = restTemplate.getForEntity(
                "/clientes/" + cliente2.getId() + "/pedidos", Pedido[].class);
        assertThat(pedidosResponseCliente2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(pedidosResponseCliente2.getBody()).hasSize(1);
        assertThat(pedidosResponseCliente2.getBody()[0].getValor()).isEqualTo(200.0);

        // 7. Verificar faturamento total
        ResponseEntity<Double> faturamentoResponse = restTemplate.getForEntity("/faturamento", Double.class);
        assertThat(faturamentoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(faturamentoResponse.getBody()).isEqualTo(450.0);
    }

    private Cliente criarCliente(String nome) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);

        ResponseEntity<Cliente> response = restTemplate.postForEntity("/clientes", cliente, Cliente.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }

    private Pedido criarPedido(Long clienteId, Double valor) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setValor(valor);

        ResponseEntity<Pedido> response = restTemplate.postForEntity(
                "/clientes/" + clienteId + "/pedidos", pedido, Pedido.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        return response.getBody();
    }
}
