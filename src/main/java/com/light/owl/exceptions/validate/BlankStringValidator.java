package com.light.owl.exceptions.validate;

import com.light.owl.exceptions.Validate;
import com.light.owl.util.OptionalCharSequence;

public class BlankStringValidator implements Validate<String> {

  @Override
  public boolean verify(String s) {
    return !OptionalCharSequence.of(s).isEmpty();
  }

  @Override
  public String get() {
    return null;
  }

  @Override
  public RuntimeException exception() {
    return null;
  }
}
