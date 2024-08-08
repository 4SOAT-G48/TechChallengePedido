package br.com.fiap.soat.grupo48.pedido.infra.adapter.messaging;

import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoEmAndamentoPort;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoReceverQueueAdapter;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoSituacaoPort;
import br.com.fiap.soat.grupo48.pedido.domain.model.SituacaoPedido;
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
    private final IPedidoSituacaoPort pedidoSituacaoPort;

    private final Gson gson;

    public PedidoReceverQueueAdapter(IPedidoEmAndamentoPort pedidoEmAndamentoPort, IPedidoSituacaoPort pedidoSituacaoPort, Gson gson) {
        this.pedidoEmAndamentoPort = pedidoEmAndamentoPort;
        this.pedidoSituacaoPort = pedidoSituacaoPort;
        this.gson = gson;
    }

    @RabbitListener(queues = "${message.recever.pagamento.efetuado.queues}")
    @Override
    public void receivePagamento(@Payload String message) {
        HashMap<String, String> map = gson.fromJson(message, HashMap.class);
        pedidoEmAndamentoPort.pagamentoEfetuado(UUID.fromString(map.get("pedidoId")), UUID.fromString(map.get("pagamentoId")));
        log.info("Mensagem recebida da fila de pagamento efetuado: {}", message);
    }

    @RabbitListener(queues = "${message.recever.pedido.mudanca-situacao.queues}")
    @Override
    public void receiveMudancaSituacao(@Payload String message) {
        try {
            HashMap<String, String> map = gson.fromJson(message, HashMap.class);
            pedidoSituacaoPort.atualizarSituacao(UUID.fromString(map.get("pedidoId")), SituacaoPedido.valueOf(map.get("situacao")));
            log.info("Mensagem recebida da fila de mudança de situação: {}", message);
        } catch (IllegalArgumentException e) {
            log.error("Erro ao desserializar mensagem: {}", message, e);
            //TODO: Implementar tratamento de erro - enviar para fila de erro
        }
    }
}
