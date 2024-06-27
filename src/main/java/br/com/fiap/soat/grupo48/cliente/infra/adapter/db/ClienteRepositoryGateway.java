package br.com.fiap.soat.grupo48.cliente.infra.adapter.db;

import br.com.fiap.soat.grupo48.cliente.application.service.port.out.IClienteRepositoryGateway;
import br.com.fiap.soat.grupo48.cliente.domain.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteRepositoryGateway implements IClienteRepositoryGateway {
    private final SpringClienteRepository springClienteRepository;

    public ClienteRepositoryGateway(SpringClienteRepository springClienteRepository) {
        this.springClienteRepository = springClienteRepository;
    }

    @Override
    public Cliente buscarPeloCpf(String cpf) {
        return this.springClienteRepository.findByCpf(cpf).toCliente();
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        ClienteEntity clienteEntity;
        clienteEntity = new ClienteEntity(cliente);

        return this.springClienteRepository.save(clienteEntity).toCliente();
    }
}
