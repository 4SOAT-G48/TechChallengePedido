package br.com.fiap.soat.grupo48.produto.application.service.port.out;

import br.com.fiap.soat.grupo48.produto.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.domain.model.SituacaoProduto;
import br.com.fiap.soat.grupo48.produto.application.service.exception.ProdutoNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IProdutoRepositoryGateway {
    List<Produto> buscarTodos();

    Page<Produto> buscarTodos(Pageable pageable);

    Produto buscarPeloId(UUID id) throws ProdutoNotFoundException;

    Produto salvar(Produto produto) throws ProdutoNotFoundException;

    boolean excluir(UUID id) throws ProdutoNotFoundException;

    List<Produto> buscarPorCategoria(Categoria categoria);

    List<Produto> buscarPorCategoriaESituacao(Categoria categoria, SituacaoProduto situacao);
}
