package br.com.fiap.soat.grupo48.produto.application.domain.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
public class Produto {
  @Setter
  private UUID codigo;
  @Setter
  private String nome;
  @Setter
  private Categoria categoria;
  @Setter
  private Double preco;
  @Setter
  private String descricao;
  @Setter
  private SituacaoProduto situacao;
  private List<String> imagens;

  public void atualiza(Produto produto) {
    this.nome = produto.getNome();
    this.categoria = produto.getCategoria();
    this.preco = produto.getPreco();
    this.descricao = produto.getDescricao();
    this.situacao = produto.getSituacao();
    this.imagens = produto.getImagens();
  }
}