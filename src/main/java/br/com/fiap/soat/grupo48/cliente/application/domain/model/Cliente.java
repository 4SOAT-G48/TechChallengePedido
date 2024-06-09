package br.com.fiap.soat.grupo48.cliente.application.domain.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
public class Cliente {
    private UUID id;
    private String nome;
    private String cpf;
    private String email;

}
