package br.com.fiap.soat.grupo48.pedido.domain.model;

import br.com.fiap.soat.grupo48.produto.domain.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PedidoItem {
    private UUID id;

    private Produto produto;

    private Integer quantidade;
    private Double precoUnitario;

    private String observacao;

    public Double getTotal() {
        return precoUnitario * quantidade;
    }

}
