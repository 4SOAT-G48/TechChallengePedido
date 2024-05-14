package br.com.fiap.soat.grupo48.produto.infra.adapter.db;

import br.com.fiap.soat.grupo48.produto.application.domain.model.Categoria;
import br.com.fiap.soat.grupo48.produto.application.domain.model.Produto;
import br.com.fiap.soat.grupo48.produto.application.domain.model.SituacaoProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
@Entity
@Table(name = "produtos")
public class ProdutoEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID codigo;
  private String nome;
  @Enumerated(EnumType.STRING)
  private Categoria categoria;
  private Double preco;
  private String descricao;
  @Enumerated(EnumType.STRING)
  private SituacaoProduto situacao;
  @ElementCollection
  @CollectionTable(name = "produto_imagens", joinColumns = @JoinColumn(name = "produto_codigo"))
  private List<String> images;

  /**
   * Construtor para criar um objeto do domínio
   *
   * @param produto objeto do domínio
   */
  public ProdutoEntity(Produto produto) {
    this.codigo = produto.getCodigo();
    this.atualizar(produto);
  }

  /**
   * Converte este objeto em um objeto do domínio
   *
   * @return Produto
   */
  public Produto toProduto() {
    List<String> imagens;
    if (Objects.isNull(this.images)) {
      imagens = new ArrayList<>();
    } else {
      imagens = this.images;
    }
    return new Produto(this.codigo, this.nome, this.categoria, this.preco, this.descricao, this.situacao,
        imagens);
  }

  /**
   * Atualiza os dados do produto
   *
   * @param produto objeto do domínio
   */
  public void atualizar(Produto produto) {
    this.nome = produto.getNome();
    this.categoria = produto.getCategoria();
    this.preco = produto.getPreco();
    this.descricao = produto.getDescricao();
    this.situacao = produto.getSituacao();
    this.images = new ArrayList<>();
    if (Objects.nonNull(produto.getImagens())) {
      this.images = produto.getImagens();
    }
  }
}
