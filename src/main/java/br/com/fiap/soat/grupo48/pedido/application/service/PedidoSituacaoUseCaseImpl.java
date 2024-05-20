package br.com.fiap.soat.grupo48.pedido.application.service;


import br.com.fiap.soat.grupo48.pedido.application.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.application.domain.model.SituacaoPedido;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoSituacaoPort;
import br.com.fiap.soat.grupo48.pedido.application.service.port.out.IPedidoRepositoryGateway;

import java.util.List;
import java.util.UUID;

public class PedidoSituacaoUseCaseImpl implements IPedidoSituacaoPort {

  private final IPedidoRepositoryGateway pedidoRepositoryGateway;

  public PedidoSituacaoUseCaseImpl(IPedidoRepositoryGateway pedidoRepositoryGateway) {
    this.pedidoRepositoryGateway = pedidoRepositoryGateway;
  }

  @Override
  public boolean atualizarSituacao(UUID pedidoId, SituacaoPedido situacaoPedido) {
    SituacaoPedido situacaoPedidoAtual = this.pedidoRepositoryGateway.buscaSituacaoPedido(pedidoId);
    if (situacaoPedidoAtual != SituacaoPedido.FINALIZADO) {
      this.pedidoRepositoryGateway.atualizarSituacao(pedidoId, situacaoPedido);
      return true;
    }
    return false;
  }

  @Override
  public List<Pedido> buscarPedidosPorSituacao(List<SituacaoPedido> situacoes) {
    return this.pedidoRepositoryGateway.buscaPedidosPorSituacoes(situacoes);
  }

  @Override
  public List<Pedido> buscarPedidosMostradosMonitorCliente() {
    return this.pedidoRepositoryGateway.buscaPedidosMostradosMonitorCliente();
  }

}
