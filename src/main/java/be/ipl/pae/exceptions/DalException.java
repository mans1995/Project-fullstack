package be.ipl.pae.exceptions;

@SuppressWarnings("serial")
public class DalException extends RuntimeException {

  public DalException() {
    super();
  }

  public DalException(String message) {
    super(message);
  }

  public DalException(String message, Exception exception) {
    super(message, exception);
  }

}
