package br.com.fiap.soat.grupo48.pedido.application.service.port.out;

public interface IPedidoPublishQueueAdapter {
  void publishRecebido(String message);
}
