package br.com.fiap.soat.grupo48.pedido.infra.adapter.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoRabbitMQConfig {

    @Value("${message.recever.exchange}")
    private String pedidoExchange;
}
