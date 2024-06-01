package br.com.fiap.soat.grupo48.pedido.application.service.port.in;

public interface IPedidoReceverQueueAdapter {
  void receive(String message);
}
