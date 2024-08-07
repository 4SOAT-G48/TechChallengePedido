package br.com.fiap.soat.grupo48.pedido.application.domain.model;

import br.com.fiap.soat.grupo48.pedido.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.domain.model.PedidoItem;
import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;
import br.com.fiap.soat.grupo48.pedido.utils.PedidoHelper;
import br.com.fiap.soat.grupo48.produto.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.utils.ProdutoHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PedidoTest {

    @Nested
    class deveCriar {
        @Test
        void viaBuilder() {
            // Arrange & Act
            var clienteId = UUID.randomUUID();
            Pedido pedido = PedidoHelper.gerarPedido(clienteId);
            var id = UUID.randomUUID();
            pedido.setId(id);

            // Assert
            assertEquals(id, pedido.getId());
            assertEquals(SituacaoPedido.EM_ANDAMENTO, pedido.getSituacao());
            assertEquals(clienteId, pedido.getClienteId());
            assertEquals(3, pedido.getItens().size());
            assertEquals(3, pedido.getItens().stream().filter(i -> i.getQuantidade() == 1).count());
        }

        @Test
        void viaConstructor() {
            // Arrange & Act
            Pedido pedido = new Pedido();

            // Assert
            assertNull(pedido.getId());
        }

        @Test
        void viaConstructorComParametros() {
            // Arrange & Act
            var clienteId = UUID.randomUUID();
            Pedido pedido = new Pedido(UUID.randomUUID(), clienteId, SituacaoPedido.EM_ANDAMENTO, "UU000002", PedidoHelper.gerarListaItens(), null);

            // Assert
            assertEquals(clienteId, pedido.getClienteId());
            assertEquals("UU000002", pedido.getIdentificacao());
            assertEquals(SituacaoPedido.EM_ANDAMENTO, pedido.getSituacao());
            assertEquals(3, pedido.getItens().size());
            assertEquals(3, pedido.getItens().stream().filter(i -> i.getQuantidade() == 1).count());
            assertNull(pedido.getDataCriacao());

        }

        @Test
        void viaSetter() {
            // Arrange & Act
            Pedido pedido = new Pedido();
            var id = UUID.randomUUID();
            pedido.setId(id);
            pedido.setClienteId(UUID.randomUUID());
            pedido.setSituacao(SituacaoPedido.EM_ANDAMENTO);
            pedido.setDataCriacao(null);
            pedido.setDataAtualizacao(null);

            var itemId = UUID.randomUUID();
            PedidoItem item = new PedidoItem();
            item.setId(itemId);
            item.setProduto(ProdutoHelper.gerarProduto());
            item.setQuantidade(1);
            item.setPrecoUnitario(item.getProduto().getPreco());
            item.setObservacao("Teste");

            pedido.setItens(List.of(item));

            // Assert
            assertEquals(id, pedido.getId());
            assertEquals(SituacaoPedido.EM_ANDAMENTO, pedido.getSituacao());
            assertEquals(1, pedido.getItens().size());
            assertEquals(1, pedido.getItens().stream().filter(i -> i.getQuantidade() == 1).count());
            assertNull(pedido.getDataCriacao());
            assertNull(pedido.getDataAtualizacao());


            PedidoItem pedidoItem = pedido.getItens().get(0);
            assertEquals(item.getPrecoUnitario() * item.getQuantidade(), pedidoItem.getTotal());
            assertThat(pedidoItem.getProduto()).isEqualTo(item.getProduto());
            assertThat(pedidoItem.getQuantidade()).isEqualTo(item.getQuantidade());
            assertThat(pedidoItem.getPrecoUnitario()).isEqualTo(item.getPrecoUnitario());
            assertThat(pedidoItem.getObservacao()).isEqualTo(item.getObservacao());
            assertThat(pedidoItem.getId()).isEqualTo(item.getId());
        }
    }

    @Nested
    class deveAtualizarPedido {
        @Test
        void testSituacao() {
            // Arrange & Act
            Pedido pedido = new Pedido();
            pedido.setSituacao(SituacaoPedido.RECEBIDO);
            pedido.setSituacao(SituacaoPedido.EM_ANDAMENTO);
            pedido.setSituacao(SituacaoPedido.EM_PREPARACAO);
            pedido.setSituacao(SituacaoPedido.PRONTO);
            pedido.setSituacao(SituacaoPedido.EM_ENTREGA);
            pedido.setSituacao(SituacaoPedido.FINALIZADO);

            // Assert
            assertEquals(SituacaoPedido.FINALIZADO, pedido.getSituacao());

        }

        @Test
        void testItens() {
            // Arrange & Act
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            Produto produto1 = ProdutoHelper.gerarProduto();
            PedidoItem pedidoItem1 = new PedidoItem(null, produto1, 1, produto1.getPreco(), "");
            pedido.setItens(List.of(pedidoItem1));

            // Assert
            assertEquals(1, pedido.getItens().size());
        }

        @Test
        void testDataAtualizacao() {
            // Arrange & Act
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            pedido.setDataAtualizacao(null);

            // Assert
            assertNull(pedido.getDataAtualizacao());
        }
    }

    @Nested
    class deveValidarItensPadraoProduto {
        @Test
        void testValidarEqualsEHashCode() {
            // Arrange & Act
            var id = UUID.randomUUID();
            Pedido pedido1 = PedidoHelper.gerarPedido(id);
            Pedido pedido2 = PedidoHelper.gerarPedido(id);
            pedido2.setItens(pedido1.getItens());
            Pedido pedido3 = PedidoHelper.gerarPedido(UUID.randomUUID());
            Pedido pedido4 = PedidoHelper.gerarPedido(null);
            Pedido pedido5 = new Pedido();

            // Assert
            assertThat(pedido1)
                .isEqualTo(pedido1)
                .isEqualTo(pedido2)
                .isNotEqualTo(pedido3)
                .isNotEqualTo(pedido4)
                .isNotEqualTo(pedido5)
                .hasSameHashCodeAs(pedido2)
                .doesNotHaveSameHashCodeAs(pedido3)
                .doesNotHaveSameHashCodeAs(pedido4)
                .doesNotHaveSameHashCodeAs(pedido5);

            assertThat(pedido1.hashCode()).isNotEqualTo(pedido3.hashCode());
        }

        @Test
        void testValidarToString() {
            // Arrange
            var id = UUID.randomUUID();
            Pedido pedido = PedidoHelper.gerarPedido(id);

            // Act
            String toString = pedido.toString();

            // Assert
            assertThat(toString)
                .contains(id.toString())
                .hasSize(458)
                .contains("identificacao=TE000001")
                .contains("situacao=EM_ANDAMENTO")
            ;
        }
    }

}