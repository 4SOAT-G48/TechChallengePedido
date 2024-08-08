package br.com.fiap.soat.grupo48.pedido.application.service.port.in;

import org.springframework.messaging.handler.annotation.Payload;

public interface IPedidoReceverQueueAdapter {
    void receivePagamento(String message);

    void receiveMudancaSituacao(@Payload String message);
}
