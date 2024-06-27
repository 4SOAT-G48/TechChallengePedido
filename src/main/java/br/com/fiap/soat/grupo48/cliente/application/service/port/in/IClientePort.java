package br.com.fiap.soat.grupo48.cliente.application.service.port.in;

import br.com.fiap.soat.grupo48.cliente.domain.model.Cliente;

public interface IClientePort {
    Cliente buscarPeloCpf(String cpf);

    Cliente criarCliente(Cliente cliente);
}
