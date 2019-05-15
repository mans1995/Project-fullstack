package be.ipl.pae.exceptions;

@SuppressWarnings("serial")
public class BizException extends RuntimeException {

  private boolean forbidden = false;

  public BizException() {
    super();
  }

  public BizException(String message) {
    super(message);
  }

  public BizException(String message, Exception exception) {
    super(message, exception);
  }

  public BizException(boolean forbidden) {
    super();
    this.forbidden = forbidden;
  }

  public BizException(String message, boolean forbidden) {
    super(message);
    this.forbidden = forbidden;
  }

  public BizException(String message, Exception exception, boolean forbidden) {
    super(message, exception);
    this.forbidden = forbidden;
  }

  public boolean isForbidden() {
    return this.forbidden;
  }

}
