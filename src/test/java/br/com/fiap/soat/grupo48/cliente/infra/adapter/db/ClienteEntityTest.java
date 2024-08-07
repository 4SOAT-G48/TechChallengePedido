package br.com.fiap.soat.grupo48.cliente.infra.adapter.db;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClienteEntityTest {
    @Nested
    class construtores {

        @Test
        void testConstrutor() {
            // Arrange & Act
            ClienteEntity clienteEntity = new ClienteEntity();

            // Assert
            assertNotNull(clienteEntity);
        }

        @Test
        void testConstrutorComParametros() {
            // Arrange & Act
            UUID id = UUID.randomUUID();
            ClienteEntity clienteEntity = new ClienteEntity(
                id,
                "Teste",
                "123.456.789-00",
                "email@teste.com"
            );

            // Assert
            assertNotNull(clienteEntity);
            assertNotNull(clienteEntity.getId());
            assertNotNull(clienteEntity.getNome());
            assertNotNull(clienteEntity.getCpf());
            assertNotNull(clienteEntity.getEmail());
            assertEquals(id, clienteEntity.getId());
            assertEquals("Teste", clienteEntity.getNome());
            assertEquals("123.456.789-00", clienteEntity.getCpf());
            assertEquals("email@teste.com", clienteEntity.getEmail());
        }

        @Test
        void testConstrutorComParametrosNull() {
            // Arrange & Act
            ClienteEntity clienteEntity = new ClienteEntity(
                null,
                null,
                null,
                null
            );
            // Assert
            assertNotNull(clienteEntity);
            assertNull(clienteEntity.getId());
            assertNull(clienteEntity.getNome());
            assertNull(clienteEntity.getCpf());
            assertNull(clienteEntity.getEmail());
        }
    }

    @Nested
    class atribucoesCampos {

        @Test
        void testSetters() {
            // Arrange
            ClienteEntity clienteEntity = new ClienteEntity();

            // Act
            UUID id = UUID.randomUUID();
            clienteEntity.setId(id);
            clienteEntity.setNome("Teste");
            clienteEntity.setCpf("123.456.789-00");
            clienteEntity.setEmail("email@teste.com");

            // Assert
            assertNotNull(clienteEntity);
            assertNotNull(clienteEntity.getId());
            assertEquals("Teste", clienteEntity.getNome());
            assertEquals("123.456.789-00", clienteEntity.getCpf());
            assertEquals("email@teste.com", clienteEntity.getEmail());

        }
    }
}