package br.com.fiap.soat.grupo48.cliente.utils;

import br.com.fiap.soat.grupo48.cliente.domain.model.Cliente;
import br.com.fiap.soat.grupo48.cliente.infra.adapter.db.ClienteEntity;
import br.com.fiap.soat.grupo48.utils.FormatoHelper;

public abstract class ClienteHelper extends FormatoHelper {

    public static Cliente gerarCliente() {
        return Cliente.builder()
            .nome("Cliente teste")
            .cpf("777.777.777-77")
            .email("cliente.teste@natememail.com")
            .build();
    }

    public static ClienteEntity gerarClienteEntity() {
        return ClienteEntity.builder()
            .nome("Cliente teste")
            .cpf("777.777.777-77")
            .email("cliente.teste@natememail.com")
            .build();
    }
}
