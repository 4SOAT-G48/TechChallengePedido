package br.com.fiap.soat.grupo48.cliente.infra.adapter.rest;

import br.com.fiap.soat.grupo48.cliente.application.domain.model.Cliente;
import br.com.fiap.soat.grupo48.cliente.application.service.port.in.IClientePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "Gerenciamento de Usuários", description = "Endpoints de criação e listagem de usuário")
@RestController
@RequestMapping("api/clientes")
public class ClienteController {
  private final IClientePort clientePort;

  public ClienteController(IClientePort clienteServicePort) {
    this.clientePort = clienteServicePort;
  }

  @Operation(summary = "Busca usuário por cpf")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
  })
  @GetMapping(value = "/{cpf}")
  public ResponseEntity<Cliente> getCliente(@PathVariable String cpf) {
    Cliente cliente = this.clientePort.buscarPeloCpf(cpf);

    if (Objects.isNull((cliente))) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
  }

  @Operation(summary = "Adiciona novo usuário")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário adicionado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
      @ApiResponse(responseCode = "400", description = "Usuário duplicado", content = {@Content}),
  })
  @PostMapping
  public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
    Cliente buscarCliente = this.clientePort.buscarPeloCpf(cliente.getCpf());
    if (Objects.isNull(buscarCliente)) {
      Cliente clienteSave = this.clientePort.criarCliente(cliente);
      return new ResponseEntity<>(clienteSave, HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
