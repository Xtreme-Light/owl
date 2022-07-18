package com.light.owl.exceptions;

public class NotStringException extends RuntimeException{

  public NotStringException() {
    super("不是String类型");
  }
}
