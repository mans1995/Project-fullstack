package be.ipl.pae.exceptions;

@SuppressWarnings("serial")
public class FatalException extends RuntimeException {

  public FatalException() {
    super();
  }

  public FatalException(String message) {
    super(message);
  }

  public FatalException(String message, Throwable throwable) {
    super(message, throwable);
  }

}
