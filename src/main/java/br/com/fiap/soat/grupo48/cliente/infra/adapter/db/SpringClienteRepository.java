package br.com.fiap.soat.grupo48.cliente.infra.adapter.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringClienteRepository extends JpaRepository<ClienteEntity, UUID> {
  ClienteEntity findByCpf(String cpf);
}
