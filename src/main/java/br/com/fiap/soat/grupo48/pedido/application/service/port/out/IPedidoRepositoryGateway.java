package br.com.fiap.soat.grupo48.pedido.application.service.port.out;

import br.com.fiap.soat.grupo48.pedido.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;

import java.util.List;
import java.util.UUID;

public interface IPedidoRepositoryGateway {

    Pedido salvar(Pedido pedido);

    Pedido atualizarSituacao(UUID id, SituacaoPedido situacao);

    List<Pedido> buscaPedidosPorSituacoes(List<SituacaoPedido> situacoes);

    List<Pedido> buscaPedidosMostradosMonitorCliente();

    SituacaoPedido buscaSituacaoPedido(UUID id);

    Pedido buscarPedidoPeloPagamento(UUID pagamentoId);

    Pedido buscarPeloId(UUID id);
}
