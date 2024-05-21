package br.com.fiap.soat.grupo48.pedido.utils;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.domain.model.SituacaoProduto;
import br.com.fiap.soat.grupo48.produto.infra.adapter.db.ProdutoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.ArrayList;

public abstract class ProdutoHelper {

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

  public static ProdutoEntity gerarProdutoEntity() {
    return ProdutoEntity.builder()
        .nome("Hambúrguer Simples")
        .descricao("Hambúrguer de carne 150g com queijo e alface")
        .preco(20.0)
        .situacao(SituacaoProduto.DISPONIVEL)
        .categoria(Categoria.LANCHE)
        .build();
  }

  public static String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String asXmlString(final Object object) {
    try {
      //retornar o objeto passado em uma string com formato xml
      XmlMapper xmlMapper = new XmlMapper();
      return xmlMapper.writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
