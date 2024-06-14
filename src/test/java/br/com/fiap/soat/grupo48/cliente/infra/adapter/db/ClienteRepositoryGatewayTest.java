package br.com.fiap.soat.grupo48.cliente.infra.adapter.db;

import br.com.fiap.soat.grupo48.cliente.application.domain.model.Cliente;
import br.com.fiap.soat.grupo48.cliente.utils.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;


class ClienteRepositoryGatewayTest {

    AutoCloseable mock;
    private ClienteRepositoryGateway clienteRepositoryGateway;
    @Mock
    private SpringClienteRepository springClienteRepository;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        clienteRepositoryGateway = new ClienteRepositoryGateway(springClienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class Salvar {
        @Test
        void deveSalvarUmCliente() {
            // Arrange
            Cliente cliente = ClienteHelper.gerarCliente();
            ClienteEntity clienteEntity = ClienteHelper.gerarClienteEntity();
            clienteEntity.setId(UUID.randomUUID());

            Mockito.when(springClienteRepository.save(any(ClienteEntity.class)))
                .thenReturn(clienteEntity);

            // Act
            var clienteCriado = clienteRepositoryGateway.salvar(cliente);

            // Assert
            assertThat(clienteCriado)
                .isInstanceOf(Cliente.class)
                .isNotNull();

            assertThat(clienteCriado.getId())
                .isNotNull()
                .isEqualTo(clienteEntity.getId());
            assertThat(clienteCriado.getNome())
                .isNotNull()
                .isEqualTo(clienteEntity.getNome());
            assertThat(clienteCriado.getEmail())
                .isNotNull()
                .isEqualTo(clienteEntity.getEmail());
            assertThat(clienteCriado.getCpf())
                .isNotNull()
                .isEqualTo(clienteEntity.getCpf());
        }
    }

    @Nested
    class Buscar {
        @Test
        void deveBuscarPorCpf() {
            // Arrange
            ClienteEntity clienteEntity = ClienteHelper.gerarClienteEntity();
            clienteEntity.setId(UUID.randomUUID());

            Mockito.when(springClienteRepository.findByCpf(any(String.class)))
                .thenReturn(clienteEntity);

            // Act
            var cliente = clienteRepositoryGateway.buscarPeloCpf(clienteEntity.getCpf());

            // Assert
            assertThat(cliente)
                .isInstanceOf(Cliente.class)
                .isNotNull();

            assertThat(cliente.getId())
                .isNotNull()
                .isEqualTo(clienteEntity.getId());
            assertThat(cliente.getNome())
                .isNotNull()
                .isEqualTo(clienteEntity.getNome());
            assertThat(cliente.getEmail())
                .isNotNull()
                .isEqualTo(clienteEntity.getEmail());
            assertThat(cliente.getCpf())
                .isNotNull()
                .isEqualTo(clienteEntity.getCpf());
        }
    }
}