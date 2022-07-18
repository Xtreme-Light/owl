package com.light.owl.exceptions.validator;

import com.light.owl.exceptions.BlankCharSequenceException;
import com.light.owl.exceptions.Validate;
import com.light.owl.util.OptionalCharSequence;

public class BlankCharSequenceValidator implements Validate {

  private final String item;

  public BlankCharSequenceValidator(String item) {
    this.item = item;
  }

  @Override
  public boolean verify() {
    return !OptionalCharSequence.ofNullable(item).isBlank();
  }

  @Override
  public RuntimeException runtimeException() {
    return new BlankCharSequenceException();
  }
}
