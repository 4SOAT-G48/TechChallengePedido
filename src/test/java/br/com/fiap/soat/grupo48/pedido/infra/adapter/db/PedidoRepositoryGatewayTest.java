package br.com.fiap.soat.grupo48.pedido.infra.adapter.db;

import br.com.fiap.soat.grupo48.pedido.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;
import br.com.fiap.soat.grupo48.pedido.utils.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoRepositoryGatewayTest {

    AutoCloseable mock;
    private PedidoRepositoryGateway pedidoRepositoryGateway;
    @Mock
    private SpringPedidoRepository springPedidoRepository;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        pedidoRepositoryGateway = new PedidoRepositoryGateway(springPedidoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class criarPedido {
        @Test
        void testCriarPedido() {
            // Arrange
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            // Cria a entidade com base no pedido para usar no mock
            PedidoEntity pedidoEntity = new PedidoEntity(pedido);

            when(springPedidoRepository.save(any(PedidoEntity.class)))
                .thenReturn(pedidoEntity);

            // Act
            var pedidoCriado = pedidoRepositoryGateway.salvar(pedido);

            // Assert
            assertThat(pedidoCriado)
                .isInstanceOf(Pedido.class)
                .isNotNull();

            assertThat(pedidoCriado.getIdentificacao()).isEqualTo(pedido.getIdentificacao());
            assertThat(pedidoCriado.getClienteId()).isEqualTo(pedido.getClienteId());
            assertThat(pedidoCriado.getSituacao()).isEqualTo(pedido.getSituacao());
            assertThat(pedidoCriado.getPagamentoId()).isEqualTo(pedido.getPagamentoId());

            verify(springPedidoRepository, times(1)).save(any(PedidoEntity.class));

        }
    }

    @Nested
    class atualizarPedido {
        @Test
        void testAtualizarPedido() {
            // Arrange
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            // Cria a entidade com base no pedido para usar no mock
            PedidoEntity pedidoEntity = new PedidoEntity(pedido);

            when(springPedidoRepository.save(any(PedidoEntity.class)))
                .thenReturn(pedidoEntity);

            // Act
            var pedidoCriado = pedidoRepositoryGateway.salvar(pedido);

            // Assert
            assertThat(pedidoCriado)
                .isInstanceOf(Pedido.class)
                .isNotNull();

            assertThat(pedidoCriado.getIdentificacao()).isEqualTo(pedido.getIdentificacao());
        }

        @Test
        void testAtualizarPedidoSituacao() {
            // Arrange
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            // Cria a entidade com base no pedido para usar no mock
            PedidoEntity pedidoEntity = new PedidoEntity(pedido);
            pedidoEntity.setSituacao(SituacaoPedido.RECEBIDO);

            when(springPedidoRepository.save(any(PedidoEntity.class)))
                .thenReturn(pedidoEntity);
            when(springPedidoRepository.findById(any(UUID.class)))
                .thenReturn(java.util.Optional.of(pedidoEntity));

            // Act
            var pedidoCriado = pedidoRepositoryGateway.atualizarSituacao(UUID.randomUUID(), SituacaoPedido.RECEBIDO);

            // Assert
            assertThat(pedidoCriado)
                .isInstanceOf(Pedido.class)
                .isNotNull();

            assertThat(pedidoCriado.getIdentificacao()).isEqualTo(pedido.getIdentificacao());
            assertThat(pedidoCriado.getSituacao()).isEqualTo(SituacaoPedido.RECEBIDO);

            verify(springPedidoRepository, times(1)).save(any(PedidoEntity.class));
            verify(springPedidoRepository, times(1)).findById(any(UUID.class));
        }
    }

    @Nested
    class buscarPedido {
        @Test
        void testBuscarSituacaoPedido() {
            // Arrange
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            // Cria a entidade com base no pedido para usar no mock
            PedidoEntity pedidoEntity = new PedidoEntity(pedido);

            when(springPedidoRepository.findById(any(UUID.class)))
                .thenReturn(java.util.Optional.of(pedidoEntity));

            // Act
            var situacaoPedido = pedidoRepositoryGateway.buscaSituacaoPedido(UUID.randomUUID());

            // Assert
            assertThat(situacaoPedido)
                .isInstanceOf(SituacaoPedido.class)
                .isNotNull()
                .isEqualTo(pedido.getSituacao());
        }

        @Test
        void testbuscaPedidosMostradosMonitorCliente() {
            // Arrange
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            // Cria a entidade com base no pedido para usar no mock
            PedidoEntity pedidoEntity = new PedidoEntity(pedido);

            when(springPedidoRepository.findPedidosWithoutFinalizados())
                .thenReturn(List.of(pedidoEntity));

            // Act
            var pedidos = pedidoRepositoryGateway.buscaPedidosMostradosMonitorCliente();

            // Assert
            assertThat(pedidos)
                .isInstanceOf(List.class)
                .isNotEmpty()
                .hasSize(1);
        }

        @Test
        void testbuscaPedidosPorSituacoes() {
            // Arrange
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            // Cria a entidade com base no pedido para usar no mock
            PedidoEntity pedidoEntity = new PedidoEntity(pedido);

            when(springPedidoRepository.findBySituacaoIn(any(List.class)))
                .thenReturn(List.of(pedidoEntity));

            // Act
            var pedidos = pedidoRepositoryGateway.buscaPedidosPorSituacoes(List.of(SituacaoPedido.RECEBIDO));

            // Assert
            assertThat(pedidos)
                .isInstanceOf(List.class)
                .isNotEmpty()
                .hasSize(1);
        }

        @Test
        void testbuscarPedidoPeloPagamento() {
            // Arrange
            Pedido pedido = PedidoHelper.gerarPedido(UUID.randomUUID());
            // Cria a entidade com base no pedido para usar no mock
            PedidoEntity pedidoEntity = new PedidoEntity(pedido);

            when(springPedidoRepository.findByPagamentoId(any(UUID.class)))
                .thenReturn(pedidoEntity);

            // Act
            var pedidoEncontrado = pedidoRepositoryGateway.buscarPedidoPeloPagamento(UUID.randomUUID());

            // Assert
            assertThat(pedidoEncontrado)
                .isInstanceOf(Pedido.class)
                .isNotNull();
        }
    }
}