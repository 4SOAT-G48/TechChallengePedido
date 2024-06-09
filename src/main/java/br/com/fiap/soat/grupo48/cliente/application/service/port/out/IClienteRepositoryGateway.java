package br.com.fiap.soat.grupo48.cliente.application.service.port.out;

import br.com.fiap.soat.grupo48.cliente.application.domain.model.Cliente;

public interface IClienteRepositoryGateway {
  Cliente buscarPeloCpf(String cpf);

  Cliente salvar(Cliente cliente);
}
