package br.com.fiap.soat.grupo48.pedido.infra.adapter.db;

import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;
import br.com.fiap.soat.grupo48.pedido.utils.PedidoHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PedidoEntityTest {

    @Nested
    class construtores {
        @Test
        void testConstrutor() {
            PedidoEntity pedidoEntity = new PedidoEntity();
            assertNotNull(pedidoEntity);
        }


        @Test
        void testConstrutorComParametros() {
            PedidoEntity pedidoEntity = new PedidoEntity(PedidoHelper.gerarPedido(UUID.randomUUID()));
            assertNotNull(pedidoEntity);
        }
    }

    @Nested
    class atribucoesCampos {

        @Test
        void testSetters() {
            // Arrange & Act
            PedidoEntity pedidoEntity = new PedidoEntity();
            pedidoEntity.setSituacao(SituacaoPedido.EM_ANDAMENTO);
            UUID pagamentoId = UUID.randomUUID();
            pedidoEntity.setPagamentoId(pagamentoId);

            // Assert
            assertNotNull(pedidoEntity);
            assertNotNull(pedidoEntity.getSituacao());
            assertNotNull(pedidoEntity.getPagamentoId());
            assertThat(pedidoEntity.getSituacao()).isEqualTo(SituacaoPedido.EM_ANDAMENTO);
            assertThat(pedidoEntity.getPagamentoId()).isEqualTo(pagamentoId);

        }
    }
}