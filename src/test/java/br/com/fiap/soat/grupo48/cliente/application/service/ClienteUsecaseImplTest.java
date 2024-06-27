package br.com.fiap.soat.grupo48.cliente.application.service;

import br.com.fiap.soat.grupo48.cliente.application.service.port.out.IClienteRepositoryGateway;
import br.com.fiap.soat.grupo48.cliente.domain.model.Cliente;
import br.com.fiap.soat.grupo48.cliente.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteUsecaseImplTest {

    AutoCloseable autoCloseable;
    private ClienteUsecaseImpl clienteUsecase;
    @Mock
    private IClienteRepositoryGateway clienteRepositoryGateway;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        clienteUsecase = new ClienteUsecaseImpl(clienteRepositoryGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Nested
    class BuscarCliente {

        @Test
        void buscarPeloCpf() {
            // Arrange
            var id = UUID.randomUUID();
            var cliente = ClienteHelper.gerarCliente();
            cliente.setId(id);
            when(clienteRepositoryGateway.buscarPeloCpf(any()))
                .thenReturn(cliente);

            // Act
            var clienteRetornado = clienteUsecase.buscarPeloCpf("777.777.777-77");

            // Assert
            assertThat(clienteRetornado)
                .isNotNull()
                .isEqualTo(cliente);
            verify(clienteRepositoryGateway, times(1))
                .buscarPeloCpf(any());
        }
    }

    @Nested
    class CriarCliente {
        @Test
        void criarCliente() {
            // Arrange
            Cliente cliente = ClienteHelper.gerarCliente();

            // Act
            when(clienteRepositoryGateway
                .salvar(any(Cliente.class)))
                .thenAnswer(i -> {
                    Cliente clienteArgument = i.getArgument(0);
                    clienteArgument.setId(UUID.randomUUID());
                    return clienteArgument;
                });

            // Assert
            Cliente clienteRetornado = clienteUsecase.criarCliente(cliente);
            assertThat(clienteRetornado.getId()).isNotNull();
            assertThat(clienteRetornado.getNome()).isEqualTo(cliente.getNome());
            assertThat(clienteRetornado.getCpf()).isEqualTo(cliente.getCpf());
            assertThat(clienteRetornado.getEmail()).isEqualTo(cliente.getEmail());
        }
    }

}