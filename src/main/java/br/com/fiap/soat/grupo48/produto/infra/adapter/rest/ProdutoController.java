package br.com.fiap.soat.grupo48.produto.infra.adapter.rest;

import br.com.fiap.soat.grupo48.commons.exception.ApplicationException;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import br.com.fiap.soat.grupo48.produto.application.service.port.in.IProdutoPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "Manuteção de Produtos", description = "Endpoints destinados a manutenção dos produtos usados pela administração")
@RestController
@RequestMapping("api/produtos")
public class ProdutoController {
  private final IProdutoPort produtoServicePort;

  public ProdutoController(IProdutoPort produtoServicePort) {
    this.produtoServicePort = produtoServicePort;
  }

  @Operation(summary = "Recupera lista de produtos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Produtos encontrados",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Produto.class))}),
  })
  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<Produto>> getProdutos() {
    List<Produto> produtos = this.produtoServicePort.buscarProdutos();
    return new ResponseEntity<>(produtos, HttpStatus.OK);
  }

  @GetMapping(
      value = "/paginados",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Page<Produto>> getProdutosPaginados(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    log.info("page: {}, size: {}", page, size);
    Pageable pageable = PageRequest.of(page, size);
    Page<Produto> produtos = this.produtoServicePort.buscarProdutosPaginados(pageable);
    return new ResponseEntity<>(produtos, HttpStatus.OK);
  }

  //<YOUR_GithubPersonalAccessToken_HERE

  @Operation(summary = "Recupera um produto pelo id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Produto encontrado",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = Produto.class))}),
      @ApiResponse(responseCode = "400", description = "Id inválido",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Produto não encontrado",
          content = @Content)})
  @GetMapping(
      value = "/{codigo}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> getProduto(@PathVariable UUID codigo) {
    Produto produto = null;
    try {
      produto = this.produtoServicePort.buscarPeloId(codigo);
      return new ResponseEntity<>(produto, HttpStatus.OK);
    } catch (ProdutoNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> createProduto(@RequestBody Produto request) {

    try {
      Produto produtoSave = this.produtoServicePort.criarProduto(request);
      return new ResponseEntity<>(produtoSave, HttpStatus.CREATED);
    } catch (ApplicationException e) { //todas as exceções da aplicação/negócio
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

  }

  @PutMapping(
      value = "/{codigo}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> updateProduto(@PathVariable final UUID codigo, @RequestBody final Produto request) {
    log.info("Atualizando produto: {}", request);
    try {
      Produto produtoAtualizado = this.produtoServicePort.atualizarProduto(codigo, request);
      return new ResponseEntity<>(produtoAtualizado, HttpStatus.ACCEPTED);
    } catch (ProdutoNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @DeleteMapping(
      value = "{codigo}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<?> deleteProduto(@PathVariable UUID codigo) {
    try {
      this.produtoServicePort.excluirProduto(codigo);
      return new ResponseEntity<>("Produto excluído", HttpStatus.OK);
    } catch (ApplicationException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
