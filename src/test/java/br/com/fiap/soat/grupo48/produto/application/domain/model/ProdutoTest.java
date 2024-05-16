package br.com.fiap.soat.grupo48.produto.application.domain.model;

import br.com.fiap.soat.grupo48.pedido.utils.ProdutoHelper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProdutoTest {

  @Nested
  class deveCriarProduto {
    @Test
    void viaConstructor() {
      // Arrange & Act
      Produto produto = new Produto();

      //Assert
      assertNull(produto.getId());
    }

    @Test
    void viaSetter() {
      // Arrange
      Produto produto = new Produto();
      UUID id = UUID.randomUUID();
      produto.setId(id);

      //Act & Assert
      assertEquals(id, produto.getId());
    }

    @Test
    void viaBuilder() {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();
      UUID id = UUID.randomUUID();
      produto.setId(id);

      //Act & Assert
      assertEquals(id, produto.getId());
      assertEquals("Hambúrguer Simples", produto.getNome());
      assertEquals("Hambúrguer de carne 150g com queijo e alface", produto.getDescricao());
      assertEquals(20.0, produto.getPreco());
      assertEquals(SituacaoProduto.DISPONIVEL, produto.getSituacao());
      assertEquals(Categoria.LANCHE, produto.getCategoria());
    }
  }

  @Nested
  class deveAtualizarProduto {
    @Test
    void deveAtualizarNome() {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();

      // Act
      produto.setNome("Hambúrguer Duplo");

      // Assert
      assertEquals("Hambúrguer Duplo", produto.getNome());
    }

    @Test
    void deveAtualizarDescricao() {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();

      // Act
      produto.setDescricao("2 Hambúrgueres de carne 150g com queijo, alface e tomate");

      // Assert
      assertEquals("2 Hambúrgueres de carne 150g com queijo, alface e tomate", produto.getDescricao());
    }

    @Test
    void deveAtualizarPreco() {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();

      // Act
      produto.setPreco(25.0);

      // Assert
      assertEquals(25.0, produto.getPreco());
    }

    @Test
    void deveAtualizarSituacao() {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();

      // Act
      produto.setSituacao(SituacaoProduto.INDISPONIVEL);

      // Assert
      assertEquals(SituacaoProduto.INDISPONIVEL, produto.getSituacao());
    }

    @Test
    void deveAtulizarCategoria() {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();

      // Act
      produto.setCategoria(Categoria.ACOMPANHAMENTO);

      // Assert
      assertEquals(Categoria.ACOMPANHAMENTO, produto.getCategoria());
    }

    @Test
    void deveAtualizarTodosOsAtributos() {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();
      Produto produto1 = ProdutoHelper.gerarProduto();

      // Act
      produto1.setNome("Hambúrguer Duplo");
      produto1.setDescricao("2 Hambúrgueres de carne 150g com queijo, alface e tomate");
      produto1.setPreco(25.0);
      produto1.setSituacao(SituacaoProduto.INDISPONIVEL);
      produto1.setCategoria(Categoria.ACOMPANHAMENTO);

      produto.atualiza(produto1);

      // Assert
      assertEquals("Hambúrguer Duplo", produto.getNome());
      assertEquals("2 Hambúrgueres de carne 150g com queijo, alface e tomate", produto.getDescricao());
      assertEquals(25.0, produto.getPreco());
      assertEquals(SituacaoProduto.INDISPONIVEL, produto.getSituacao());
      assertEquals(Categoria.ACOMPANHAMENTO, produto.getCategoria());
    }
  }

  @Nested
  class deveValidarItensPadraoProduto {
    @Test
    void deveValidarToString() {
      // Arrange
      Produto produto = ProdutoHelper.gerarProduto();

      // Act
      String toStringOutput = produto.toString();

      // Assert
      assertThat(toStringOutput)
          .hasSize(160)
          .contains("descricao=Hambúrguer de carne 150g com queijo e alface")
          .contains("preco=20.0")
          .contains("situacao=DISPONIVEL");
    }

    @Test
    void deveValidarEqualsEHashCode() {
      // Arrange
      Produto produto1 = ProdutoHelper.gerarProduto();
      Produto produto2 = ProdutoHelper.gerarProduto();
      Produto produto3 = ProdutoHelper.gerarProduto();
      produto3.setDescricao("Produto diferente");

      // Assert
      assertThat(produto1).isEqualTo(produto1)
          .isNotEqualTo(null)
          .isNotEqualTo(new Object())
          .isEqualTo(produto2)
          .isNotEqualTo(produto3)
          .hasSameHashCodeAs(produto2);
      assertThat(produto1.hashCode()).isNotEqualTo(produto3.hashCode());
    }

  }
}