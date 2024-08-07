package br.com.fiap.soat.grupo48.pedido.infra.adapter.messaging;

import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoEmAndamentoPort;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoReceverQueueAdapter;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Service
public class PedidoReceverQueueAdapter implements IPedidoReceverQueueAdapter {

    private final IPedidoEmAndamentoPort pedidoEmAndamentoPort;

    private final Gson gson;

    public PedidoReceverQueueAdapter(IPedidoEmAndamentoPort pedidoEmAndamentoPort, Gson gson) {
        this.pedidoEmAndamentoPort = pedidoEmAndamentoPort;
        this.gson = gson;
    }

    @RabbitListener(queues = "${message.recever.pagamento.efetuado.queues}")
    @Override
    public void receive(@Payload String message) {
        HashMap<String, String> map = gson.fromJson(message, HashMap.class);
        pedidoEmAndamentoPort.pagamentoEfetuado(UUID.fromString(map.get("pedidoId")), UUID.fromString(map.get("pagamentoId")));
        log.info("Mensagem recebida da fila de pagamento efetuado: {}", message);
    }
}
