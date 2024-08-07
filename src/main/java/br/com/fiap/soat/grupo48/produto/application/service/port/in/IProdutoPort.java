package br.com.fiap.soat.grupo48.produto.application.service.port.in;

import br.com.fiap.soat.grupo48.produto.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProdutoPort {
    List<Produto> buscarProdutos();

    Produto buscarPeloId(UUID id) throws ProdutoNotFoundException;

    Produto criarProduto(Produto produto) throws ProdutoNotFoundException;

    Produto atualizarProduto(UUID id, Produto produto) throws ProdutoNotFoundException;

    boolean excluirProduto(UUID id) throws ProdutoNotFoundException;

    Page<Produto> buscarProdutosPaginados(Pageable pageable);

    List<Produto> buscarProdutosPorCategoria(Categoria categoria);

    List<Produto> buscarProdutosDiponiveisPorCategoria(Categoria categoria);
}
