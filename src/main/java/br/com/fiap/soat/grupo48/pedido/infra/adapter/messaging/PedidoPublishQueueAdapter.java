package br.com.fiap.soat.grupo48.pedido.infra.adapter.messaging;

import br.com.fiap.soat.grupo48.pedido.application.service.port.out.IPedidoPublishQueueAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PedidoPublishQueueAdapter implements IPedidoPublishQueueAdapter {

  @Value(value = "${queue.pedidos.recebidos.name}")
  private static String PEDIDOS_RECEBIDOS_QUEUE;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public void publishRecebido(@Payload String message) {
    this.rabbitTemplate.convertAndSend(PEDIDOS_RECEBIDOS_QUEUE, message);
    log.info("Mensagem enviada para a fila de pedidos recebidos: {}", message);
  }
}
