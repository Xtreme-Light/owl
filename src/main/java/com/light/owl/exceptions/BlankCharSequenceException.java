package com.light.owl.exceptions;


public class BlankCharSequenceException extends RuntimeException  {

  public BlankCharSequenceException() {
    super();
  }

  public BlankCharSequenceException(String message) {
    super(message);
  }

  public BlankCharSequenceException(String message, Throwable cause) {
    super(message, cause);
  }

  public BlankCharSequenceException(Throwable cause) {
    super(cause);
  }

  protected BlankCharSequenceException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
