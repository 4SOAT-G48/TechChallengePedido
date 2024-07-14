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

    @Value(value = "${message.publish.pedido.recebido.route-key}")
    private String pedidosRecebidosRoutekey;

    @Value(value = "${message.publish.pedido.recebido.exchange}")
    private String pedidosRecebidosExchange;

    @Value(value = "${message.publish.pedido.recebido.erro.route-key}")
    private String pedidosRecebidosErroRoutekey;

    @Value(value = "${message.publish.pedido.recebido.erro.exchange}")
    private String pedidosRecebidosErroExchange;

    @Value(value = "${message.publish.pedido.registrado.route-key}")
    private String pedidosRegistradoRoutekey;

    @Value(value = "${message.publish.pedido.registrado.exchange}")
    private String pedidosRegistradoExchange;

    @Value(value = "${message.publish.pedido.registrado.erro.route-key}")
    private String pedidosRegistradoErroRoutekey;

    @Value(value = "${message.publish.pedido.registrado.erro.exchange}")
    private String pedidosRegistradoErroExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publishRecebido(@Payload String message) {
        this.rabbitTemplate.convertAndSend(pedidosRecebidosExchange, pedidosRecebidosRoutekey, message);
        log.info("Mensagem enviada para a fila de pedidos recebidos E:{} R:{} - {}", pedidosRecebidosExchange, pedidosRecebidosRoutekey, message);
    }

    @Override
    public void publishRecebidoErro(String message) {
        this.rabbitTemplate.convertAndSend(pedidosRecebidosErroExchange, pedidosRecebidosErroRoutekey, message);
        log.info("Mensagem enviada para a fila de pedidos recebidos com erro E:{} R:{} - {}", pedidosRecebidosErroExchange, pedidosRecebidosErroRoutekey, message);
    }

    @Override
    public void publishRegistrado(@Payload String message) {
        this.rabbitTemplate.convertAndSend(pedidosRegistradoExchange, pedidosRegistradoRoutekey, message);
        log.info("Mensagem enviada para a fila de pedidos recebidos E:{} R:{} - {}", pedidosRegistradoExchange, pedidosRegistradoRoutekey, message);
    }

    @Override
    public void publishRegistradoErro(String message) {
        this.rabbitTemplate.convertAndSend(pedidosRegistradoErroExchange, pedidosRegistradoErroRoutekey, message);
        log.info("Mensagem enviada para a fila de pedidos recebidos com erro E:{} R:{} - {}", pedidosRegistradoErroExchange, pedidosRegistradoErroRoutekey, message);
    }
}
