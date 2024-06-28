package br.com.fiap.soat.grupo48.pedido.application.domain.valueobject;

import br.com.fiap.soat.grupo48.pedido.domain.valueobject.GeradorDeNumeroSequencial;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GeradorDeNumeroSequencialTest {

    @Nested
    class GerarNumeroTest {

        @Test
        void deveGerarNumeroSequencial() {
            // Arrange
            String numeroSequencial1 = GeradorDeNumeroSequencial.getInstance().proximoNumero();
            String numeroSequencial2 = GeradorDeNumeroSequencial.getInstance().proximoNumero();

            // Assert
            assertNotNull(numeroSequencial1);
            assertNotNull(numeroSequencial2);
            assertThat(numeroSequencial1).isNotEqualTo(numeroSequencial2);
        }
    }
}