package com.light.owl.exceptions;

public class Assert {
  private Assert() {}

  public static <T> void validate(Validate<T> validate) {
    if (validate.verify(validate.get())) {
      return;
    }
    throw validate.exception();
  }


}
