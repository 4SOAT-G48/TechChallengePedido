package br.com.fiap.soat.grupo48.pedido.application.service.port.in;

import br.com.fiap.soat.grupo48.pedido.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;

import java.util.List;
import java.util.UUID;

public interface IPedidoSituacaoPort {
    boolean atualizarSituacao(UUID pedidoId, SituacaoPedido situacaoPedido);

    List<Pedido> buscarPedidosPorSituacao(List<SituacaoPedido> situacoes);

    List<Pedido> buscarPedidosMostradosMonitorCliente();
}
