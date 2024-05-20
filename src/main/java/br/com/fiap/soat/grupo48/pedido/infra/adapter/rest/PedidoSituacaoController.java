package br.com.fiap.soat.grupo48.pedido.infra.adapter.rest;

import br.com.fiap.soat.grupo48.pedido.application.domain.model.Pedido;
import br.com.fiap.soat.grupo48.pedido.application.domain.model.SituacaoPedido;
import br.com.fiap.soat.grupo48.pedido.application.service.port.in.IPedidoSituacaoPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Tag(name = "Acompanhamento da situação do pedido",
    description = "Endpoints para atualizar a situação do pedido a medida que vai avançando em cada fase e " +
        " para acompanhar os pedidos por situação")
@RestController
@RequestMapping("api/pedidosituacao")
public class PedidoSituacaoController {

  private final IPedidoSituacaoPort pedidoSituacaoPort;

  public PedidoSituacaoController(IPedidoSituacaoPort pedidoSituacaoPort) {
    this.pedidoSituacaoPort = pedidoSituacaoPort;
  }

  @Operation(summary = "Atualiza situação do pedido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Pedido Atualizado", content = {@Content}),
      @ApiResponse(responseCode = "404", description = "Pedido não Encontrado", content = {@Content}),
  })
  @PutMapping(value = "/{id}")
  public ResponseEntity<Void> updateSituacaoPedido(@PathVariable UUID id, @RequestBody Pedido request) {
    if (Objects.nonNull(id)) {
      request.setId(id);
    }
    if (this.pedidoSituacaoPort.atualizarSituacao(request.getId(), request.getSituacao())) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Recupera a lista de pedidos por situacao")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Pedidos encontrados",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Pedido.class))}),
  })
  @GetMapping
  public ResponseEntity<List<Pedido>> getPedidosPorSituacao(@RequestParam List<SituacaoPedido> situacoes) {
    List<Pedido> pedidos = this.pedidoSituacaoPort.buscarPedidosPorSituacao(situacoes);
    return new ResponseEntity<>(pedidos, HttpStatus.OK);
  }

  @Operation(summary = "Recupera a lista de pedidos com situações para serem mostradas no monitor de acompanhamento do cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Pedidos encontrados",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Pedido.class))}),
  })
  @GetMapping(value = "/monitor")
  public ResponseEntity<List<Pedido>> getPedidosMonitor() {
    List<Pedido> pedidos = this.pedidoSituacaoPort.buscarPedidosMostradosMonitorCliente();
    return new ResponseEntity<>(pedidos, HttpStatus.OK);
  }

}
