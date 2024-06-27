package br.com.fiap.soat.grupo48.produto.infra.adapter.rest;

import br.com.fiap.soat.grupo48.produto.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.service.port.in.IProdutoPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Listas de produtos para pedido", description = "Lista de produtos focados para montagem do pedido pelo cliente")
@RestController
@RequestMapping("api/produto/categoria")
public class ProdutoCategoriaController {

    private final IProdutoPort produtoServicePort;

    public ProdutoCategoriaController(IProdutoPort produtoServicePort) {
        this.produtoServicePort = produtoServicePort;
    }

    @Operation(summary = "Recupera lista de produtos por categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos encontrados para a categoria",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = Produto.class))}),
    })
    @GetMapping(value = "/{categoria}")
    ResponseEntity<List<Produto>> getPorCategoria(@PathVariable Categoria categoria) {
        List<Produto> produtos = this.produtoServicePort.buscarProdutosPorCategoria(categoria);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @Operation(summary = "Recupera lista de produtos com situação de DISPONIVEL por categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos  disponíveis encontrados para a categoria",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = Produto.class))}),
    })
    @GetMapping("/disponiveis/{categoria}")
    ResponseEntity<List<Produto>> getPorCategoriaDisponivel(@PathVariable Categoria categoria) {
        List<Produto> produtos = this.produtoServicePort.buscarProdutosDiponiveisPorCategoria(categoria);
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
}
