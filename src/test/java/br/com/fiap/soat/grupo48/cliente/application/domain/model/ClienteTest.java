package br.com.fiap.soat.grupo48.cliente.application.domain.model;

import br.com.fiap.soat.grupo48.cliente.utils.ClienteHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteTest {
    @Nested
    class deveCriarCliente {

        @Test
        void viaConstrutor() {
            // Arrange & Act
            UUID id = UUID.randomUUID();
            Cliente cliente = new Cliente(
                id,
                "Cliente teste",
                "777.777.777-77",
                "XXXXXXXXXXXXXXXXXXXXXXXXXXXX");

            // Assert
            assertEquals(id, cliente.getId());
            assertEquals("Cliente teste", cliente.getNome());
            assertEquals("777.777.777-77", cliente.getCpf());
            assertEquals("XXXXXXXXXXXXXXXXXXXXXXXXXXXX", cliente.getEmail());
        }

        @Test
        void viaBuilder() {
            // Arrange & Act
            Cliente cliente = ClienteHelper.gerarCliente();

            // Assert
            assertEquals("Cliente teste", cliente.getNome());
            assertEquals("777.777.777-77", cliente.getCpf());
            assertEquals("cliente.teste@natememail.com", cliente.getEmail());
        }

        @Test
        void viaSetter() {
            // Arrange & Act
            Cliente cliente = new Cliente();
            UUID id = UUID.randomUUID();
            cliente.setId(id);
            cliente.setNome("Novo nome");

            // Assert
            assertEquals(id, cliente.getId());
            assertEquals("Novo nome", cliente.getNome());
        }
    }

    @Nested
    class deveAlterarCliente {

        @Test
        void atualizaNome() {
            // Arrange & Act
            Cliente cliente = ClienteHelper.gerarCliente();

            // Act
            cliente.setNome("Novo nome");

            // Assert
            assertEquals("Novo nome", cliente.getNome());
        }

        @Test
        void atualizaEmail() {
            // Arrange & Act
            Cliente cliente = ClienteHelper.gerarCliente();

            // Act
            cliente.setEmail("XXXXXXXXXXXXXXXXXXXXXXXX");

            // Assert
            assertEquals("XXXXXXXXXXXXXXXXXXXXXXXX", cliente.getEmail());
        }

        @Test
        void atualizaCpf() {
            // Arrange & Act
            Cliente cliente = ClienteHelper.gerarCliente();

            // Act
            cliente.setCpf("888.888.888-88");

            // Assert
            assertEquals("888.888.888-88", cliente.getCpf());
        }
    }

    @Nested
    class deveValidarItensPadraoProduto {

        @Test
        void tetValidarToString() {
            // Arrange & Act
            Cliente cliente = ClienteHelper.gerarCliente();

            // Assert
            assertEquals("Cliente(id=null, nome=Cliente teste, cpf=777.777.777-77, email=cliente.teste@natememail.com)", cliente.toString());
        }

        @Test
        void testValidarEqualsEHashCode() {
            // Arrange & Act
            Cliente cliente = ClienteHelper.gerarCliente();
            Cliente cliente2 = ClienteHelper.gerarCliente();

            // Assert
            assertEquals(cliente, cliente2);
            assertEquals(cliente.hashCode(), cliente2.hashCode());
        }
    }
}