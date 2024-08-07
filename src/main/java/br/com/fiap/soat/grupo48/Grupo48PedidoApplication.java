package br.com.fiap.soat.grupo48;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class Grupo48PedidoApplication {

  public static void main(String[] args) {
    SpringApplication.run(Grupo48PedidoApplication.class, args);
  }

}
