package br.com.fiap.soat.grupo48.cliente.application.service;

import br.com.fiap.soat.grupo48.cliente.application.domain.model.Cliente;
import br.com.fiap.soat.grupo48.cliente.application.service.port.in.IClientePort;
import br.com.fiap.soat.grupo48.cliente.application.service.port.out.IClienteRepositoryGateway;

public class ClienteUsecaseImpl implements IClientePort {
  private final IClienteRepositoryGateway iClienteRepositoryGateway;

  public ClienteUsecaseImpl(IClienteRepositoryGateway iClienteRepositoryGatewayParam) {
    this.iClienteRepositoryGateway = iClienteRepositoryGatewayParam;
  }

  @Override
  public Cliente buscarPeloCpf(String cpf) {
    return this.iClienteRepositoryGateway.buscarPeloCpf(cpf);
  }

  @Override
  public Cliente criarCliente(Cliente cliente) {
    return this.iClienteRepositoryGateway.salvar(cliente);
  }
}
