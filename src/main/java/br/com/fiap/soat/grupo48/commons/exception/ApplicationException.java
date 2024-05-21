package br.com.fiap.soat.grupo48.commons.exception;

/**
 * Exception da aplicação, usado para identificar as Exceptions disparadas pela aplicação.
 */
public class ApplicationException extends Exception {

  private static final long serialVersionUID = 5297268515715579641L;

  public ApplicationException(String message) {
    super(message);
  }

  public ApplicationException(String message, Throwable cause) {
    super(message, cause);
  }
}
