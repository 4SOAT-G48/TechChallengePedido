package br.com.fiap.soat.grupo48.produto.utils;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.domain.model.SituacaoProduto;
import br.com.fiap.soat.grupo48.produto.infra.adapter.db.ProdutoEntity;
import br.com.fiap.soat.grupo48.utils.FormatoHelper;

import java.util.ArrayList;

public abstract class ProdutoHelper extends FormatoHelper {

  public static Produto gerarProduto() {
    return Produto.builder()
        .nome("Hambúrguer Simples")
        .descricao("Hambúrguer de carne 150g com queijo e alface")
        .preco(20.0)
        .situacao(SituacaoProduto.DISPONIVEL)
        .categoria(Categoria.LANCHE)
        .imagens(new ArrayList<>(0))
        .build();
  }

  public static Produto gerarProdutoAcompanhamento() {
    return Produto.builder()
        .nome("Batata Frita")
        .descricao("250g de pura batata")
        .preco(15.0)
        .situacao(SituacaoProduto.DISPONIVEL)
        .categoria(Categoria.ACOMPANHAMENTO)
        .imagens(new ArrayList<>(0))
        .build();
  }

  public static Produto gerarProdutoBebida() {
    return Produto.builder()
        .nome("Refrigerante Lata")
        .descricao("Lata 350ml")
        .preco(6.0)
        .situacao(SituacaoProduto.DISPONIVEL)
        .categoria(Categoria.BEBIDA)
        .imagens(new ArrayList<>(0))
        .build();
  }

  public static ProdutoEntity gerarProdutoEntity() {
    return ProdutoEntity.builder()
        .nome("Hambúrguer Simples")
        .descricao("Hambúrguer de carne 150g com queijo e alface")
        .preco(20.0)
        .situacao(SituacaoProduto.DISPONIVEL)
        .categoria(Categoria.LANCHE)
        .build();
  }

}
