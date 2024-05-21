package br.com.fiap.soat.grupo48.pedido.application.service.exception;

import br.com.fiap.soat.grupo48.commons.exception.ApplicationException;

import java.io.Serial;

public class MetodoPagamentoInvalidoException extends ApplicationException {

  @Serial
  private static final long serialVersionUID = -5702077678463666943L;

  public MetodoPagamentoInvalidoException(String message) {
    super(message);
  }

  public MetodoPagamentoInvalidoException(String message, Throwable cause) {
    super(message, cause);
  }
}
